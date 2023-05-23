package model.zones;

import java.util.ArrayList;
import model.Field;
import model.pop.Person;

/**
 * Abstract class for Zones.
 * @author Bálint
 */
public abstract class Zone extends Field{

    /**
     *
     */
    protected int currentPeople = 0;

    /**
     *
     */
    protected int capacity = 10;

    /**
     *
     */
    protected int upgradeState = 1;

    /**
     *
     */
    protected int currentTax=0;

    /**
     *
     */
    protected boolean hasRoad = false;

    /**
     *
     */
    protected int taxMultiplier;

    /**
     *
     */
    protected int tax = 0;

    /**
     *
     */
    protected ArrayList<Person> peopleInZone;

    
    //Attributes for upgrading the buildings

    /**
     *
     */
    protected double multiplier = 1.3;

    /**
     *
     * @param x
     * @param y
     */
    public Zone(int x, int y) {
        super(x, y);
        this.peopleInZone = new ArrayList<>();
    }

    /**
     * Sets the tax amount and manages the pension system as well
     */
    public void setTax() {
        this.tax=currentPeople*taxMultiplier;
        for(Person p : peopleInZone){
            if(p.hasPension){
                this.tax -= taxMultiplier;
                this.tax -= p.getAvgTax20();
            }
            if(p.educationLevel==1){
                this.tax+=taxMultiplier;
            }
            if(p.educationLevel==2){
                this.tax+=2*taxMultiplier;
            }
        }
        currentTax=this.tax;
    }
    
    /**
     *
     * @param x
     */
    public void changeHappiness(int x){
        for (Person person : peopleInZone){
            person.setHappiness(person.getHappiness()+x);
        }
    }
    
    /**
     *
     */
    public void updateCurrentPeople() {
        currentPeople = peopleInZone.size();
    }
    
    /**
     *
     * @return
     */
    public int getCapacity() {
        return capacity;
    }
    
    /**
     *
     * @return
     */
    public int getCurrentTax(){
        return currentTax;
    }

    /**
     *
     * @return
     */
    public int getCurrentPeople() {
        return currentPeople;
    }

    /**
     *
     * @return
     */
    @Override
    public boolean isConnectable() {
        return true;
    }
    
    /**
     * Sums the happiness of every resident or worker
     * @return Average happiness of tenants
     */
    public int getHappiness(){
        if(currentPeople == 0){
            return 0;
        }
        int sum = 0;
        for(Person p : peopleInZone){
            sum += p.getHappiness();
        }
        return (int)sum/currentPeople;
    }

    /**
     *
     * @param taxMultiplier
     */
    public void setTaxMultiplier(int taxMultiplier) {
        this.taxMultiplier = taxMultiplier;
    }

    /**
     *
     * @return
     */
    public int getTaxMultiplier() {
        return taxMultiplier;
    }

    /**
     *
     * @return
     */
    public int getTax() {
        return tax;
    }
    
    /**
     *
     * @return
     */
    public ArrayList<Person> getPeopleInZone() { 
        return peopleInZone;
    }
    
    /**
     *
     * @return
     */
    public boolean upgrade() {
        return false;
    }
    
    /**
     *
     * @return
     */
    public int getUpgradeState() {
        return upgradeState;
    }
    

}
