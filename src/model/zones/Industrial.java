package model.zones;

import java.util.ArrayList;
import model.pop.Person;

/**
 * Class for the industrial zone
 * @author Arni
 */
public class Industrial extends Zone {
    private ArrayList<Person> workers;
    
    /**
     *
     * @param x
     * @param y
     */
    public Industrial(int x, int y) {
        super(x, y);
        this.workers = peopleInZone;
        this.taxMultiplier = 18;
        this.buildingCost = 400;
        currentTax=tax;
  
        //This will specify which image the resourceloader class has to draw
        this.image = "industrialSelected";
    }
    
    /**
     *
     * @return
     */
    @Override
    public boolean isIndustrial() {
        return true;
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
     * Not implemented (not needed)
     */
    public void downgrade() {
        this.upgradeState--;

        updateState();
    }
    
    /**
     * Manages the upgrading costs, capacity and sprite of the building
     */
    public void updateState() {
        switch (upgradeState) {
            case 1:
                image = "industrial_1";
                buildingCost = 750;
                capacity = 10;
                break;
            case 2:
                image = "industrial_2";
                buildingCost = 1000;
                capacity = 20;
                break;
            case 3:
                image = "industrial_3";
                capacity = 30;
                break;
            default:
                image = "noimage";
        }
    }
    
    /**
     *
     * @param person
     */
    public void addWorker(Person person) {
        workers.add(person);
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
                return "industrialSelected";
            }
            else {
                updateState();
                return image;
            }            
        }
    }
}
