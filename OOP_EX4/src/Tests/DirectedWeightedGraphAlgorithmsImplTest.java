package Tests;

import GraphPack.*;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DirectedWeightedGraphAlgorithmsImplTest {
    DirectedWeightedGraph Graph = new DirectedWeightedGraphImpl();
    DirectedWeightedGraph Graph2 = new DirectedWeightedGraphImpl("data/G1.json");
    DirectedWeightedGraph Graph3 = new DirectedWeightedGraphImpl("data/My_graph_example_1.json");
    DirectedWeightedGraph Graph4 = new DirectedWeightedGraphImpl("data/My_graph_example_2.json");
    DirectedWeightedGraph Graph5 = new DirectedWeightedGraphImpl("data/My_graph_example_3.json");

    GeoLocation GL1 = new geo_loc(1,2,0);
    GeoLocation GL2 = new geo_loc(5,7,0);
    GeoLocation GL3 = new geo_loc(12,23,0);
    GeoLocation GL4 = new geo_loc(1,9,0);
    GeoLocation GL5 = new geo_loc(1,10,0);

    NodeData N1= new Nodeimple(1,GL1);
    NodeData N2= new Nodeimple(2,GL2);
    NodeData N3= new Nodeimple(3,GL3);
    NodeData N4= new Nodeimple(4,GL4);
    NodeData N5= new Nodeimple(5,GL5);
    @Test
    void init() {
     DirectedWeightedGraphAlgorithms g = new DirectedWeightedGraphAlgorithmsImpl();
     DirectedWeightedGraphAlgorithms g2 = new DirectedWeightedGraphAlgorithmsImpl();
     g.init(Graph);
     DirectedWeightedGraph G = g.getGraph();
     assertEquals(Graph,G);
     assertNotEquals(Graph,g2.getGraph());

    }

    @Test
    void copy() {
        DirectedWeightedGraphAlgorithms g = new DirectedWeightedGraphAlgorithmsImpl();

        g.init(Graph3);

        DirectedWeightedGraph copy_graph = g.copy();

        assertNotNull(copy_graph);//to check if its not null and actually copied the other graph

        assertEquals(Graph3.nodeSize(),copy_graph.nodeSize());//checks if copied the same nodes

        assertEquals(Graph3.edgeSize(),copy_graph.edgeSize());//chcks if copied the same edges
    }

    @Test
    void isConnected() {
        DirectedWeightedGraphAlgorithms g = new DirectedWeightedGraphAlgorithmsImpl();
        DirectedWeightedGraphAlgorithms g2 = new DirectedWeightedGraphAlgorithmsImpl();
        DirectedWeightedGraphAlgorithms g3 = new DirectedWeightedGraphAlgorithmsImpl();
        DirectedWeightedGraphAlgorithms g4 = new DirectedWeightedGraphAlgorithmsImpl();

        Graph.addNode(N1);
        Graph.addNode(N2);
        Graph.addNode(N3);
        Graph.addNode(N4);
        Graph.connect(N1.getKey(), N2.getKey(),77);
        Graph.connect(N2.getKey(), N4.getKey(),77);
        Graph.connect(N4.getKey(), N3.getKey(),77);
        Graph.connect(N3.getKey(), N1.getKey(),77);

        g.init(Graph);
        boolean ans = g.isConnected();//should return true
        assertTrue(ans);
        Graph.removeEdge(4,3);
        g.init(Graph);
        boolean ans1=g.isConnected();//should return false
        assertFalse(ans1);
        // We init the graphs (As DirectedWeightedGraphAlgorithms) so we can do algorithms on them
        g2.init(Graph2);
        g3.init(Graph3);
        g4.init(Graph4);
        assertTrue(g2.isConnected());
        assertTrue(g3.isConnected());
        assertFalse(g4.isConnected());
    }

    @Test
    void shortestPathDist() {
        DirectedWeightedGraphAlgorithms g = new DirectedWeightedGraphAlgorithmsImpl();


        Graph.addNode(N1);
        Graph.addNode(N2);
        Graph.addNode(N3);
        Graph.addNode(N4);
        Graph.addNode(N5);
        Graph.connect(N1.getKey(), N2.getKey(),1);
        Graph.connect(N2.getKey(), N4.getKey(),2);
        Graph.connect(N4.getKey(), N3.getKey(),3);
        Graph.connect(N3.getKey(), N1.getKey(),4);
        Graph.connect(N4.getKey(), N5.getKey(),5);
        Graph.connect(N5.getKey(), N3.getKey(),6);

        g.init(Graph);

        double dist = g.shortestPathDist(4,1);//should return 7 which is the shortest path dest
        assertEquals(7,dist);
        Graph.connect(4,2,2);
        Graph.connect(2,1,1);
        g.init(Graph);
        double dist2 = g.shortestPathDist(4,1);//should return 3
        assertEquals(3,dist2);
        NodeData N6 = new Nodeimple(6,"64,0,0");
        Graph.addNode(N6);
        g.init(Graph);
        double dist3 = g.shortestPathDist(4,6);//No such path -> should return -1
        assertEquals(-1,dist3);


    }

    @Test
    void shortestPath() {
        DirectedWeightedGraphAlgorithms g = new DirectedWeightedGraphAlgorithmsImpl();
        DirectedWeightedGraphAlgorithms g2 = new DirectedWeightedGraphAlgorithmsImpl();

        Graph.addNode(N1);
        Graph.addNode(N2);
        Graph.addNode(N3);
        Graph.addNode(N4);
        Graph.addNode(N5);
        Graph.connect(N1.getKey(), N2.getKey(),1);
        Graph.connect(N2.getKey(), N4.getKey(),2);
        Graph.connect(N4.getKey(), N3.getKey(),3);
        Graph.connect(N3.getKey(), N1.getKey(),4);
        Graph.connect(N4.getKey(), N5.getKey(),5);
        Graph.connect(N5.getKey(), N3.getKey(),6);

        g.init(Graph);
        List<NodeData> ans  = new ArrayList<>();
        ans=g.shortestPath(4,1);
        assertEquals(3,ans.size());
        System.out.println( g.shortestPath(4,1));//should print {4,3,1}
        List<NodeData> ans2  = new ArrayList<>();
        g2.init(Graph5);
        ans2 = g2.shortestPath(1,2);
        String string_list = ans2.toString();
        assertEquals("[1, 3, 4, 5, 2]",string_list);//the shortest path depends on weights so it should return this list

    }

    @Test
    void center() {
        DirectedWeightedGraphAlgorithms g = new DirectedWeightedGraphAlgorithmsImpl();
        Graph.addNode(N1);
        Graph.addNode(N2);
        Graph.addNode(N3);
        Graph.addNode(N4);
        Graph.addNode(N5);

        Graph.connect(N1.getKey(), N2.getKey(),1);
        Graph.connect(N2.getKey(), N4.getKey(),2);
        Graph.connect(N4.getKey(), N3.getKey(),3);
        Graph.connect(N3.getKey(), N1.getKey(),4);
        Graph.connect(N4.getKey(), N5.getKey(),5);
        Graph.connect(N5.getKey(), N3.getKey(),6);
        g.init(Graph);
        NodeData center=g.center();
        assertEquals(N1,center);//should return 1 if true

    }

    @Test
    void tsp() {
        List<NodeData> cities = new ArrayList<>();
        List<NodeData> answer = new ArrayList<>();
        DirectedWeightedGraphAlgorithms g = new DirectedWeightedGraphAlgorithmsImpl();
        g.init(Graph5);
        NodeData n1 = new Nodeimple(1,"862.0,248.0,150.0");
        NodeData n5 = new Nodeimple(5,"792.0,211.0,150.0") ;
        cities.add(n1);
        cities.add(n5);
        answer = g.tsp(cities);
       String string_test="[1, 3, 4, 5]";
       assertEquals(string_test,answer.toString());
    }
}