package model;

import model.nature.Grass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Test class for Board
 * @author Bálint
 */
public class BoardTest {
    /**
     *
     */
    @Test
    public void test1() {
        Board board = new Board(10,20);
        assertEquals(10, board.getSizeX());
    }
    
    /**
     *
     */
    @Test
    public void test2() {
        Board board = new Board(10,20);
        assertEquals(20, board.getSizeY());
    }
    
    /**
     *
     */
    @Test
    public void test3() {
        Board board = new Board(12,20);
        Grass grass = new Grass(1,1);
        Grass grass2 = new Grass(10,8);
        board.putIntoFields(grass, 1, 1);
        board.putIntoFields(grass2, 10, 8);
        assertEquals(grass, board.getFieldAt(1, 1));
        assertEquals(grass2, board.getFieldAt(10, 8));
    }   
}
