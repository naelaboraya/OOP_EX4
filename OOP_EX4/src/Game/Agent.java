package Game;


import org.json.JSONException;

import org.json.JSONObject;

import GraphPack.*;


import static Game.Arena.EPS;//importing epselon




public class Agent    implements  Comparable<Agent>{
    private EdgeData E;
    private NodeData N;

    private int key;
    private double value;
    private int src,dest;
    private double speed;
    private GeoLocation pos;

    private DirectedWeightedGraph graph;


    public Agent(DirectedWeightedGraph G, int key){//constructor
        this.graph = G;
        this.N = G.getNode(key);
        pos = N.getLocation();
        this.key = -1;
        set_speed(0);
        set_val(0);
    }


    public void change_vals(String S_json) throws JSONException {
        JSONObject json_obj ;

        try {

            json_obj = new JSONObject(S_json);

            JSONObject agent = json_obj.getJSONObject("Agent");


            int key = agent.getInt("id");
            if (key == this.key || this.key == -1) {
                if (this.key == -1) {
                    this.key = key;
                }

            geo_loc p = new geo_loc(agent.getString("pos"));
            this.set_position(p);

            this.set_val(agent.getDouble("value"));

            this.src = (agent.getInt("src"));
            this.set_node(graph.getNode(src));

            this.dest = agent.getInt("dest");
            this.set_next(dest);

            this.set_speed(agent.getDouble("speed"));
            }
        }

        catch(Exception e){//error
            }
        }


    public boolean set_next(int dest) {

        boolean bool = false;

        int src = this.N.getKey();
        this.E = (EdgeData) graph.getEdge(src, dest);
        if (E != null) {
            bool = true;
        }
        else {
            E = null;
        }


        return bool;
    }


    public int get_next() {
        int next;
        if (this.E == null) {
            next = -1;
        }
        else {
            next = this.E.getDest();
        }


        return next;
    }




    public int get_key() {
        return key;
    }

    public void set_key(int _key) {
        this.key = _key;
    }

    public double get_val() {
        return value;
    }

    public void set_val(double value) {
        this.value = value;
    }

    public double get_speed() {
        return speed;
    }

    public void set_speed(double speed) {
        this.speed = speed;
    }

    public GeoLocation get_pos() {
        return pos;
    }

    public void set_position(GeoLocation pos) {
        this.pos = pos;
    }

    public NodeData get_node() {
        return N;
    }

    public void set_node(NodeData n) {
        this.N = n;
    }

    //comparator!
    @Override
    public int compareTo(Agent agent) {
        if(agent==null) return 1;
        if(this.pos.distance(agent.pos) < EPS) return 1;
        else return -1;
    }


}
