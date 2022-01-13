package Tests;

import GraphPack.*;
import org.junit.jupiter.api.Test;

import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.*;

class DirectedWeightedGraphImplTest {

    DirectedWeightedGraph Graph = new DirectedWeightedGraphImpl();
    DirectedWeightedGraph Graph2 = new DirectedWeightedGraphImpl("data/My_graph_example_2.json");
    GeoLocation GL1 = new geo_loc(1,2,0);
    GeoLocation GL2 = new geo_loc(5,7,0);
    GeoLocation GL3 = new geo_loc(12,23,0);
    GeoLocation GL4 = new geo_loc(1,9,0);

    NodeData N1= new Nodeimple(1,GL1);
    NodeData N2= new Nodeimple(2,GL2);
    NodeData N3= new Nodeimple(3,GL3);
    NodeData N4= new Nodeimple(4,GL4);
    NodeData N5= new Nodeimple(5,GL1);
    NodeData N6= new Nodeimple(6,GL2);
    NodeData N7= new Nodeimple(7,GL3);
    NodeData N8= new Nodeimple(8,GL4);
    @Test
    void addNode() {
        Graph.addNode(N1);
        Graph.addNode(N4);
        Graph.addNode(N1);
        int nodes=Graph.nodeSize();
        String GG = N1.getLocation().toString();
        assertEquals("1.0 , 2.0 , 0.0",GG);
        assertEquals(2,nodes);
        assertFalse(nodes==3);
    }

    @Test
    void connect() {
        DirectedWeightedGraph d=new DirectedWeightedGraphImpl();
        d.addNode(N1);
        d.addNode(N2);
        d.addNode(N3);
        d.addNode(N4);
       d.connect(N1.getKey(),N2.getKey(),1);
       d.connect(N3.getKey(),N4.getKey(),1);
       d.connect(N4.getKey(),N3.getKey(),1);
       d.connect(N3.getKey(),N2.getKey(),1);


       assertEquals(4,d.edgeSize());
        Iterator<EdgeData> et = d.edgeIter();
        while (et.hasNext()){
            EdgeData nn=et.next();
            System.out.println(nn.getSrc()+" "+nn.getDest());
        }



        //Graph.connect(N2.getKey().N1.getKey(),2);
        ////////
        assertEquals(3,Graph2.edgeSize());//we will now add edges and check the edges size (to check "connect" again)
        Graph2.connect(0,2,16.5);
        Graph2.connect(0,3,19.55266);
        assertEquals(5,Graph2.edgeSize());//added two successfully
    }

    @Test
    void removeNode() {
        DirectedWeightedGraph a=new DirectedWeightedGraphImpl();
        a.addNode(N1);
        a.addNode(N2);
        assertEquals(2,a.nodeSize());
        a.removeNode(N2.getKey());
        assertEquals(1,a.nodeSize());
        NodeData n =a.removeNode(N2.getKey());
        assertNull(n);
    }

    @Test
    void removeEdge() {
        DirectedWeightedGraph g=new DirectedWeightedGraphImpl();
        g.addNode(N1);
        g.addNode(N2);
        g.addNode(N3);
        g.addNode(N4);
        g.connect(N1.getKey(), N2.getKey(),11);
        g.connect(N2.getKey(), N3.getKey(),13);
        g.connect(N4.getKey(), N3.getKey(),12);
        g.connect(N4.getKey(), N2.getKey(),15);
        int size_edges = g.edgeSize();
        assertEquals(4,size_edges);
        g.removeEdge(1,2);
        size_edges=g.edgeSize();
        assertEquals(3,size_edges);

        EdgeData E =  g.removeEdge(1,2);
        assertNull(E);
       EdgeData E2 = g.removeEdge(4,2);
        assertEquals(4,E2.getSrc());
        assertEquals(2,E2.getDest());
        GeoLocation gg =N1.getLocation();
        System.out.println(gg);


    }

    @Test
    void getNode(){
        NodeData testnode = new Nodeimple(0,"862.0,251.0,150.0");
        NodeData realnode = Graph2.getNode(0);
        assertEquals(testnode.getKey(),realnode.getKey());
    }

    @Test
    void getEdge(){
        EdgeData testedge = new EdgeDataImpl(0,1,502.14762763585395);
        NodeData n = new Nodeimple(0,"862.0,251.0,150.0");
        NodeData n2 = Graph2.getEdge(0,1);
        assertEquals(n.getKey(),n2.getKey());
    }
}