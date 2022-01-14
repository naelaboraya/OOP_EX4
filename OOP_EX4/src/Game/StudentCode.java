package Game;

import GraphPack.*;
import com.google.gson.*;
import org.json.*;
import GUI.gui;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;



public class StudentCode implements Runnable {

    private static Arena field;
    private static Client cli;
    private static HashMap<Agent, List<NodeData>> Route;
    public static final double INFINITY=Double.POSITIVE_INFINITY;


    public StudentCode() {

    }


    //the main function
    public static void main(String[] args) {

        cli = new Client();
        try {
            cli.startConnection("127.0.0.1", 6666);
        } catch (IOException e) {
            e.printStackTrace();
        }

        String str_g = cli.getGraph();
        System.out.println(cli.timeToEnd());
        DirectedWeightedGraph g = create_graph(str_g);
        try {
            game_begin(cli);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        gui G = new gui("Pokemon Game");
        field.set_graph(g);
        G.renew(field);
        G.setSize(1200,1000);
        G.setVisible(true);


        Thread clients = new Thread(new StudentCode());
        clients.start();

    }


    @Override
    public void run() {
        String str_g = cli.getGraph();
        System.out.println(cli.timeToEnd());
        DirectedWeightedGraph g = create_graph(str_g);

        cli.start();

        System.out.println(cli.timeToEnd());
        while (cli.isRunning().equals("true")) {
            int v=Integer.parseInt(cli.timeToEnd());

            if (v< 100) {
                System.out.println(cli.getInfo());
                try {
                    cli.stopConnection();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                System.exit(0);
            }
            try {
                Thread.sleep((int) ((Math.random() * (140 - 60)) + 60));
                cli.move();
                agent_move(cli, g);

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Initializes the game , converts the strings to objects
     * @param game
     * @throws JSONException
     */
    public static void game_begin(Client game) throws JSONException {

        String json_graph = game.getGraph();
        String json_pokemon = game.getPokemons();

        DirectedWeightedGraph g = create_graph(json_graph);
        List<Pokemon> pokemons = Arena.pok_from_json(json_pokemon);

        field = new Arena();
        field.set_graph(g);
        field.set_pokemons(pokemons);

        String game_info = game.getInfo();
        JSONObject game_obj;
        try {
            game_obj = new JSONObject(game_info);
            JSONObject server_obj = game_obj.getJSONObject("GameServer");

            int _agents = server_obj.getInt("agents");

            for (Pokemon P : pokemons) {
                Arena.change_edge(P, g);
            }

            if (_agents < pokemons.size()) {

                for (int i = 0; i < _agents; i++) {
                    EdgeData edge = pokemons.get(i).getEdge();
                    String source = "{\"id\":" + edge.getSrc() + "}";
                    game.addAgent(source);
                }
            }
            else {

                int size_difference = _agents - pokemons.size();
                for (Pokemon P : pokemons) {
                    EdgeData edge = P.getEdge();
                    String source = "{\"id\":" + edge.getSrc() + "}";
                    game.addAgent(source);
                }

                //

                for (int i = 0; i < size_difference; i++) {
                    game.addAgent(String.valueOf(i % (g.nodeSize() - 1)));
                }

            }
        }
        catch (JSONException e) {
            e.printStackTrace();
        }

    }


    private static void agent_route_initialize() {
        Route = new HashMap<>();
        for (Agent A : field.get_agents()) {
            Route.put(A, null);
        }
    }

    private static int upcoming_node(DirectedWeightedGraph G, int Current, Agent agent) {
        if (Route == null) {
            agent_route_initialize();
        }
        int _next = -1;
        List<NodeData> this_route = Route.get(agent);
        if (this_route != null) {
            if (!this_route.isEmpty()) {
                _next = this_route.get(0).getKey();
                this_route.remove(0);
                return _next;
            }
        }

        DirectedWeightedGraphAlgorithms G_alg = new DirectedWeightedGraphAlgorithmsImpl();
        G_alg.init(G);
        double shortest_distance = INFINITY;

        Pokemon goal = null;


        for (Pokemon P : field.get_pokemons()) {
            Arena.change_edge(P, G);


            if (P.is_hunted())
                continue;

            int Source = P.getEdge().getSrc();
            int Destination = P.getEdge().getDest();
            List<NodeData> is_route = G_alg.shortestPath(Current, Source);


            if (!is_route.isEmpty())
                is_route.remove(0);

            double _distance = is_route.size();
            P.not_hunted();
            if (_distance == 0) return Destination;
            if (_distance < shortest_distance) {
                shortest_distance = _distance;
                Route.put(agent, is_route);
                _next = is_route.get(0).getKey();
                goal = P;
            }
        }
        assert goal != null;
        goal.hunted();



        return _next;
    }



    private static void agent_move(Client game, DirectedWeightedGraph g) {
        List<Agent> agents = Arena.agents_from_json(game.getAgents(), g);
        String str_p = game.getPokemons();
        List<Pokemon> pokemons = Arena.pok_from_json(str_p);
        field.set_agents(agents);
        field.set_pokemons(pokemons);

        for (Agent A : agents) {
            int this_node = A.get_node().getKey();
            int key = A.get_key();
            int dest = A.get_next();
            double val = A.get_val();
            boolean bool = (dest==-1);

            if (bool) {
                dest = upcoming_node(g, this_node, A);
                String next_edge = "{\"agent_id\":" + A.get_key() + ", \"next_node_id\":" + dest + "}";
                game.chooseNextEdge(next_edge);

                System.out.println("Agent: " + key + ", value: " + val + "   to : " + dest + " Edge:" + this_node + "," + dest);

                score();
            }
        }
    }






    private static void score() {
        JsonParser jp = new JsonParser();
        JsonObject info = (JsonObject) jp.parse(cli.getInfo());
        info = info.getAsJsonObject("GameServer");
        gui.Score = (int) info.get("grade").getAsDouble();
        gui.Moves = (int) info.get("moves").getAsDouble();
        gui.Level = info.get("game_level").getAsInt();
        gui.Time = (Integer.parseInt(cli.timeToEnd()) / 1000);

    }











    public static DirectedWeightedGraph create_graph(String graph) {
        JsonObject obj;
        JsonParser j_parser = new JsonParser();
        obj = (JsonObject) j_parser.parse(graph);
        DirectedWeightedGraph g = new DirectedWeightedGraphImpl();
        JsonArray nodes = obj.getAsJsonArray("Nodes");
        JsonArray edges = obj.getAsJsonArray("Edges");


        for (JsonElement E : nodes) {
            JsonObject node = E.getAsJsonObject();
            String loc = node.get("pos").getAsString();
            String[] pos = loc.split(",");
            int key = node.get("id").getAsInt();
            double a,b,c;



            a = Double.parseDouble(pos[0]);
            b = Double.parseDouble(pos[1]);
            c = Double.parseDouble(pos[2]);
            g.addNode(new Nodeimple(key ,new geo_loc(a,b,c) ));
        }


        for (JsonElement E : edges) {
            JsonObject Edge_json = E.getAsJsonObject();
            int source = Edge_json.get("src").getAsInt();
            int destination = Edge_json.get("dest").getAsInt();
            double weight = Edge_json.get("w").getAsDouble();

            g.connect(source, destination, weight);
        }



        return g;
    }




}


