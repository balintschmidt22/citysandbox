package model;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Test class for Fields.
 * @author Bálint
 */
public class FieldTest {
    /**
     * Test of getPosX method, of class Field.
     */
    @Test
    public void test1() {
        int x = 1;
        int y = 3;
        Field field = new Field(x,y);
        assertEquals(x, field.getPosX());
        assertEquals(y, field.getPosY());
    }
    
    /**
     *
     */
    @Test
    public void test2() {
        int x = 1;
        int y = 1;
        Field field = new Field(x,y);
        assertEquals(true, field.deletability());
        field.setUndeletable();
        assertEquals(false, field.deletability());
    }

    /**
     *
     */
    @Test
    public void test3() {
        int x = 1;
        int y = 1;
        Field field = new Field(x,y);
        assertEquals(false, field.isConnectedToMainRoad());
        field.connectToMainRoad();
        assertEquals(true, field.isConnectedToMainRoad());
    }
    
    /**
     *
     */
    @Test
    public void test4() {
        int x = 1;
        int y = 1;
        Field field = new Field(x,y);
        assertEquals("noimage", field.image);
        field.setImage("test");
        assertEquals("test", field.image);
    }
}
