package GraphPack;

public class geo_loc implements GeoLocation {
private double x,y,z;

    public geo_loc(){
    this.x=0;
    this.y=0;
    this.z=0;

    }

    public geo_loc(geo_loc p){
        this(p.x(), p.y(), p.z());
    }

    public geo_loc(double x,double y){
    this(x,y,0);
    }

    public geo_loc(String s) {
        try {
            String[] a = s.split(",");
            x = Double.parseDouble(a[0]);
            y = Double.parseDouble(a[1]);
            z = Double.parseDouble(a[2]);
        }
        catch(IllegalArgumentException e) {
            System.err.println("Must be x,y,z");
            throw(e);
        }
    }
    public  geo_loc(double x,double y , double z){
    this.x=x;
    this.y=y;
    this.z=z;
    }
    public geo_loc(GeoLocation e){
        this.x=e.x();
        this.y=e.y();
        this.z=e.z();
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

    @Override
    public double distance(GeoLocation g) {
        double dx=Math.pow((this.x-g.x()),2);
        double dy=Math.pow((this.y-g.y()),2);
        double dz=Math.pow((this.z-g.z()),2);
        double ans=Math.sqrt(dx+dy+dz);
        return ans;
    }
    @Override
    public  String toString(){
       String geo_loc_str=this.x + " , "  + this.y + " , " + this.z;
       return geo_loc_str;
    }
}
