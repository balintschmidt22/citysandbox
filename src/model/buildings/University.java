package model.buildings;

import java.util.ArrayList;
import model.pop.Person;

/**
 * Implementing education feature, university
 * @author Arni
 */
public class University extends SpecialBuilding {

    /**
     * Needed for 2x2 building
     */
    public boolean topLeft = false;

    /**
     * Needed for 2x2 building, helps at removing
     */
    public University main;
    private ArrayList<Person> students;
      private int capacity;
    private int currentPeople;
    
    /**
     *
     * @param x
     * @param y
     */
    public University(int x, int y) {
        super(x, y);
        this.buildingCost = 3500;
        this.image = "university";
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
    public int getCapacity(){
        return this.capacity;
    }

    /**
     *
     * @return
     */
    public boolean isTopLeft() {
        return topLeft;
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
