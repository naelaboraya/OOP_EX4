package GraphPack;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import java.util.HashMap;
import java.util.Map;

import java.awt.*;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.List;

public class DirectedWeightedGraphAlgorithmsImpl implements DirectedWeightedGraphAlgorithms {
    private DirectedWeightedGraph g;

    private static final double Infinity = Double.POSITIVE_INFINITY;

    @Override
    public void init(DirectedWeightedGraph g) {
        this.g = g;
    }

    @Override
    public DirectedWeightedGraph getGraph() {
        return this.g;
    }

    @Override
    public DirectedWeightedGraph copy() {
        DirectedWeightedGraphImpl graph = new DirectedWeightedGraphImpl();
        //copy nodes
        Iterator<NodeData> nodes = this.g.nodeIter();
        while (nodes.hasNext()) {
            graph.addNode(nodes.next());
        }
        //copy edges , and connect them
        Iterator<NodeData> newitr = this.g.nodeIter();
        while (newitr.hasNext()) {
            NodeData i = newitr.next();
            Iterator<EdgeData> j = this.g.edgeIter(i.getKey());
            while (j.hasNext()) {
                EdgeData edge = j.next();
                graph.connect(edge.getSrc(), edge.getDest(), edge.getWeight());
            }
        }
        return graph;
    }

    public void start() {//A function that starts/clears the graph (sets all the nodes colors white and weight infinity )
        Iterator<NodeData> itr = g.nodeIter();
        while (itr.hasNext()) {
            NodeData node = itr.next();
            node.setTag(Color.WHITE.getRGB());
            node.setWeight(Infinity);
            node.setInfo(null);
        }


    }

    @Override
    public boolean isConnected() {

        Iterator<NodeData> itr = g.nodeIter();
        while (itr.hasNext()) {
            NodeData node = itr.next();
            start();

            DFS(g, node.getKey());

            Iterator<NodeData> n = g.nodeIter();
            while (n.hasNext()) {

                if (n.next().getTag() != Color.BLACK.getRGB()) {
                    return false;
                }

            }

        }
        return true;
    }

    private void DFS(DirectedWeightedGraph g, int key) {//DFS Algorithm

        g.getNode(key).setTag(Color.BLACK.getRGB());
        Iterator<EdgeData> edges = g.edgeIter(key);
        while (edges.hasNext()) {
            NodeData temp = g.getNode(edges.next().getDest());

            if (temp.getTag() != Color.BLACK.getRGB()) {
                DFS(g, temp.getKey());

            }
        }

    }


    @Override
    public double shortestPathDist(int src, int dest) {
        start();
        Queue<NodeData> queue = new LinkedList<NodeData>();
        NodeData s = g.getNode(src);
        s.setWeight(0);
        queue.add(s);
        while (s != null) {

            s.setTag(Color.BLACK.getRGB());

            Iterator<EdgeData> itr = g.edgeIter(s.getKey());


            while (itr.hasNext()) {
                EdgeData e = itr.next();
                NodeData neighbor = g.getNode(e.getDest());

                if (neighbor.getWeight() > s.getWeight() + e.getWeight()) {

                    neighbor.setInfo("" + s.getKey());

                    neighbor.setWeight(s.getWeight() + e.getWeight());

                    queue.add(neighbor);
                }
            }

            queue.poll();
            s = queue.peek();
        }
        if (g.getNode(dest).getWeight() == Infinity) {

            return -1;
        } else {

            return g.getNode(dest).getWeight();
        }
    }


    @Override
    public List<NodeData> shortestPath(int src, int dest) {

        if (shortestPathDist(src, dest) == -1) return null;
        ArrayList<NodeData> ans = new ArrayList<NodeData>();
        NodeData source = g.getNode(dest);
        ans.add(source);
        while (source.getInfo() != null) {
            source = g.getNode(Integer.parseInt(source.getInfo()));
            ans.add(source);
        }

        ArrayList<NodeData> answer = new ArrayList<NodeData>();
        for (int i = ans.size() - 1; i >= 0; i--) {
            answer.add(ans.get(i));
        }
        return answer;
    }


