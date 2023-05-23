package model.pop;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import model.buildings.School;
import model.buildings.University;
import model.zones.Residential;
import model.zones.Zone;

/**
 * Class to implement a person in the game.
 * @author Arni
 */
public class Person {

    /**
     *
     */
    protected int age;
    private int happiness;

    /**
     * Pension
     */
    public boolean hasPension;
    private int avgTax20;
    private boolean hasHome;
    private boolean hasWork;
    private boolean alive;

    /**
     *
     */
    protected Residential home;
    protected Zone workPlace;

    /**
     * Education
     */
    public int educationLevel;
    protected School school;
    protected University university;
    
    /**
     *
     */
    public Person() {
        age = ThreadLocalRandom.current().nextInt(18, 60);
        happiness = 50;
        hasPension = false;
        avgTax20 = 0;
        alive = true;
        hasHome = false;
        hasWork = false;
        home = null;
        educationLevel=0;
    }
    public School getSchool(){
        return school;
    }
    public University getUniversity(){
        return university;
    }
    public void setSchool(School s){
        this.school=s;
    }
    public void setUni(University u){
        this.university=u;
    }
    
    /**
     *
     */
    public void assignedToHome() {
        hasHome = true;
    }

    /**
     *
     * @return
     */
    public boolean hasHome() {
        return hasHome;
    }

    /**
     *
     */
    public void assignToWork() {
        hasWork = true;
    }

    /**
     *
     * @return
     */
    public boolean hasWork() {
        return hasWork;
    }
    
    /**
     *
     * @param residential
     */
    public void moveIn(Residential residential) {
        home = residential;
    }

    /**
     *
     * @return
     */
    public Residential getHome() {
        return home;
    }

    /**
     *
     * @return
     */
    public int getHappiness() {
        return happiness;
    }

    /**
     *
     * @param happiness
     */
    public void setHappiness(int happiness) {
        if(happiness <= 0){
            this.happiness = 0;
        }else if(happiness >= 100){
            this.happiness = 100;
        }else{
            this.happiness = happiness;
        }
    }

    /**
     *
     * @return
     */
    public int getAge() {
        return age;
    }

    /**
     * Sets the age of a person, and also changes variables regarding the pension feature, and rolls a random number to kill the person too
     * @param age
     */
    public void setAge(int age) {
        this.age = age;
        if(age > 45 && age < 65){
            if(hasWork){
                avgTax20 += workPlace.getTaxMultiplier();
            }
            if(hasHome){
                avgTax20 += home.getTaxMultiplier();
            }
        }else if(age == 65){
            hasPension = true;
            if(hasWork){
                workPlace.getPeopleInZone().remove(this);
                workPlace.updateCurrentPeople();
                workPlace.setTax();
                hasWork = false;
            }
            avgTax20 = (int)0.5*avgTax20/20;
            home.setTax();
        }else if(age > 95){
            if(hasHome){
                home.getResidents().remove(this);
                home.updateCurrentPeople();
                home.setTax();
                alive = false;
            }
        }
        else if(age > 65){
            Random random = new Random();
            int num = random.nextInt(100-age);
            if(num == 1){
                if(hasHome){
                    home.getResidents().remove(this);
                    home.updateCurrentPeople();
                    home.setTax();
                    alive = false;
                }
            }
        }
    }

    /**
     *
     * @param workPlace
     */
    public void setWorkPlace(Zone workPlace) {
        this.workPlace = workPlace;
    }
    
    public void setEducation(int level){
        this.educationLevel=level;
    }
    public String getEducation(){
        if (this.educationLevel==0){
            return "Uneducated";
        }
        if (this.educationLevel==1){
            return "HighSchool";
        }
        if (this.educationLevel==2){
            return "University";
        }
        return "xd";
    }
    

    /**
     *
     * @return
     */
    public Zone getWorkPlace() {
        return workPlace;
    }

    /**
     *
     * @return
     */
    public int getAvgTax20() {
        return avgTax20;
    }

    /**
     *
     * @return
     */
    public boolean isAlive() {
        return alive;
    }
}