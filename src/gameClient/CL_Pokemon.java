package gameClient;
import api.dw_graph_algorithms;
import api.edge_data;
import api.geo_location;
import api.nodeLocation;
import gameClient.util.Point3D;
import org.json.JSONObject;

import java.util.Comparator;

import static java.lang.Double.POSITIVE_INFINITY;

/**
 * This class representing a pokemon, containing helpful information that we "take" from the game server about the agent such as:
 * pokemon value (how much he cost for catching)
 * on what edge does it placed in the game graph
 * we has added 2 things to this written class - 2 comparators
 */

public class CL_Pokemon {
	private edge_data _edge;
	private double _value;
	private int _type;
	private Point3D _pos;
	private double min_dist;
	private int min_ro;
	private geo_location _location;

	private double tempWeight = 0;

	public CL_Pokemon(Point3D p, int t, double v, double s, edge_data e) {
		_type = t;
		_value = v;
		set_edge(e);
		_pos = p;
		min_dist = -1;
		min_ro = -1;
		_location = new nodeLocation(_pos.x(), _pos.y(), _pos.z());
	}

	/**
	 * overrides the equals Object function - checks if 2 pokemons is "equals", equals (for our algorithm) means "sitting"
	 * on the same edge.
	 * if the pokemon is sits in the same edge - return true
	 * if not- false
	 * @param o - pokemon
	 * @return true/false
	 */
	@Override
	public boolean equals(Object o){
		if (!(o instanceof CL_Pokemon)) {
			return false;
		}
		if(this.get_edge().getSrc() == ((CL_Pokemon) o).get_edge().getSrc() && this.get_edge().getDest() == ((CL_Pokemon) o).get_edge().getDest()){
			return true;
		}
		if(this.get_edge().getSrc() == ((CL_Pokemon) o).get_edge().getDest() && this.get_edge().getDest() == ((CL_Pokemon) o).get_edge().getSrc()){
			return true;
		}
		return false;
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
	public double getValue() {return _value;}

	/**
	 * this function is calculating, with the algorithm that we have written in the first part of this exercise
	 * called "shortestPathDist" algorithm. this algorithms return an double that representing the shortest path consider
	 * the weight of the edges from given src node with an agent to this pokemon src edge node + dest of the pokemon
	 * edge. if the shortest path algo returns -1 it means that the pokemon and the agent are not at the same
	 * component in the graph.
	 * we set the answer in that case to be POSITIVE_INFINITY because we are using this field in a priority queue
	 * that poll the pokemon with the shortest path from the agent.
	 * @param ga = graph algorithms
	 * @param src = src node that the agent is standing
	 */
	public void setTempWeight(dw_graph_algorithms ga, int src){
		//update the distance between agent and this pokemon
		tempWeight = ga.shortestPathDist(src, this.get_edge().getSrc()) + this.get_edge().getWeight();
		if(tempWeight == -1) {//the pokemon not in the same component as the agent
			tempWeight = POSITIVE_INFINITY;
		}
	}

	/**
	 * return the weight of the pokemon that has been calculate by the "setTempWeight" function above
	 * @return pokemon temp weight(considering specific agent)
	 */
	public double getTempWeight(){
		return this.tempWeight;
	}

	public geo_location get_location(){
		return this._location;
	}

	/**
	 * Comparator for the priority queue we are using in the main function in Ex2, poll out the pokemon with the
	 * shortest path to an agent
	 */
	static class pathComparator implements Comparator<CL_Pokemon> {

		@Override
		public int compare(CL_Pokemon o1, CL_Pokemon o2) {
			if (o1.getTempWeight() - o2.getTempWeight() > 0) return 1;
			else if (o1.getTempWeight() - o2.getTempWeight() < 0) return -1;
			return 0;
		}
	}

	/**
	 * this Comparator using by a priority queue we are using in the init function in the main game class,
	 * polls the pokemon with the highest value, we are placing the agent in the beginning of the game near this pokemons
	 * so that pokemons will be eaten first.
	 */
	static class valueComparator implements Comparator<CL_Pokemon>{

		@Override
		public int compare(CL_Pokemon o1, CL_Pokemon o2) {
			if (o1.getValue() - o2.getValue() < 0) return 1;
			else if (o1.getValue() - o2.getValue() > 0) return -1;
			return 0;
		}
	}
}
