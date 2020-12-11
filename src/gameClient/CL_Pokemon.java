package gameClient;
import api.dw_graph_algorithms;
import api.edge_data;
import api.geo_location;
import api.nodeLocation;
import gameClient.util.Point3D;
import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;

import java.nio.channels.CancelledKeyException;
import java.util.Comparator;

public class CL_Pokemon implements Comparable<CL_Pokemon> {
	private edge_data _edge;
	private double _value;
	private int _type;
	private Point3D _pos;
	private double min_dist;
	private int min_ro;
	private geo_location _location;

	private double tempWeight = 0;
	private double ratio;

	public CL_Pokemon(Point3D p, int t, double v, double s, edge_data e) {
		_type = t;
		//	_speed = s;
		_value = v;
		set_edge(e);
		_pos = p;
		min_dist = -1;
		min_ro = -1;
		_location = new nodeLocation(_pos.x(), _pos.y(), _pos.z());
	}
	public static CL_Pokemon init_from_json(String json) {
		CL_Pokemon ans = null;
		try {
			JSONObject p = new JSONObject(json);
			int id = p.getInt("id");

		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return ans;
	}
	public String toString() {return "F:{v="+_value+", t="+_type+"}";}
	public edge_data get_edge() {
		return _edge;
	}

	public void set_edge(edge_data _edge) {
		this._edge = _edge;
	}

	public Point3D getLocation() {
		return _pos;
	}
	public int getType() {return _type;}
	//	public double getSpeed() {return _speed;}
	public double getValue() {return _value;}

	public double getMin_dist() {
		return min_dist;
	}

	public void setMin_dist(double mid_dist) {
		this.min_dist = mid_dist;
	}

	public int getMin_ro() {
		return min_ro;
	}

	public void setMin_ro(int min_ro) {
		this.min_ro = min_ro;
	}

	public void setTempWeight(dw_graph_algorithms ga, int src){
		//update the distance between agent and this pokemon
		tempWeight = ga.shortestPathDist(src, this.get_edge().getSrc()) + this.get_edge().getWeight();
	}
	public double getTempWeight(){
		return this.tempWeight;
	}

	public void ratioWeightValue(){ //calculates the ratio between shortestPath from pok to agent, and pokemon value.
		double weightR = (3.0/4)*getTempWeight();
		double valR = (1.0/4)*(-1*getValue());
		this.ratio = weightR + valR;
	}

	public double getRatio(){
		return this.ratio;
	}
	public geo_location get_location(){
		return this._location;
	}

	@Override
	public int compareTo(@NotNull CL_Pokemon o) {
		if (this.getTempWeight() - o.getTempWeight() > 0) return 1;
		else if (this.getTempWeight() - o.getTempWeight() < 0) return -1;
		return 0;
	}
	static class pathComparator implements Comparator<CL_Pokemon> {

		@Override
		public int compare(CL_Pokemon o1, CL_Pokemon o2) {
			if (o1.getTempWeight() - o2.getTempWeight() > 0) return 1;
			else if (o1.getTempWeight() - o2.getTempWeight() < 0) return -1;
			return 0;
		}
	}

	static class valueComparator implements Comparator<CL_Pokemon>{

		@Override
		public int compare(CL_Pokemon o1, CL_Pokemon o2) {
			if (o1.getValue() - o2.getValue() < 0) return 1;
			else if (o1.getValue() - o2.getValue() > 0) return -1;
			return 0;
		}
	}
}
