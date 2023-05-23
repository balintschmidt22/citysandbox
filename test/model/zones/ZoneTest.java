package model.zones;

import model.pop.Person;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Test class for Zone
 * @author Bálint
 */
public class ZoneTest {
    /**
     * Testing tax
     */
    @Test
    public void test1() {
        Residential res = new Residential(1,1);
        res.currentPeople = 5;
        res.taxMultiplier = 10;
        res.setTax();
        assertEquals(50,res.getTax());
    }
    
    /**
     *
     */
    @Test
    public void test2() {
        Residential res = new Residential(1,1);
        res.currentPeople = 0;
        assertEquals(0,res.getHappiness());
    }
    
    /**
     * Testing Metropolis function
     */
    @Test
    public void test3() {
        Residential res = new Residential(1,1);
        res.upgrade();
        assertEquals(2,res.upgradeState);
        res.upgrade();
        assertEquals(3,res.upgradeState);
    }
    
    /**
     *
     */
    @Test
    public void test4() {
        Residential res = new Residential(1,1);
        res.taxMultiplier = 8;
        res.setTax();
        assertEquals(0,res.getTax());
        res.addResident(new Person());
        assertEquals(1,res.getCurrentPeople());
        res.addResident(new Person());
        res.addResident(new Person());
        assertEquals(3,res.getCurrentPeople());
    }
    
    /**
     *
     */
    @Test
    public void test5() {
        Residential res = new Residential(1,1);
        res.currentPeople = 5;
        res.taxMultiplier = 8;
        res.setTax();
        assertEquals(40,res.getTax());
        res.addResident(new Person());
        res.setTaxMultiplier(10);
        res.setTax();
        assertEquals(10,res.getTax());
    }
    
    /**
     *
     */
    @Test
    public void test6() {
        Industrial ind = new Industrial(1,1);
        ind.currentPeople = 5;
        ind.taxMultiplier = 8;
        ind.setTax();
        assertEquals(40,ind.getTax());
        ind.addWorker(new Person());
        ind.setTaxMultiplier(10);
        ind.setTax();
        assertEquals(10,ind.getTax());
    }
    
    /**
     *
     */
    @Test
    public void test7() {
        Service ser = new Service(1,1);
        ser.currentPeople = 5;
        ser.taxMultiplier = 8;
        ser.setTax();
        assertEquals(40,ser.getTax());
        ser.addWorker(new Person());
        ser.setTaxMultiplier(10);
        ser.setTax();
        assertEquals(10,ser.getTax());
    }
    
    /**
     *
     */
    @Test
    public void test8() {
        Service ser = new Service(1,1);
        ser.addWorker(new Person());
        ser.upgrade();
        assertEquals("service_2",ser.getImage());
        assertEquals(20, ser.getCapacity());
        ser.upgrade();
        assertEquals("service_3",ser.getImage());
        assertEquals(30, ser.getCapacity());
    }
}
