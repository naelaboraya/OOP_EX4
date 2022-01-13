package Tests;

import GraphPack.DirectedWeightedGraph;
import GraphPack.DirectedWeightedGraphImpl;
import GraphPack.GeoLocation;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class geo_locTest {
    DirectedWeightedGraph G = new DirectedWeightedGraphImpl("data/My_graph_example_1.json");// A graph from json file , has 5 nodes
    @Test
    void x() {
        double x = G.getNode(1).getLocation().x();
        assertTrue(620.0==x);


    }

    @Test
    void y() {
        double y = G.getNode(1).getLocation().y();
        assertTrue(193.0==y);
    }

    @Test
    void _test(){
        GeoLocation gl3 = G.getNode(3).getLocation();
        String gl_str3 =  "242.0 , 112.0 , 598.0" ;

        assertTrue(gl_str3.equals(gl3.toString()));

        GeoLocation gl1 = G.getNode(1).getLocation();
        String gl_str1 =  "620.0 , 193.0 , 842.0" ;

        assertTrue(gl_str1.equals(gl1.toString()));

        GeoLocation gl2 = G.getNode(2).getLocation();
        String gl_str2 =  "978.0 , 748.0 , 569.0" ;

        assertTrue(gl_str2.equals(gl2.toString()));

        GeoLocation gl5 = G.getNode(5).getLocation();
        String gl_str5 =  "502.0 , 790.0 , 485.0" ;

        assertTrue(gl_str5.equals(gl5.toString()));

        GeoLocation gl4 = G.getNode(4).getLocation();
        String gl_str4 =  "708.0 , 784.0 , 140.0" ;

        assertTrue(gl_str4.equals(gl4.toString()));



    }
}