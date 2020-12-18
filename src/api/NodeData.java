package api;

/**
 * This interface represents the set of operations applicable on a
 * node (vertex) in a (directional) weighted graph.
 */

import static java.lang.Double.POSITIVE_INFINITY;

public class NodeData implements node_data, Comparable<node_data> {
    private int _id;
    private String _msg;
    private int _tag;
    private nodeLocation _pos = new nodeLocation();
    private double _weight = POSITIVE_INFINITY;
    static int counterKey = 0;

    /**
     * override compareTo in order to sort the nodes by their weight
     * @param o - node
     * @return int
     */
    @Override
    public int compareTo(node_data o) {
        if (this._weight - o.getWeight() > 0) return 1;
        else if (this._weight - o.getWeight() < 0) return -1;
        return 0;
    }

    //constructor

    public NodeData(node_data n){
        this._id = n.getKey();
        this._msg = n.getInfo();
        this._tag = n.getTag();
        this._pos = new nodeLocation(n.getLocation());
        this._weight = n.getWeight();

    }
    //constructor

    public NodeData(int key, String info, int tag, double weight, double x, double y, double z){
        this._id = key;
        this.setInfo(info);
        this.setTag(tag);
        geo_location thisNodeLocation = new nodeLocation(x,y,z);
        this.setLocation(thisNodeLocation);
        this.setWeight(weight);
    }
    //constructor

    public NodeData(int key, double x, double y, double z){
        this._id = key;
        geo_location thisNodeLocation = new nodeLocation(x,y,z);
        this.setLocation(thisNodeLocation);

    }
    //constructor
    public NodeData(){
        this._id = counterKey++;
        this._msg ="";
        this._tag = -1;

    }
    //constructor

    public NodeData(int key){
        this._id = key;
        this._msg ="";
        this._tag = -1;
        this._pos = new nodeLocation(0,0,0);
    }

    /**
     * Returns the key (id) associated with this node.
     * @return node key
     */
    @Override
    public int getKey() {
        return this._id;
    }

    /**
     * Returns the location of this node, if none return null.
     * @return location or null
     */
    @Override
    public geo_location getLocation() {
        if(_pos.x() == POSITIVE_INFINITY  || _pos.y() == POSITIVE_INFINITY || _pos.z() == POSITIVE_INFINITY)
            return null;
        return this._pos;
    }

    /** Allows changing this node's location.
     * @param p - new new location  (position) of this node.
     */
    @Override
    public void setLocation(geo_location p) {
        this._pos.setX(p.x());
        this._pos.setY(p.y());
        this._pos.setZ(p.z());
    }

    /**
     * Returns the weight associated with this node.
     * @return nodes weight
     */
    @Override
    public double getWeight() {
        return this._weight;
    }

    /**
     * Allows changing this node's weight.
     * @param w - the new weight
     */
    @Override
    public void setWeight(double w) {
        this._weight = w;
    }

    /**
     * Returns the remark (meta data) associated with this node.
     * @return nodes info
     */
    @Override
    public String getInfo() {
        return this._msg;
    }

    /**
     * Allows changing the remark (meta data) associated with this node.
     * @param s = new info
     */
    @Override
    public void setInfo(String s) {
        this._msg = s;
    }

    /**
     * returns the nodes tag (- temporal data (aka color: e,g, white, gray, black))
     * @return nodes Tag
     */
    @Override
    public int getTag() {
        return this._tag;
    }

    /**
     * Allows setting the "tag" value for temporal marking an node - mainly for the use of the algorithms
     * @param t - the new value of the tag
     */
    @Override
    public void setTag(int t) {
        this._tag = t;
    }

}
