package gameClient;

import api.*;
import gameClient.util.Point3D;
import org.json.JSONObject;

/**
 * This class representing an agent, containing helpful information that we "take" from the game server about the agent such as:
 * agent ID
 * agent speed
 * update the agent with new information as speed
 * and so on
 */

public class CL_Agent {

	private int _id;
	private int pokSrc;
	private geo_location _pos;
	private double _speed;
	private edge_data _curr_edge;
	private node_data _curr_node;
	private directed_weighted_graph _gg;


	private double _value;

	public CL_Agent(directed_weighted_graph g, int start_node) {
		_gg = g;
		setMoney(0);
		this._curr_node = _gg.getNode(start_node);
		_pos = _curr_node.getLocation();
		_id = -1;
		setSpeed(0);
	}

	public void update(String json) {
		JSONObject line;
		try {
			line = new JSONObject(json);
			JSONObject ttt = line.getJSONObject("Agent");
			int id = ttt.getInt("id");
			if (id == this.getID() || this.getID() == -1) {
				if (this.getID() == -1) {
					_id = id;
				}
				double speed = ttt.getDouble("speed");
				String p = ttt.getString("pos");
				Point3D pp = new Point3D(p);
				int src = ttt.getInt("src");
				int dest = ttt.getInt("dest");
				double value = ttt.getDouble("value");
				this._pos = pp;
				this.setCurrNode(src);
				this.setSpeed(speed);
				this.setNextNode(dest);
				this.setMoney(value);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	//@Override
	public int getSrcNode() {
		return this._curr_node.getKey();
	}

	public String toJSON() {
		int d = this.getNextNode();
		String ans = "{\"Agent\":{"
				+ "\"id\":" + this._id + ","
				+ "\"value\":" + this._value + ","
				+ "\"src\":" + this._curr_node.getKey() + ","
				+ "\"dest\":" + d + ","
				+ "\"speed\":" + this.getSpeed() + ","
				+ "\"pos\":\"" + _pos.toString() + "\""
				+ "}"
				+ "}";
		return ans;
	}

	private void setMoney(double v) {
		_value = v;
	}

	public boolean setNextNode(int dest) {
		boolean ans = false;
		int src = this._curr_node.getKey();
		this._curr_edge = _gg.getEdge(src, dest);
		if (_curr_edge != null) {
			ans = true;
		} else {
			_curr_edge = null;
		}
		return ans;

	}

	public void setCurrNode(int src) {
		this._curr_node = _gg.getNode(src);
	}

	public String toString() {
		return toJSON();
	}


	public int getID() {
		return this._id;
	}

	public geo_location getLocation() {
		return _pos;
	}


	public int getNextNode() {
		int ans = -2;
		if (this._curr_edge == null) {
			ans = -1;
		} else {
			ans = this._curr_edge.getDest();
		}
		return ans;
	}

	public double getSpeed() {
		return this._speed;
	}

	public void setSpeed(double v) {
		this._speed = v;
	}

	public void setPokSrc(int src) {
		this.pokSrc = src;
	}

	public int getPokSrc() {
		return this.pokSrc;
	}

	public String get_value() {
		return String.valueOf(this._value);
	}
}
