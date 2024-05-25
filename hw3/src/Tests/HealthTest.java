package src.Tests;

import org.junit.Before;
import org.junit.Test;
import src.BusinessLayer.Health;

import static org.junit.Assert.*;

public class HealthTest {

    private Health health;
    @Before
    public void setUp() throws Exception {
        health = new Health(50,50);
    }

    @Test
    public void setPool() {
        assertTrue(health.setPool(100));
        assertEquals(health.getPool(),100);
    }

    @Test
    public void setAmount() {
        health.setAmount(30);
        assertEquals(health.getAmount(),30);
        health.setAmount(-30);
        assertEquals(health.getAmount(),0);
    }

    @Test
    public void reduceAmount() {
        health.reduceAmount(40);
        assertEquals(health.getAmount(),10);
        health.reduceAmount(40);
        assertEquals(health.getAmount(),0);
    }

    @Test
    public void setHealthOnLevelUp() {
        health.setHealthOnLevelUp(2);
        assertEquals(health.getAmount(),health.getPool());
        assertEquals(health.getPool(),70);

    }

    @Test
    public void isAlive() {
        assertTrue(health.isAlive());
        health.setAmount(0);
        assertFalse(health.isAlive());
    }
}