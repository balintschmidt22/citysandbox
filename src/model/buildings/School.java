package model.buildings;

import java.util.ArrayList;
import model.pop.Person;

/**
 * Class for education feature, implementing school
 * @author Arni
 */
public class School extends SpecialBuilding {

    /**
     * Needed for building 2x1 size in the game
     */
    public boolean left = false;

    /**
     * Needed for building 2x1 size in the game
     */
    public School main;
    private ArrayList<Person> students;
    private int capacity;
    private int currentPeople;
    
    /**
     *
     * @param x
     * @param y
     */
    public School(int x, int y) {
        super(x, y);
        this.buildingCost = 2000;
        this.image = "school";
        students = new ArrayList<>();
        this.capacity=10;
        currentPeople=0;
    }

    /**
     *
     * @return
     */
    public int getCurrentPeople(){
        return this.currentPeople;
    }

    /**
     *
     * @return
     */
    public boolean isLeft() {
        return left;
    }

    /**
     *
     * @return
     */
    public int getCapacity(){
        return this.capacity;
    }
    
    /**
     *
     * @return
     */
    @Override
    public int getUpkeep() {
        return main.upkeep;
    }
    
    /**
     *
     * @return
     */
    @Override
    public boolean isConnectedToMainRoad() {
        return main.isConnectedToMainRoad;
    }
    
    /**
     *
     */
    @Override
    public void connectToMainRoad() {
        isConnectedToMainRoad = true;
        main.isConnectedToMainRoad = true;
    }
    
    /**
     *
     */
    @Override
    public void disconnectFromMainRoad() {
        isConnectedToMainRoad = false;
        main.isConnectedToMainRoad = false;
    }
    
    /**
     *
     * @param person
     */
    public void addStudent(Person person) {
        students.add(person);
        currentPeople++;
    }
    
    /**
     *
     * @param person
     */
    public void removeStudent(Person person){
        students.remove(person);
        currentPeople--;
    }
    
    /**
     *
     * @return
     */
    public ArrayList<Person> getStudents(){
        return this.students;
    }
}
