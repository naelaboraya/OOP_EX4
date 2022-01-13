package Game;
import GraphPack.*;




public class Pokemon {

    private EdgeData E;

    private double value;

    private int type;

    private geo_loc pos;

    private boolean hunting;


    public Pokemon(geo_loc pos, int t, double v, EdgeData E) {//constructor
        this.E = E;
        this.type = t;
        this.value = v;
        this.pos = pos;
    }



    public EdgeData getEdge() {
        return E;
    }

    public void setEdge(EdgeData edge) {
        this.E = edge;
    }

    public geo_loc getLocation() {
        return pos;
    }

    public int getType() {
        return type;
    }

    public boolean is_hunted(){
        return this.hunting;
    }

    public void hunted(){
        this.hunting = true;
    }

    public void not_hunted(){
        this.hunting = false;
    }

    public String toString() {return "Pokemon:{value="+ value +", type= "+ type +"}";}




}