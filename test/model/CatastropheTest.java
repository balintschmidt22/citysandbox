package model;

import model.zones.Industrial;
import model.zones.Residential;
import model.zones.Service;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Test class for Catastrophes
 * @author Bálint
 */
public class CatastropheTest {
    /**
     *
     */
    @Test
    public void test1() {
        GameEngine ge = new GameEngine();
        ge.residentialList.add(new Residential(1,1));
        ge.getCatastrophe().invokeDisaster(1);
        assertEquals(1, ge.damagedBuildings.size());
    }
    
    /**
     *
     */
    @Test
    public void test2() {
        GameEngine ge = new GameEngine();
        ge.residentialList.add(new Residential(1,1));
        ge.industrialList.add(new Industrial(2,1));
        ge.serviceList.add(new Service(3,1));
        ge.getCatastrophe().invokeDisaster(1);
        assertEquals(1, ge.damagedBuildings.size());
        ge.getCatastrophe().invokeDisaster(2);
        assertEquals(2, ge.damagedBuildings.size());
        ge.getCatastrophe().invokeDisaster(3);
        assertEquals(3, ge.damagedBuildings.size());
    }
    
}
