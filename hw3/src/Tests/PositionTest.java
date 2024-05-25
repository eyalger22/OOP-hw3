package src.Tests;

import org.junit.Before;
import org.junit.Test;
import src.BusinessLayer.Health;
import src.BusinessLayer.Position;

import static org.junit.Assert.*;

public class PositionTest {

    private Position p1;
    private Position p11;

    private Position p2;

    @Before
    public void setUp() throws Exception {
        p1 = new Position(10,5);
        p11 = new Position(10,5);

        p2 =  new Position(0,0);
    }

    @Test
    public void getX() {
        assertEquals(p1.getX(),10);
        assertEquals(p2.getX(),0);
    }

    @Test
    public void getY() {
        assertEquals(p1.getY(),5);
        assertEquals(p2.getY(),0);
    }

    @Test
    public void compareTo() {
        int c = p1.compareTo(p2);
        assertEquals(c,1);
        c = p2.compareTo(p1);
        assertEquals(c,-1);
        c = p1.compareTo(p11);
        assertEquals(c,1);
    }

    @Test
    public void range() {
        double range = p1.range(p2);
        assertEquals(11.18,range,0.01);

    }
}