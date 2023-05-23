package model;

import model.buildings.School;
import model.buildings.Stadium;
import model.buildings.University;
import model.zones.Residential;
import model.nature.Grass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Test class for GameEngine
 * @author Bálint
 */
public class GameEngineTest {
    /**
     *
     */
    @Test
    public void test1(){
        GameEngine engine = new GameEngine();
        int money = 1000;
        engine.setMoney(money);
        
        assertEquals(money, engine.getMoney());
    }
    
    /**
     * Testing removable function.
     */
    @Test
    public void test2(){
        GameEngine engine = new GameEngine();
        assertEquals(false, engine.removable(1, 1));
        assertEquals(true, engine.removable(10, 10));
    }
    
    /**
     * Testing building.
     */
    @Test
    public void testBuild(){
        GameEngine engine = new GameEngine();
        Residential res = new Residential(10, 10);
        engine.setField(res, 10, 10);
        assertEquals(res, engine.getFieldAt(10, 10));
    }
    
    /**
     * Testing 2x2 building.
     */
    @Test
    public void test3(){
        GameEngine engine = new GameEngine();
        Stadium st = new Stadium(10, 10);
        st.topLeft = true;
        engine.setField(st, 10, 10);
        assertEquals(st, engine.getFieldAt(10, 10));
        engine.remove2x2(10, 10, st);
        assertTrue(engine.getFieldAt(10, 10) instanceof Grass);
    }
    
    /**
     * Testing 2x2 building.
     */
    @Test
    public void test4(){
        GameEngine engine = new GameEngine();
        University uni = new University(10, 10);
        uni.topLeft = true;
        engine.setField(uni, 10, 10);
        assertEquals(uni, engine.getFieldAt(10, 10));
        engine.remove2x2(10, 10, uni);
        assertTrue(engine.getFieldAt(10, 10) instanceof Grass);
    }
    
    /**
     * Testing 2x1 building.
     */
    @Test
    public void test5(){
        GameEngine engine = new GameEngine();
        School sc = new School(10, 10);
        sc.left = true;
        engine.setField(sc, 10, 10);
        assertEquals(sc, engine.getFieldAt(10, 10));
        engine.remove2x2(10, 10, sc);
        assertTrue(engine.getFieldAt(10, 10) instanceof Grass);
    }
    
    /**
     *
     */
    @Test
    public void test6(){
        GameEngine engine = new GameEngine();
        assertEquals(1, engine.roadList.size());
        assertTrue(engine.roadList.get(0).isMainRoad());
    }
}
