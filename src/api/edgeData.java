package api;

/**
 * This interface represents the set of operations applicable on a
 * directional edge(src,dest) in a (directional) weighted graph.
 */
public class edgeData implements edge_data {
    int _src;
    int _dest;
    int _tag;
    double _weight;
    String _msg="";

    public edgeData(int src, int dest, double w, int tag, String info){
        this._src = src;
        this._dest = dest;
        this._weight = w;
        this.setTag(tag);
        this.setInfo(info);
    }
    public edgeData(int src, int dest, double w){
        this._src = src;
        this._dest = dest;
        this._weight = w;
    }
    /**
     * The id of the source node of this edge.
     * @return src
     */
    @Override
    public int getSrc() {
        return this._src;
    }

    /**
     * The id of the destination node of this edge
     * @return dest
     */
    @Override
    public int getDest() {
        return this._dest;
    }

    /**
     * @return the weight of this edge (positive value).
     */
    @Override
    public double getWeight() {
        return this._weight;
    }

    /**
     * @return the remark (meta data) associated with this edge.
     */
    @Override
    public String getInfo() {
        return this._msg;
    }
    /**
     * Allows changing the remark (meta data) associated with this edge.
     * @param s string
     */
    @Override
    public void setInfo(String s) {
        this._msg = s;
    }

    /**
     * Temporal data (aka color: e,g, white, gray, black)
     * which can be used be algorithms
     * @return int
     */
    @Override
    public int getTag() {
        return this._tag;
    }

    /**
     * This method allows setting the "tag" value for temporal marking an edge - common
     * practice for marking by algorithms.
     * @param t - the new value of the tag
     */
    @Override
    public void setTag(int t) {
        this._tag = t;
    }


}
