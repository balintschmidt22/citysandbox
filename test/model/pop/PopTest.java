package model.pop;

import model.GameEngine;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Test class for Population
 * @author Bálint
 */
public class PopTest {

    /**
     *
     */
    @Test
    public void test1() {
        GameEngine ge = new GameEngine();
        Pop pop = new Pop(ge);
        pop.people.add(new Person());
        assertEquals(1, pop.people.size());
    }

    /**
     *
     */
    @Test
    public void test2() {
        GameEngine ge = new GameEngine();
        Pop pop = new Pop(ge);
        pop.people.add(new Person());
        pop.people.add(new Person());
        pop.people.add(new Person());
        assertEquals(3, pop.people.size());
    }

    /**
     *
     */
    @Test
    public void test3() {
        GameEngine ge = new GameEngine();
        Pop pop = new Pop(ge);
        pop.people.add(new Person());
        pop.people.add(new Person());
        pop.people.add(new Person());
        pop.changeGlobalHappiness(10);
        assertEquals(60, pop.people.get(0).getHappiness());
        assertEquals(60, pop.people.get(1).getHappiness());
        assertEquals(60, pop.people.get(2).getHappiness());
    }
    
    /**
     *
     */
    @Test
    public void test4() {
        GameEngine ge = new GameEngine();
        Pop pop = new Pop(ge);
        pop.people.add(new Person());
        pop.people.add(new Person());
        pop.people.add(new Person());
        pop.uneducated.add(pop.people.get(0));
        assertFalse(pop.educationLimit());
    }
}
