package api;

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

    @Override
    public int getSrc() {
        return this._src;
    }

    @Override
    public int getDest() {
        return this._dest;
    }

    @Override
    public double getWeight() {
        return this._weight;
    }

    @Override
    public String getInfo() {
        return this._msg;
    }

    @Override
    public void setInfo(String s) {
        this._msg = s;
    }

    @Override
    public int getTag() {
        return this._tag;
    }

    @Override
    public void setTag(int t) {
        this._tag = t;
    }


}
