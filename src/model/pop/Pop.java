package model.pop;

import java.util.ArrayList;
import java.util.Random;
import model.GameEngine;
import model.buildings.School;
import model.buildings.SpecialBuilding;
import model.buildings.University;
import model.zones.Industrial;
import model.zones.Residential;
import model.zones.Service;
import model.zones.Zone;

/**
 * Used to manage all population-related logic within the game
 * @author Arni
 */

public class Pop {
    
    /**
     *
     */
    public ArrayList<Person> people;
    
    // This is necessary since people can move into the city while no workplaces are available

    /**
     *
     */
    public ArrayList<Person> unemployed;
    public ArrayList<Person> uneducated;
    
    private GameEngine gameEngine;
    
    /**
     *
     * @param gameEngine
     */
    public Pop (GameEngine gameEngine) {
        this.gameEngine = gameEngine;
        this.people = new ArrayList<>();
        this.unemployed = new ArrayList<>();
        this.uneducated = new ArrayList<>();
    }
    
    /**
     *
     */
    public void spawnPerson() {
        if (!gameEngine.residentialList.isEmpty()) {
            Person person = new Person();
            people.add(person);
            assignToHome(person);
        }
        
    }

    /**
     * Pension feature: spawns a 18 y.o. person
     */
    public void spawn18Person() {
        if (!gameEngine.residentialList.isEmpty()) {
            Person person = new Person();
            person.age = 18;
            people.add(person);
            assignToHome(person);
        }
    }
    
    /**
     * Ages citizens
     */
    public void age(){
        int count = 0;
        for(Person person : people){
            person.setAge(person.getAge()+1);
            if(!person.isAlive()){
                count++; 
            }
        }
        people.removeIf(p -> !p.isAlive());
        for(int i = 0; i < count; i++){
            spawn18Person();
        }
    }
    
    /**
     * If people are too unhappy, they have a chance to move out from the city
     */
    public void movingOut(){
        if (!gameEngine.residentialList.isEmpty() && !people.isEmpty()) {
            Random random = new Random();
            int randomIndex = random.nextInt(people.size());
            Person p = people.get(randomIndex);
            if(p.getHappiness() < 15 && !p.hasPension){
                int rand = random.nextInt(3);
                if(rand == 1){
                    if(p.hasHome()){
                        p.getHome().getResidents().remove(p);
                        p.getHome().updateCurrentPeople();
                    }
                    if(p.hasWork()){
                        p.getWorkPlace().getPeopleInZone().remove(p);
                        p.getWorkPlace().updateCurrentPeople();
                    }
                    if (p.educationLevel==1){
                        p.getSchool().removeStudent(p);
                    }
                    if (p.educationLevel==2){
                        p.getUniversity().removeStudent(p);
                    }
                    people.remove(p);
                }
            }
        }
    }
    
    // WIP method, good as a placeholder like this for now, before we implement
    // more factors.

    /**
     * Assigns a person to a random available home
     * @param person
     */
    public void assignToHome(Person person) {
        if (!gameEngine.residentialList.isEmpty()) {
            Random random = new Random();
            int randomIndex = random.nextInt(gameEngine.residentialList.size());

            Residential randomItem = gameEngine.residentialList.get(randomIndex);
            if (randomItem.getCapacity() > randomItem.getCurrentPeople() && randomItem.isConnectedToMainRoad()) {
                randomItem.addResident(person);
                person.moveIn(randomItem);
                person.assignedToHome();
                assignToWork(person, true);
                uneducated.add(person);
            }else{
                people.remove(person);
            }
        }
    }
    

