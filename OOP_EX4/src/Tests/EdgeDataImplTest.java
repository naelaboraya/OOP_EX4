package Tests;

import GraphPack.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EdgeDataImplTest {
    EdgeDataImpl e= new EdgeDataImpl(1,2,0.2);

    @Test
    void getSrc() {
        assertEquals(1,e.getSrc());
    }

    @Test
    void getDest() {
        assertEquals(2,e.getDest());
    }

    @Test
    void getWeight() {
        assertEquals(0.2,e.getWeight());
    }

    @Test
    void setSource(){//we changed the edge data
    e.setSrc(5);
    int s = e.getSrc();
    assertFalse(s==1);
    assertTrue(s==5);
    }

    @Test
    void setDest(){
        assertTrue(e.getDest()==2);
        e.setDst(100);
        int d = e.getDest();
        assertEquals(100,d);
        assertNotEquals(2,d);
    }

    @Test
    void setWeight(){
        assertEquals(0.2,e.getWeight());
        e.setWeight(55.3);
        double w = e.getWeight();
        assertNotEquals(0.2,w);
        assertEquals(55.3,w);
        assertNotEquals(2,w);

    }
}