    @Override
    public NodeData center() {
        double min = Infinity;
        NodeData centernode = new Nodeimple();
        if (isConnected() == false) {//check if the graph is connected , if not return null
            return null;
        }
        HashMap<Integer, Double> totaldis = new HashMap<>();
        Iterator<NodeData> itr = g.nodeIter();
        while (itr.hasNext()) {
            double distance = 0;
            int firstnode = itr.next().getKey();
            Iterator<NodeData> itr2 = g.nodeIter();
            while (itr2.hasNext()) {

                int secoendnode = itr2.next().getKey();
                distance = distance + shortestPathDist(firstnode, secoendnode);

            }
            totaldis.put(firstnode, distance);

        }
        double minimum = Infinity;
        Iterator<NodeData> nodeiter = g.nodeIter();
        Iterator it = totaldis.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry par = (Map.Entry) it.next();

            minimum = Collections.min(totaldis.values());

        }
        int n = getkey(totaldis, minimum);

        Iterator<NodeData> answer = g.nodeIter();
        while (answer.hasNext()) {
            NodeData ansnode = new Nodeimple();
            ansnode = answer.next();
            if (ansnode.getKey() == n) {

                centernode = ansnode;

            }

        }
        return centernode;
    }

    //function that returns the key for a given value , we used it (helping function) , in the center function
    //it returns the key/id of the center node
    private Integer getkey(HashMap<Integer, Double> tot, Double min) {
        for (Integer key : tot.keySet()) {
            if (min.equals(tot.get(key))) {
                return key;
            }
        }
        return null;
    }

    public void start2() {
        Iterator<NodeData> itr = g.nodeIter();
        while (itr.hasNext()) {
            Nodeimple node = (Nodeimple) itr.next();
            node.setTAG2(Color.WHITE.getRGB());
            node.setWeight(Infinity);
            node.setInfo(null);
        }
    }

    @Override
    public List<NodeData> tsp(List<NodeData> cities) {
        List<NodeData> list = new ArrayList<>();
        start();
        if(cities.size() == 0){
            return null;
        }
        Nodeimple Start =(Nodeimple)cities.get(0);//The first element of the given sub - graph
        Start.setTAG2(Color.BLACK.getRGB());
        list.add(cities.get(0));
        List<NodeData> res = new ArrayList<>();
        for(int i=1; i < cities.size(); i++){
            Nodeimple curr = (Nodeimple) cities.get(i);
            res = shortestPath(Start.getKey(), curr.getKey());
            List<Nodeimple> copylist = new ArrayList<>();
            for (NodeData node : res){
               copylist.add((Nodeimple) node);
            }
            if(curr.getTAG2() !=Color.BLACK.getRGB() ) {//Not visited yet
                    for (Nodeimple node : copylist) {
                        if (node.getKey() != Start.getKey()) {
                            node.setTAG2(Color.BLACK.getRGB());//Mark as visited
                            list.add(node);
                        }
                    }
                Start = curr;
            }
        }
        return list;
    }


    @Override
    public boolean save(String file) {
        FileWriter jsonfile;

       Iterator<EdgeData> itr2=g.edgeIter();
        JSONObject object=new JSONObject();
        JSONArray edgearray =new JSONArray();
        while (itr2.hasNext()){
         EdgeData edgeitr=itr2.next();
            JSONObject edge = new JSONObject();

            edge.put("src",edgeitr.getSrc() + "");
            edge.put("dest",edgeitr.getDest() + "");
            edge.put("w",edgeitr.getWeight() + "");

            edgearray.add(edge);
        }
        Iterator<NodeData> itr=g.nodeIter();
        JSONArray nodearray=new JSONArray();
        while (itr.hasNext()){
            NodeData nodeitr=itr.next();
            JSONObject node = new JSONObject();

            node.put("key_id",nodeitr.getKey() + "");
            node.put("pos",nodeitr.getLocation().x()+ "|" + nodeitr.getLocation().y() + "|" +nodeitr.getLocation().z());
            node.put("w",nodeitr.getWeight() + "");

            nodearray.add(node);
        }

        try{
            jsonfile=new FileWriter(file);
            JSONObject obj=new JSONObject();
            obj.put("nodes",nodearray);
            obj.put("edges",edgearray);
            jsonfile.write(obj.toString());
        } catch (IOException e) {
           return false;
        }

        return true;
    }

    public static DirectedWeightedGraph getGrapg(String json_file) {
        DirectedWeightedGraph g;
        DirectedWeightedGraphImpl n = new DirectedWeightedGraphImpl();
        try {
            g = new DirectedWeightedGraphImpl(json_file);
        } catch (Exception e) {
            e.printStackTrace();
            return n;
        }
        return g;
    }

    @Override
    public boolean load(String file) {
        try {
            DirectedWeightedGraph G = getGrapg(file);
            this.g=G;
        }
        catch(Exception Exc){return false;}
        return true;
    }

}
