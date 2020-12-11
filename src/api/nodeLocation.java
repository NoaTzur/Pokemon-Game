package api;

import static java.lang.Float.POSITIVE_INFINITY;

public class nodeLocation implements geo_location{
    double x,y,z;


    public nodeLocation(geo_location n){
        this.setX(n.x());
        this.setY(n.y());
        this.setZ(n.z());
    }

    public nodeLocation(double x, double y, double z){
        this.setX(x);
        this.setY(y);
        this.setZ(z);
    }
    public nodeLocation(){
        this.setX(POSITIVE_INFINITY);
        this.setY(POSITIVE_INFINITY);
        this.setZ(POSITIVE_INFINITY);
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    public void setZ(double z) {
        this.z = z;
    }

    @Override
    public double x() {
        return this.x;
    }

    @Override
    public double y() {
        return this.y;
    }

    @Override
    public double z() {
        return this.z;
    }

    /**
     * this function computes the distance between 2 3DPoints.
     * sqrt((x1-x2)^2 + (y1-y2)^2 + (z1-z2)^2)
     * @param g - the point to calculates
     * @return distance
     */
    @Override
    public double distance(geo_location g) {
        double xPow = Math.pow(this.x() - g.x(), 2);
        double yPow = Math.pow(this.y() - g.y(), 2);
        double zPow = Math.pow(this.z() - g.z(), 2);
        return Math.sqrt(xPow + yPow + zPow);
    }
}