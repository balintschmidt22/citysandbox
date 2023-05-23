package model.zones;

import java.util.ArrayList;
import model.Field;
import model.pop.Person;

/**
 * Class for the Residential zone
 * @author Arni
 */
public class Residential extends Zone {
    // This variable will determine if the populating algorithm is going to include
    // this building 
    
    // This will contain a list of connected workplaces (along roads)
    private ArrayList<Field> connectedWorkplaces;
    private ArrayList<Field> connectedEducation;
    
    
    // This will contain the residents (People class)
    private ArrayList<Person> residents;
    
    /**
     *
     * @param x
     * @param y
     */
    public Residential(int x, int y) {
        super(x, y);
        this.buildingCost = 300;
        this.residents = peopleInZone;
        this.taxMultiplier = 50;
        
        //This will specify which image the resourceloader class has to draw
        this.image = "residentialSelected";
    }
    
    /**
     * Upgrades the building by one state (metropolis)
     * @return
     */
    @Override
    public boolean upgrade() {
        boolean success = false;
        if (upgradeState != 3) {
            this.upgradeState++;
            updateState();
            success = true;
        }
        return success;
    }
    
    /**
     *
     */
    public void downgrade() {
        if (this.upgradeState != 1) {
            this.upgradeState--;
        }
        else {
            System.out.println("Cannot downgrade this building!");
        }

        updateState();
    }
    
    /**
     *  Manages the upgrading costs, capacity and sprite of the building
     */
    public void updateState() {
        switch (upgradeState) {
            case 1:
                image = "residential_1";
                buildingCost = 750;
                capacity = 10;
                break;
            case 2:
                image = "residential_2";
                buildingCost = 1000;
                capacity = 20;
                break;
            case 3:
                image = "residential_3";
                capacity = 30;
                break;
            default:
                image = "noimage";
        }
    }
    
    /**
     *
     * @return
     */
    @Override
    public boolean isResidential() {
        return true;
    }
    
    /**
     *
     * @param workplace
     */
    public void connectWorkplace(Zone workplace) {
        connectedWorkplaces.add(workplace);
    }
    
    /**
     * 
     * @param edu 
     */
    public void connectEducation(Field edu){
        connectedEducation.add(edu);
    }
    
    /**
     * 
     * @return 
     */
    public ArrayList<Field> getConnectedWorkplaces() {
        return connectedWorkplaces;
    }
    
    /**
     * 
     * @return 
     */
    public ArrayList<Field> getConnectedEducation() {
        return connectedEducation;
    }
    
    /**
     * 
     * @param list 
     */
    public void setConnectedWorkplaces(ArrayList<Field> list) {
        connectedWorkplaces = list;
    }
    
    /**
     * 
     * @param list 
     */
    public void setConnectedEducation(ArrayList<Field> list){
        connectedEducation = list;
    }
    
    /**
     * 
     * @param person 
     */
    public void addResident(Person person) {
        residents.add(person);
        updateCurrentPeople();
        setTax();
    }
    
    /**
     *
     * @return
     */
    @Override
    public String getImage() {
        if (damaged) {
            return damagedImage;
        }
        else {
            if (currentPeople == 0) {
                return "residentialSelected";
            }
            else {
                updateState();
                return image;
            }    
        }
    }

    /**
     *
     * @return
     */
    public ArrayList<Person> getResidents() {
        return residents;
    }
}
