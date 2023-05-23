package model;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Test class for GraphSearch
 * @author Bálint
 */
public class GraphSearchTest {
    /**
     * Test of hasPathToMainRoad method, of class GraphSearch.
     */
    @Test
    public void test1() {
        int startX = 0;
        int startY = 0;
        GraphSearch instance = new GraphSearch();
        instance.board = new Board(15,15);
        boolean expResult = false;
        boolean result = instance.hasPathToMainRoad(startX, startY);
        assertEquals(expResult, result);
    }
    
    /**
     *
     */
    @Test
    public void test2() {
        GraphSearch instance = new GraphSearch();
        instance.board = new Board(15,15);
        assertEquals(true, instance.isValidPosition(0, 0));
        assertEquals(true, instance.isValidPosition(1, 1));
        assertEquals(false, instance.isValidPosition(16, 0));
    }
    
}
