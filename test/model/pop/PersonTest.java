package model.pop;

import model.GameEngine;
import model.zones.Industrial;
import model.zones.Residential;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Test class for Person
 * @author Bálint
 */
public class PersonTest {
    /**
     *
     */
    @Test
    public void test1(){
        Person p = new Person();
        assertTrue(p.getAge() >= 18);
        assertTrue(p.getAge() <= 60);
    }
    
    /**
     *
     */
    @Test
    public void test2(){
        Person p = new Person();
        p.assignedToHome();
        assertTrue(p.hasHome());
    }
    
    /**
     *
     */
    @Test
    public void test3(){
        Person p = new Person();
        p.assignToWork();
        assertTrue(p.hasWork());
    }
    
    /**
     *
     */
    @Test
    public void test4(){
        Person p = new Person();
        Residential res = new Residential(1, 1);
        p.moveIn(res);
        p.assignedToHome();
        assertTrue(p.hasHome());
        assertEquals(res, p.getHome());
    }
    
    /**
     *
     */
    @Test
    public void test5(){
        Person p = new Person();
        p.setHappiness(10);
        assertEquals(10, p.getHappiness());
        p.setHappiness(0);
        assertEquals(0, p.getHappiness());
        p.setHappiness(-100);
        assertEquals(0, p.getHappiness());
        p.setHappiness(1000);
        assertEquals(100, p.getHappiness());
    }
    
    /**
     *
     */
    @Test
    public void test6(){
        Person p = new Person();
        GameEngine ge = new GameEngine();
        Pop pop = new Pop(ge);
        p.setAge(20);
        assertEquals(20, p.getAge());
    }
    
    /**
     *
     */
    @Test
    public void test7(){
        Person p = new Person();
        GameEngine ge = new GameEngine();
        Pop pop = new Pop(ge);
        Residential res = new Residential(1,1);
        p.home = res;
        p.setAge(65);
        assertEquals(true, p.hasPension);
    }
    
    /**
     *
     */
    @Test
    public void test8(){
        Person p = new Person();
        p.age = 20;
        GameEngine ge = new GameEngine();
        Pop pop = new Pop(ge);
        Residential res = new Residential(1,1);
        p.home = res;
        p.workPlace = new Industrial(1,2);
        p.assignToWork();
        assertEquals(true, p.hasWork());
        p.setAge(65);
        assertEquals(false, p.hasWork());
    }
}