    /**
     * Assigns a specific person to one of the available workplaces (that are also connected to their residence)
     * @param person
     * @param addToUnemployed
     * @return Return value will indicate whether person has been assigned to a workplace
     */
    public boolean assignToWork(Person person, boolean addToUnemployed) {
        boolean hasBeenAssigned = false;
        if (person.hasHome() && person.getAge() < 65) {
            
            Random random = new Random();
            int randomNum = random.nextInt(10);
            if (randomNum < 5) {
                // Assign to a random industrial zone
                Residential personsHome = person.getHome();
                
                ArrayList<Industrial> connectedIndustrials = new ArrayList<>();
                
                for (int index = 0; index < personsHome.getConnectedWorkplaces().size(); index++) {
                    Zone workplace = (Zone)personsHome.getConnectedWorkplaces().get(index);
                    if (workplace instanceof Industrial) {
                        connectedIndustrials.add((Industrial)workplace);
                    }
                }
                
                if (!connectedIndustrials.isEmpty()) {
                    int randomIndex = random.nextInt(connectedIndustrials.size());
                    Industrial randomItem = connectedIndustrials.get(randomIndex);

                    if(randomItem.getCapacity() > randomItem.getCurrentPeople()){
                        randomItem.addWorker(person);
                        person.assignToWork();
                        person.setWorkPlace(randomItem);
                        hasBeenAssigned = true;
                    }
                }
            }
            else {
                // Assign to a random service zone
                Residential personsHome = person.getHome();
                
                ArrayList<Service> connectedServices = new ArrayList<>();
                
                for (int index = 0; index < personsHome.getConnectedWorkplaces().size(); index++) {
                    Zone workplace = (Zone)personsHome.getConnectedWorkplaces().get(index);
                    if (workplace instanceof Service) {
                        connectedServices.add((Service)workplace);
                    }
                }
                
                if (!connectedServices.isEmpty()) {
                    int randomIndex = random.nextInt(connectedServices.size());
                    Service randomItem = connectedServices.get(randomIndex);
                    
                    if(randomItem.getCapacity() > randomItem.getCurrentPeople()){
                        randomItem.addWorker(person);
                        person.assignToWork();
                        person.setWorkPlace(randomItem);
                        hasBeenAssigned = true;
                    }
                }
            }
            
            if (!hasBeenAssigned && addToUnemployed) {
                unemployed.add(person);
            }
            
        }
        return hasBeenAssigned;
    }
    
    /**
     * Assings a random unemployed person to a workplace connected to their residence
     */
    public void employRandom() {
        if (!unemployed.isEmpty()) {
            Random random = new Random();
            int randomInd = random.nextInt(unemployed.size());
            boolean success = assignToWork(unemployed.get(randomInd), false);
            if (success) {
                unemployed.remove(unemployed.get(randomInd));
            }
        }
    }
    public void yearlyReset(){
        for(Person p: people){
           Random random = new Random();
           int randomNum = random.nextInt(10);
           if (randomNum<4){
            if (p.educationLevel>0){
            p.setEducation(0);   
            uneducated.add(p);
            }
            if (p.getSchool()!=null){
            p.getSchool().removeStudent(p);
            }
            if (p.getUniversity()!=null){
            p.getUniversity().removeStudent(p);
            }
           }
        }
    }
    
    public boolean assignToStudy(Person person, boolean addToUneducated){
        boolean hasBeenAssigned = false;
        if(person.hasHome() && person.getAge()<65){
            Random random = new Random();
            int randomNum = random.nextInt(10);
            if (randomNum<6){
                Residential home = person.getHome();
                ArrayList<School> schools = new ArrayList<>();
                for (int index = 0; index < home.getConnectedEducation().size(); index++) {
                    SpecialBuilding building = (SpecialBuilding)home.getConnectedEducation().get(index);
                    if (building instanceof School) {
                        schools.add((School)building);
                    }
                }
                if (!schools.isEmpty()){
                    int randomIndex = random.nextInt(schools.size());
                    School randomItem = schools.get(randomIndex);
                    if(randomItem.getCapacity() > randomItem.getCurrentPeople()){
                        randomItem.addStudent(person);
                        person.setEducation(1);
                        person.setSchool(randomItem);
                        hasBeenAssigned = true;
                    }
                }
            }
            else{
                Residential home = person.getHome();
                ArrayList<University> universities = new ArrayList<>();
                for (int index = 0; index < home.getConnectedEducation().size(); index++) {
                    SpecialBuilding building = (SpecialBuilding)home.getConnectedEducation().get(index);
                    if (building instanceof University) {
                        universities.add((University)building);
                    }
                }
                if (!universities.isEmpty()){
                    int randomIndex = random.nextInt(universities.size());
                    University randomItem = universities.get(randomIndex);
                    if(randomItem.getCapacity() > randomItem.getCurrentPeople()){
                        randomItem.addStudent(person);
                        person.setEducation(2);
                        person.setUni(randomItem);
                        hasBeenAssigned = true;
                    }
                }
            }
            if (!hasBeenAssigned && addToUneducated) {
                uneducated.add(person);
            }
        }
        return hasBeenAssigned;
    }
    
    public void studyRandom(){
        if(!uneducated.isEmpty() && educationLimit()){
            Random random = new Random();
            int randomInd = random.nextInt(uneducated.size());
            boolean success = assignToStudy(uneducated.get(randomInd),false);
            if (success){
                uneducated.remove(uneducated.get(randomInd));
            }
        }
    }
    public boolean educationLimit(){
        return (uneducated.size()>people.size()*0.4);
    }
    
    /**
     *
     * @param value
     */
    public void changeGlobalHappiness(int value) {
        for (Person person : people) {
            person.setHappiness(person.getHappiness() + value);
        }
    }
    
    /**
     *
     * @return
     */
    public int getSize(){
        return people.size();
    }
}
