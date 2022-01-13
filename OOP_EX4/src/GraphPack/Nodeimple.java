package GraphPack;

import java.awt.*;

public class Nodeimple implements  NodeData{
private int key;
private double weight;
private String info;
 private Color color;
 private GeoLocation location;


 public  Nodeimple(){
     this.key=0;
     this.weight=0;
     this.info=null;
     this.color=Color.WHITE;
     this.location=new geo_loc(0,0,0);
 }

    public Nodeimple(int id, String pos) {
   this.key=id;

  String [] loc = pos.split(",");
        Double xx =Double.parseDouble(loc[0]);
        Double yy =Double.parseDouble(loc[1]);
        Double zz =Double.parseDouble(loc[2]);
        this.location=new geo_loc(xx,yy,zz);

   this.weight=0;
   this.color=Color.WHITE;
    }

    public Nodeimple(int id , GeoLocation GL){
     this.key=id;
     this.location=GL;
     this.weight=0;
     this.color=Color.WHITE;
    }

    public Nodeimple(String pos, int id) {
        this.key=id;
        String [] loc = pos.split(",");
       Double xx =Double.parseDouble(loc[0]);
        Double yy =Double.parseDouble(loc[1]);
       Double zz =Double.parseDouble(loc[2]);
       this.location=new geo_loc(xx,yy,zz);
    }

    public void setkey(int key){
        this.key=key;
    }
    public Color getcolor(){
        return this.color;

    }


    public void setColor(Color c){
        this.color=c;

    }
    @Override
    public int getKey()
    {
        return key;
    }
    @Override
    public GeoLocation getLocation() {

        return this.location;
    }

    @Override
    public void setLocation(GeoLocation p) {
    this.location=new geo_loc(p);
    }

    @Override
    public double getWeight() {

        return weight;
    }

    @Override
    public void setWeight(double w) {
   this.weight=w;
    }

    @Override
    public String getInfo() {

        return this.info;
    }

    @Override
    public void setInfo(String s) {
    this.info=s;
    }

    @Override
    public int getTag() {

     return this.color.getRGB();
    }

    @Override
    public void setTag(int t) {
    this.color=new Color(t);

    }
    public int getTAG2() {//for tsp

        return this.color.getRGB();
    }
    public void setTAG2(int t) {//for tsp
        this.color=new Color(t);

    }

    @Override
    public String toString() {
        return key+"";
    }
}

