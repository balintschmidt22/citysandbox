package model.zones;

import model.pop.Person;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Test class for Residential
 * @author Bálint
 */
public class ResidentialTest {
    /**
     *
     */
    @Test
    public void test1() {
        Residential res = new Residential(1, 1);
        res.upgradeState = 1;
        res.updateState();
        assertEquals(10, res.capacity);
        res.upgradeState = 2;
        res.updateState();
        assertEquals(20, res.capacity);
        res.upgradeState = 3;
        res.updateState();
        assertEquals(30, res.capacity);
    }
    
    /**
     *
     */
    @Test
    public void test2() {
        Residential res = new Residential(1, 1);
        res.addResident(new Person());
        res.addResident(new Person());
        res.addResident(new Person());
        assertEquals(3, res.getResidents().size());
    }
    
    /**
     *
     */
    @Test
    public void test3() {
        Residential res = new Residential(1, 1);
        assertTrue(res.isResidential());
    }
}