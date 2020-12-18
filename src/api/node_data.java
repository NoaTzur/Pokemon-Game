package api;

/**
 * This interface represents the set of operations applicable on a
 * node (vertex) in a (directional) weighted graph.
 * @author boaz.benmoshe
 *
 */
public interface node_data {
    /**
     * Returns the key (id) associated with this node.
     * @return int
     */
    public int getKey();
    /** Returns the location of this node, if
     * none return null.
     *
     * @return geo_location
     */
    public geo_location getLocation();
    /** Allows changing this node's location.
     * @param p - new new location  (position) of this node.
     */
    public void setLocation(geo_location p);
    /**
     * Returns the weight associated with this node.
     * @return double
     */
    public double getWeight();
    /**
     * Allows changing this node's weight.
     * @param w - the new weight
     */
    public void setWeight(double w);
    /**
     * Returns the remark (meta data) associated with this node.
     * @return string
     */
    public String getInfo();
    /**
     * Allows changing the remark (meta data) associated with this node.
     * @param s - string
     */
    public void setInfo(String s);
    /**
     * Temporal data (aka color: e,g, white, gray, black)
     * which can be used be algorithms
     * @return int
     */
    public int getTag();
    /**
     * Allows setting the "tag" value for temporal marking an node - common
     * practice for marking by algorithms.
     * @param t - the new value of the tag
     */
    public void setTag(int t);
}