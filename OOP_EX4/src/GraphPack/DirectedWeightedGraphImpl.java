package GraphPack;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import java.io.File;
import java.io.FileReader;
import java.util.*;


public class DirectedWeightedGraphImpl implements DirectedWeightedGraph {
 private   HashMap<Integer, NodeData> nodes = new HashMap<>();
 private   HashMap<NodeData , Hashtable<Integer,EdgeData>> edges = new HashMap<>();
    private int modecounter;
 private int counter;



 public DirectedWeightedGraphImpl(){
     this.nodes=new HashMap<>();
     this.edges=new HashMap<>();
     this.modecounter=0;
 }

 public DirectedWeightedGraphImpl(DirectedWeightedGraph g){
    this.nodes= new HashMap<>();
    this.edges = new HashMap<>();//mesh m3mooool (zrd)
 }

    public DirectedWeightedGraphImpl(String file){

        this.nodes = new HashMap<>();
        this.edges = new HashMap<>();

        this.modecounter = 0;

        JSONParser jsonParser = new JSONParser();
        File file2 = new File(file);
        try(FileReader reader = new FileReader(file2)){

            Object obj =  jsonParser.parse(reader);
            JSONObject Node_Obj = (JSONObject) obj;

            JSONArray Nodes_arr= (JSONArray) Node_Obj.get("Nodes");
            JSONArray Edges_arr = (JSONArray) Node_Obj.get("Edges");

            for(Object n : Nodes_arr){
                json_obj_Node((JSONObject)n);
            }
            for(Object e : Edges_arr){
                json_obj_Edge((JSONObject)e);
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void json_obj_Edge(JSONObject edge) {
        try {
            if (edge != null) {
                int src = ((Long) edge.get("src")).intValue();
                int dest = ((Long) edge.get("dest")).intValue();
                double w = (double) edge.get("w");
                this.connect(src, dest, w);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    private void json_obj_Node(JSONObject node) {
        try {
            if (node != null) {
                int id = ((Long) node.get("id")).intValue();
                String pos = (String) node.get("pos");
                geo_loc g = new geo_loc(pos);
                Nodeimple n = new Nodeimple(id, g);
                this.addNode(n);
            }
        }catch(Exception e) {
            e.printStackTrace();
        }
    }

 public DirectedWeightedGraph init(){
     return  new DirectedWeightedGraphImpl();
 }
    @Override
    public NodeData getNode(int key) {
      return nodes.get(key);


    }

    @Override
    public NodeData getEdge(int src, int dest) {
        NodeData key= nodes.get(src);
        if(edges.get(key).get(dest)!= null)
            return nodes.get(src);
        else {
            return null;
        }

    }

    @Override
    public void addNode(NodeData n) {
        if(nodes.get(n.getKey()) == null) {
            nodes.put(n.getKey(), n);
            edges.put(n, new Hashtable<Integer, EdgeData>());
            modecounter+=1;
        }
    }

    @Override
    public void connect(int src, int dest, double w) {

        if(w < 0) System.out.println("the weight can not be negative");
        else {
            if(src!=dest) {

                NodeData key = nodes.get(src);
                NodeData desti = nodes.get(dest);

                if (key!= null&& desti!= null && edges.get(key).get(dest)== null) {
                    EdgeDataImpl newedge=new EdgeDataImpl(0,0,0);
                    newedge.setDst(dest);
                    newedge.setSrc(src);
                    newedge.setWeight(w);
                    EdgeData edge= newedge;
                    edges.get(key).put(dest, edge);
                    modecounter++;
                    counter++;
                }
                else {
                    throw new RuntimeException("error , src/dest does not exist");
                }
            }
            else {
                throw new RuntimeException("error , same nodes");

            }
        }
    }

    @Override
    public Iterator<NodeData> nodeIter() {
        return nodes.values().iterator();
    }

    @Override
    public Iterator<EdgeData> edgeIter() {
        HashMap<Integer,EdgeData> temp =new HashMap<>();
        int count=0;
       Iterator<NodeData> itr =nodeIter();
       while (itr.hasNext()){
           NodeData edege=itr.next();
           Iterator<EdgeData> ans =null;
                   ans=edgeIter(edege.getKey());
           while (ans.hasNext()){
               temp.put(count,ans.next());
               count++;
           }

       }
       return temp.values().iterator();
        }


    @Override
    public Iterator<EdgeData> edgeIter(int node_id) {
        NodeData data = nodes.get(node_id);
        return  edges.get(data).values().iterator();
    }


    @Override
    public NodeData removeNode(int key) {
        NodeData temp = nodes.get(key);
        if(temp== null) return null;
        //update the number of edges in the graph
        counter -= edges.get(temp).size();
        Set<NodeData> sets = edges.keySet();
        //remove edge when dest is the key
        for(NodeData tempR : sets) {
            edges.get(tempR).remove(key);
        }
        edges.remove(temp);
        nodes.remove(key);
        modecounter++;
        return temp;
    }


    @Override
    public EdgeData removeEdge(int src, int dest) {

        EdgeData ans = null;
        if (src != dest) {
            NodeData temp = nodes.get(src);
            NodeData temp2 = nodes.get(dest);
            if (temp != null && temp2 != null) {
                ans = edges.get(temp).remove(dest);
                if (ans != null) {
                    modecounter++;
                    counter--;
                } else {
                    return null;
                }

            }
        }
        return ans;
    }

    @Override
    public int nodeSize() {
        return nodes.size();
    }

    @Override
    public int edgeSize() {
        return counter;
    }


    @Override
    public int getMC() {
        return modecounter;
    }
}
