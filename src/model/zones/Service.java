package model.zones;

import java.util.ArrayList;
import model.pop.Person;

/**
 * Class for the Service zone
 * @author Arni
 */
public class Service extends Zone {
    
    private ArrayList<Person> workers;
    
    /**
     *
     * @param x
     * @param y
     */
    public Service(int x, int y) {
        super(x, y);
        this.workers = peopleInZone;
        this.taxMultiplier = 18;
        this.buildingCost = 450;
        
        //This will specify which image the resourceloader class has to draw
        this.image = "serviceSelected";
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
        this.upgradeState--;

        updateState();
    }
    
    /**
     *
     * @return
     */
    @Override
    public boolean isService() {
        return true;
    }
    
    /**
     * Manages the upgrading costs, capacity and sprite of the building
     */
    public void updateState() {
        switch (upgradeState) {
            case 1:
                image = "service_1";
                buildingCost = 750;
                capacity = 10;
                break;
            case 2:
                image = "service_2";
                buildingCost = 1000;
                capacity = 20;
                break;
            case 3:
                image = "service_3";
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
                return "serviceSelected";
            }
            else {
                updateState();
                return image;
            }            
        }        
    }
}
