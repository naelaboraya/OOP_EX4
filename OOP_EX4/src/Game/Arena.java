package Game;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import GraphPack.*;




public class Arena {
    //epsilon
    public static final double EPS =0.00001;

    private DirectedWeightedGraph G;

    private List<Agent> agents;
    private List<Pokemon> pokemons;


    public Arena() {}


    /**
     * This functions convert pokemons from json
     * @param JSON_FILE
     * @return
     */
    public static ArrayList<Pokemon> pok_from_json(String JSON_FILE) {

        ArrayList<Pokemon> pokemons = new  ArrayList<>();

        try {

            JSONObject pok_obj = new JSONObject(JSON_FILE);

            JSONArray pok_arr = pok_obj.getJSONArray("Pokemons");


            for(int i=0; i < pok_arr.length(); i++)
            {
                JSONObject pokemonJ = pok_arr.getJSONObject(i);
                JSONObject pkJ = pokemonJ.getJSONObject("Pokemon");
                int type = pkJ.getInt("type");
                double value = pkJ.getDouble("value");
                String pos = pkJ.getString("pos");



                Pokemon P = new Pokemon(new geo_loc(pos), type, value, null);
                pokemons.add(P);
            }
        }
        catch (JSONException e) {e.printStackTrace();
        }

        return pokemons;
    }

    /**
     * This function converts agents from json
     * @param JSON_FILE
     * @param g
     * @return
     */
    public static List<Agent> agents_from_json(String JSON_FILE, DirectedWeightedGraph g) {

        ArrayList<Agent> agents = new ArrayList<>();

        try {
            JSONObject agent_obj = new JSONObject(JSON_FILE);
            JSONArray agent_arr = agent_obj.getJSONArray("Agents");

            for(int i=0; i < agent_arr.length(); i++)
            {
                Agent agent = new Agent(g,0);
                agent.change_vals(agent_arr.get(i).toString());
                agents.add(agent);
            }
        }
        catch (JSONException e) {
            e.printStackTrace();
        }

        return agents;
    }


    /**
     * is the pokemon exist on the edge ?
     * @param pos
     * @param edge
     * @param type
     * @param g
     * @return
     */
    private static boolean pokemon_location(GeoLocation pos, EdgeData edge, int type, DirectedWeightedGraph g) {

        int src = g.getNode(edge.getSrc()).getKey();
        int dest = g.getNode(edge.getDest()).getKey();
        if(type<0 && dest>src) {
            return false;
        }
        if(type>0 && src>dest) {
            return false;
        }

        GeoLocation Source = g.getNode(src).getLocation();
        GeoLocation Destination = g.getNode(dest).getLocation();

        double dist = Source.distance(Destination);//distance between src and dest
        double D = Source.distance(pos) + pos.distance(Destination);//distance between pokemon and src&dest
        if (dist> D - EPS)//greater than epsilon (0.00001)
        return true;


        return false;

    }


    public static void change_edge(Pokemon P, DirectedWeightedGraph g) {
        Iterator<NodeData> node_itr = g.nodeIter();
        Iterator<EdgeData> edge_itr ;
        while(node_itr.hasNext()){
            NodeData curr = node_itr.next();
            edge_itr = g.edgeIter(curr.getKey());
            while(edge_itr.hasNext()){
                EdgeData edge = edge_itr.next();
                boolean has_found = pokemon_location(P.getLocation(), edge, P.getType(), g);
                if (has_found){
                    P.setEdge(edge);

                    return;
                }
            }
        }
    }

    public void set_pokemons(List<Pokemon> pokemons) {
        this.pokemons = pokemons;
    }
    public void set_agents(List<Agent> agents) {
        this.agents = agents;
    }
    public void set_graph(DirectedWeightedGraph graph) {
        this.G =graph;
    }
    public List<Agent> get_agents() {
        return agents;
    }
    public List<Pokemon> get_pokemons() {
        return pokemons;
    }
    public DirectedWeightedGraph get_graph() {
        return G;
    }


}
