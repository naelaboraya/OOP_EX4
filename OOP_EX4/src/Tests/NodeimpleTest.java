package Tests;

import GraphPack.Nodeimple;
import GraphPack.geo_loc;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class NodeimpleTest {
    Nodeimple node1 = new Nodeimple(1, new geo_loc(0, 0, 0));
    Nodeimple node2 = new Nodeimple(2, new geo_loc(1.0, 0.0, 0.0));
    Nodeimple node3 = new Nodeimple(5, ("6,2,0"));

    @Test
    void getKey() {
        int id1 = 1;
        int id2 = 2;
        int id3 = 5;
        assertEquals(id1, node1.getKey());
        assertEquals(id2, node2.getKey());
        assertEquals(id3, node3.getKey());
    }

    @Test
    void getLocation() {
        double x = 0, y = 0, x2 = 1, y2 = 0, x3 = 6, y3 = 2;
        assertEquals(x, node1.getLocation().x());
        assertEquals(y, node1.getLocation().y());
        assertEquals(x2, node2.getLocation().x());
        assertEquals(y2, node2.getLocation().y());
        assertEquals(x3,node3.getLocation().x());
        assertEquals(y3,node3.getLocation().y());
    }
}