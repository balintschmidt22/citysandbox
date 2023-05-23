package model;
import java.time.LocalDate;
import java.util.Arrays;
import model.zones.Residential;
import model.zones.Industrial;
import model.zones.Zone;
import model.nature.Grass;
import model.nature.Water;
import model.nature.Rock;
import model.buildings.Stadium;
import model.buildings.University;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.ThreadLocalRandom;
import javax.swing.JOptionPane;
import model.buildings.Forest;
import model.buildings.Police;
import model.buildings.School;
import model.buildings.SpecialBuilding;
import model.pop.Person;
import model.pop.Pop;
import model.zones.Service;
import view.GameGUI;
import static view.GameWindow.FREQ;
import static view.GameWindow.date;
import static view.GameWindow.timer;


/**
 * This class contains the main logic behind the game, and serves as the connection point between the model and view.
 * @author Bálint
 */
public class GameEngine{
    private final int sizeX = 20; //HEIGHT of the board
    private final int sizeY = 20; //WIDTH of the board
    private final int STARTINGFUNDS = 10000; // Setting money
    
    // Uses normal coordinates (not flipped!)
    private final int MainroadX = 0;
    private final int MainroadY = 8;
    
    private final int radius = 4;
    
    private Board board;
    private GraphSearch graphSearch;
    private Catastrophe catastrophe;
    private int money;
    private Pop pop;
    private int avgHappiness;
    private int income;
    protected GameGUI gamegui;
    
    /**
     *
     */
    public ArrayList<Residential> residentialList = new ArrayList<>();

    /**
     *
     */
    public ArrayList<Industrial> industrialList = new ArrayList<>();

    /**
     *
     */
    public ArrayList<Service> serviceList = new ArrayList<>();

    /**
     *
     */
    public ArrayList<Road> roadList = new ArrayList<>();

    /**
     *
     */
    public ArrayList<Police> policeList = new ArrayList<>();

    /**
     *
     */
    public ArrayList<Stadium> stadiumList = new ArrayList<>();

    /**
     *
     */
    public ArrayList<Forest> forestList = new ArrayList<>();

    /**
     *
     */
    public ArrayList<School> schoolList = new ArrayList<>();

    /**
     *
     */
    public ArrayList<University> universityList = new ArrayList<>();

    /**
     * Listing damaged buildings by catastrophe.
     */
    public ArrayList<Field> damagedBuildings = new ArrayList<>();

    /**
     * Listting buildings which are repaired after catastrophe.
     */
    public ArrayList<Field> repairingBuildings = new ArrayList<>();

    /**
     * Listing every special buildings.
     */
    public ArrayList<SpecialBuilding> specialList = new ArrayList<>();

    /**
     * Constructor, initializes variables
     */
    public GameEngine(){
        board = new Board(sizeX,sizeY);
        graphSearch = new GraphSearch();
        graphSearch.board = this.board;
        catastrophe = new Catastrophe();
        catastrophe.gameEngine = this;
        money = STARTINGFUNDS;
        avgHappiness = 50;
        pop = new Pop(this);
        
        setFields();
    }

    /**
     * Gets called each month, calls all methods involving core game logic (moving in, happiness calc, catastrophes, etc..)
     */
    public void tick() {
        // Game logic after a timer tick goes here (ex. calculating money, etc.)
        
        //Moving out if happiness is low
        pop.movingOut();
        
        // Spawning of people
        if(avgHappiness <= 30){
            if(1 == ThreadLocalRandom.current().nextInt(1,3)){
                pop.spawnPerson();
            }
        }else if(avgHappiness <= 65 && avgHappiness > 30){
            pop.spawnPerson();
        }else if(avgHappiness <= 80 && avgHappiness > 65){
            pop.spawnPerson();
            pop.spawnPerson();
        }else if(avgHappiness <= 100 && avgHappiness > 80){
            pop.spawnPerson();
            pop.spawnPerson();
            pop.spawnPerson();
        }
        
        pop.employRandom();
        pop.studyRandom();
        //System.out.println(pop.uneducated);
        for(Forest f : forestList){
            f.updateState();
        }
        setIncome();
        calcHappiness();
        gamegui.getInfoMenu().updateLabelText();
        if(gamegui.getInfoMenu().getStatMenu() != null){
            gamegui.getInfoMenu().getStatMenu().updateLabelText();
        }
        
        // Repairing disaster damage
        repairTick();
        // Handling catastrophes
        catastrophe.disasterRoll();
        /*if(!damagedBuildings.isEmpty()){
            System.out.println(damagedBuildings);
        }
        System.out.println(pop.uneducated.size());*/
    }
    
    /**
     * Repairs all buildings currently under repair 
     */
    public void repairTick() {
        Iterator<Field> iterator = repairingBuildings.iterator();
        while (iterator.hasNext()) {
            Field element = iterator.next();
            if (element.repairTick()) {
                iterator.remove();
                damagedBuildings.remove(element);
            }
        }
    }
    
    /**
     * Starts the repairing process of the specified building
     * @param x
     * @param y
     */
    public void repairBuilding(int x, int y) {
        Field field = board.getFieldAt(x, y);
        if (field instanceof Zone || field instanceof SpecialBuilding) {
            if (!repairingBuildings.contains(field)) {
                repairingBuildings.add(field);
                setMoney(money - 500);
            }
        }
    }
    
    /**
     * Calculates happiness globally
     */
    public void calcHappiness(){
        int x; int y; //Residence
        int wx; int wy; //Workplace
        int ix; int iy; //Industrial
        int px; int py; //Police
        int sx; int sy; //Stadium
        int fx; int fy; //Forest
        int count;
        
        // Removing happiness for each destroyed building
        for (Field field : damagedBuildings) {
            pop.changeGlobalHappiness(-3);
        }
        
        for(Residential res : residentialList){
            x = res.getPosY();
            y = res.getPosX();
            count = 0;
                //Police close to home (+)
                for(Police pol : policeList){
                    if(pol.isConnectedToMainRoad()){
                        px = pol.getPosY();
                        py = pol.getPosX();
                        int pradius = pol.getRadius();
                        if(px >= x-pradius && px <= x+pradius && py >= y-pradius && py <= y+pradius){
                            res.changeHappiness(+1);
                        }
                    }
                }
                //Stadium close to home(+)
                for(Stadium st : stadiumList){
                    if(st.isConnectedToMainRoad()){
                        sx = st.getPosY();
                        sy = st.getPosX();
                        int sradius = st.getRadius();
                        if(sx >= x-sradius && sx <= x+sradius && sy >= y-sradius && sy <= y+sradius){
                            res.changeHappiness(+1);
                        }
                    }
                }
                //Forest close to home(+)
                for(Forest f : forestList){
                    fx = f.getPosY();
                    fy = f.getPosX();
                    int fradius = f.getRadius();
                    if(fx >= x-fradius && fx <= x+fradius && y >= fy-fradius && fy <= fy+fradius){
                        res.changeHappiness(f.getBonus());
                    }
                }
            
            //Living close to workplace (+) or far (-)
            for(Person person : res.getResidents()){
                if(person.hasWork()){
                    wx = person.getWorkPlace().getPosY();
                    wy = person.getWorkPlace().getPosX();
                    if(wx >= x-radius && wx <= x+radius && wy >= y-radius && wy <= y+radius){
                        person.setHappiness(person.getHappiness() + 1);
                    }else{
                        person.setHappiness(person.getHappiness() - 2);
                    }
                    //Police close to workplace (+)
                    for(Police pol : policeList){
                        if(pol.isConnectedToMainRoad()){
                            px = pol.getPosY();
                            py = pol.getPosX();
                            int pradius = pol.getRadius();
                            if(px >= wx-pradius && px <= wx+pradius && py >= wy-pradius && py <= wy+pradius){
                                res.changeHappiness(+1);
                            }
                        }
                    }
                    //Stadium close to workplace(+)
                    for(Stadium st : stadiumList){
                        if(st.isConnectedToMainRoad()){
                            sx = st.getPosY();
                            sy = st.getPosX();
                            int sradius = st.getRadius();
                            if(sx >= wx-sradius && sx <= wx+sradius && sy >= wy-sradius && sy <= wy+sradius){
                                res.changeHappiness(+1);
                            }
                        }
                    }
                }
            }
            //Industrial close to home (-)
            for(Industrial ind : industrialList){
                ix = ind.getPosY();
                iy = ind.getPosX();
                if(ix >= x-radius && ix <= x+radius && iy >= y-radius && iy <= y+radius){
                    count++;
                    res.changeHappiness(-2);
                }
            }

            //0 industrial in the radius (+)
            if(count == 0){
                res.changeHappiness(1);
            }
            //debt (-)
            if(money < 0){
                int change = (Integer)money/400;
                res.changeHappiness(change);
            }
            //More industrial than service or the other way around (-)
            if(serviceList.isEmpty() || industrialList.isEmpty()){
                if(serviceList.size() >= 3 || industrialList.size() >= 3){
                    res.changeHappiness(-2);
                }
            }else {
                double rate = (double)serviceList.size()/(double)industrialList.size();
                if(rate >= 3 || 1/rate >= 3){
                    res.changeHappiness(-2);
                }
            }
        }
    }
    
    /**
     * Used for pension, ages citizens
     */
    public void newYear(){
        //every January
        updateMoney();
        pop.age();
        pop.yearlyReset();
    }
    
    /**
     * Returns a specified field
     * @param x
     * @param y
     * @return
     */
    public Field getFieldAt(int x, int y){
        return board.getFieldAt(x, y);
    }
    

    /**
     * Initializes the map
     */
    public void setFields() {
        for (int x = 0; x < sizeX; ++x) {
            for (int y = 0; y < sizeY; ++y) {
                if ((x+y)<((sizeX+sizeY)/8) || (x+y)>(sizeX+sizeY)*0.75){
                board.putIntoFields(new Water(x, y), x, y);
                }
                else{
                board.putIntoFields(new Grass(x, y), x, y);
                }
            }
        }
        mapSpawn();
    }
    
    /**
     * 
     * @param x
     * @param y
     * @return Whether a field is buildable on or not
     */
    public boolean buildable(int x, int y){
        return (board.getFieldAt(x, y) instanceof Grass);
    }
    

    /**
     * Method to build on the board (deducts money)
     * @param field
     * @param x
     * @param y
     */
    public void buildField(Field field, int x, int y) {
        if (money - field.getBuildingCost() < -3000) {
            //can be <0 because of debt
        }
        else {
            if (buildable(x,y)) {
                if(field instanceof Stadium || field instanceof University){
                    if(buildable(x+1, y) && buildable(x, y+1) && buildable(x+1, y+1)){
                        build2x2(x,y, field);
                    }else{
                        System.out.println("Invalid placement!");
                    }
                }else if(field instanceof School){
                    if(buildable(x, y+1)){
                        build2x2(x,y, field); //It's 2x1 but same logic
                    }
                }
                else{ 
                    board.putIntoFields(field, x, y);
                    if (field.isRoad()){
                        //If its a road type, we cycle over all the roads to check what changed.
                        roadList.add((Road)field);
                        roadList.forEach(road->setRoadDirection(road));
                    }  
                    money -= field.getBuildingCost();
                    gamegui.repaint();
                    // If the new building is of Residential type, we need to check
                    // if it is connected to the Main road
                    if (field.isResidential()) {
                        // this could create a fatal issue since the same field could be added
                        // infinite times to this list, and removing elements is not implemented YET
                        residentialList.add((Residential)field);
                        //System.out.println("field added to residential list");
                    }
                    else if (field.isIndustrial()){
                        industrialList.add((Industrial)field);
                    }
                    else if (field.isService()) {
                        serviceList.add((Service)field);
                    }
                    else if (field instanceof SpecialBuilding specialBuilding) {
                        specialList.add(specialBuilding);
                        //happiness needs it, can be refactored later 
                        if (field instanceof Police pol){
                            policeList.add(pol);
                        }
                        if (field instanceof Forest f){
                            forestList.add(f);
                        }
                    }  
                    if(field instanceof Zone || field instanceof Road){
                        residentialList.forEach(item -> {
                            if (!item.isConnectedToMainRoad()) {
                                updateMainRoadConnection(item);
                            }
                        });
                        industrialList.forEach(item -> {
                            if (!item.isConnectedToMainRoad()) {
                                updateMainRoadConnection(item);
                            }
                        });                        
                        serviceList.forEach(item -> {
                            if (!item.isConnectedToMainRoad()) {
                                updateMainRoadConnection(item);
                            }
                        });
                        
                        //THIS HAS TO BE THE LAST CALL (or everything breaks)
                        residentialList.forEach(item->updateConnectedWorkplaces(item));
                        residentialList.forEach(item->updateConnectedEducation(item));
                        
                    }
                    if (field instanceof SpecialBuilding || field instanceof Road) {
                        specialList.forEach(item -> {
                            if (!item.isConnectedToMainRoad()) {
                                updateMainRoadConnection(item);
                            }
                        });
                    }
                }
            }
            else {
                System.out.println("Invalid placement!");
            }
        }
    }

    /**
     * Removes specified field
     * @param x
     * @param y
     */
    public void removeField(int x, int y){
        var field = getFieldAt(x, y);
        
        if(field instanceof Stadium || field instanceof University || field instanceof School){
            remove2x2(x, y, field);
        }else{
            if(removable(x,y)){
                board.putIntoFields(new Grass(x,y),x,y);
                money += field.buildingCost*0.1;
                if (field.isRoad()){
                    //If its a road type, we cycle over all the roads to check what changed.
                    roadList.remove((Road)field);
                    roadList.forEach(road->setRoadDirection(road));      
                }else if (field.isResidential()) {
                    // this could create a fatal issue since the same field could be added
                    // infinite times to this list, and removing elements is not implemented YET
                    residentialList.remove((Residential)field);
                    //System.out.println("field removed from residential list");
                }

                if(field instanceof Zone || field instanceof Road){
                    residentialList.forEach(item -> updateMainRoadConnection(item));
                    residentialList.forEach(item -> updateConnectedWorkplaces(item));
                    //System.out.println("residential list check called at remove");
                }
                if(field.isIndustrial()){
                    industrialList.remove((Industrial)field);
                } else if(field.isService()){
                    serviceList.remove((Service)field);
                } else if(field instanceof Police pol){
                    policeList.remove(pol);
                    specialList.remove(pol);
                } else if(field instanceof Forest f){
                    forestList.remove(f);
                    specialList.remove(f);
                } else if (field instanceof School s){
                    schoolList.remove(s);
                    specialList.remove(s);
                } else if (field instanceof University u){
                    universityList.remove(u);
                    specialList.remove(u);
                }
            }
            else{
                JOptionPane.showMessageDialog(gamegui, "Nature elements, and the main road, or connecting roads are not removable!");
            }
        }
        gamegui.repaint();
    }
    
    /**
     * Upgrades specified field (metropolis)
     * @param x
     * @param y
     */
    public void upgradeField(int x, int y) {
        Field field = getFieldAt(x,y);
        if (field instanceof Zone zone) {
            if (zone.upgrade()) {
                money -= zone.getBuildingCost();
                gamegui.getInfoMenu().updateLabelText();
                gamegui.repaint();
            }
        }
    }
        
    /**
     * Builds 2x2 buildings
     * @param x
     * @param y
     * @param field
     */
    public void build2x2(int x, int y, Field field){
        //Upper left corner should be selected
        if(field instanceof Stadium st){
            st.image = "stadium";
            st.topLeft = true; //For removing && drawing
            st.setUpkeep(300);
            st.main = st; //For removing && infomenu
            board.putIntoFields(field, x, y);
            Stadium st2 = new Stadium(x, y+1);
            st2.main = st;
            board.putIntoFields(st2, x, y+1);
            Stadium st3 = new Stadium(x+1, y);
            st3.main = st;
            board.putIntoFields(st3, x+1, y);
            Stadium st4 = new Stadium(x+1, y+1);
            st4.main = st;
            board.putIntoFields(st4, x+1, y+1); 
            specialList.add(st); specialList.add(st2);
            specialList.add(st3); specialList.add(st4);
            residentialList.forEach(item -> {
                if (!item.isConnectedToMainRoad()) {
                    updateMainRoadConnection(item);
                    }
                });
            universityList.forEach(item -> {
                if (!item.isConnectedToMainRoad()) {
                    updateMainRoadConnection(item);
                        }
                });        
            stadiumList.add(st);
        }else if(field instanceof University uni){
            uni.image = "university";
            uni.topLeft = true;
            uni.setUpkeep(200);
            uni.main = uni;
            board.putIntoFields(field, x, y);
            University u2 = new University(x, y+1);
            u2.main = uni;
            board.putIntoFields(u2, x, y+1);
            University u3 = new University(x+1, y);
            u3.main = uni;
            board.putIntoFields(u3, x+1, y);
            University u4 = new University(x+1, y+1);
            u4.main = uni;
            board.putIntoFields(u4, x+1, y+1);
            specialList.add(uni); specialList.add(u2);
            specialList.add(u3); specialList.add(u4);
            universityList.add(uni);
        }else if(field instanceof School sc){
            sc.image = "school";
            sc.left = true;
            sc.setUpkeep(150);
            sc.main = sc; //For removing && infomenu
            board.putIntoFields(sc, x, y);
            School sc2 = new School(x, y+1);
            sc2.main = sc;
            board.putIntoFields(sc2, x, y+1);
            specialList.add(sc); specialList.add(sc2);
            schoolList.add(sc);
        }
        specialList.forEach(item -> {
            if (!item.isConnectedToMainRoad()) {
                updateMainRoadConnection(item);
            }
        });
        if (field instanceof School || field instanceof University){
            residentialList.forEach(item->updateConnectedEducation(item));
        }
        gamegui.repaint();
        money -= field.getBuildingCost();
    }

    /**
     * Returns whether a field is removable or not.
     * @param x
     * @param y
     * @return
     */
    public boolean removable(int x, int y){
        return (board.getFieldAt(x, y) instanceof Water ==false 
                && board.getFieldAt(x, y) instanceof Rock ==false
                && (MainroadY==x && MainroadX==y) ==false
                && ((board.getFieldAt(x, y))).deletability() != false);
    }
    
    /**
     * Removing 2x2 buildings.
     * @param x
     * @param y
     * @param field
     */
    public void remove2x2(int x, int y, Field field){
        if(field instanceof Stadium stadium){
            if(stadium.topLeft){
                stadiumList.remove(stadium);
                specialList.remove(stadium);
                specialList.remove(getFieldAt(x+1, y));
                specialList.remove(getFieldAt(x, y+1));
                specialList.remove(getFieldAt(x+1, y+1));
                board.putIntoFields(new Grass(x,y), x, y);
                board.putIntoFields(new Grass(x+1,y),x+1,y);
                board.putIntoFields(new Grass(x,y+1),x,y+1);
                board.putIntoFields(new Grass(x+1,y+1),x+1,y+1);
                money += stadium.buildingCost*0.1;
            }else{
                Stadium st = stadium.main;
                remove2x2(st.getPosX(), st.getPosY(), st);
            }
        }else if(field instanceof University uni){
            if(uni.topLeft){
                universityList.remove(uni);
                specialList.remove(uni);
                specialList.remove(getFieldAt(x+1, y));
                specialList.remove(getFieldAt(x, y+1));
                specialList.remove(getFieldAt(x+1, y+1));
                board.putIntoFields(new Grass(x,y), x, y);
                board.putIntoFields(new Grass(x+1,y),x+1,y);
                board.putIntoFields(new Grass(x,y+1),x,y+1);
                board.putIntoFields(new Grass(x+1,y+1),x+1,y+1);
                money += uni.buildingCost*0.1;
            }else{
                University un = uni.main;
                remove2x2(un.getPosX(), un.getPosY(), un);
            }
        }else if(field instanceof School sc){
            if(sc.left){
                schoolList.remove(sc);
                specialList.remove(sc);
                specialList.remove(getFieldAt(x, y+1));
                board.putIntoFields(new Grass(x,y), x, y);
                board.putIntoFields(new Grass(x,y+1),x,y+1);
                money += sc.buildingCost*0.1;
            }else{
                School sc2 = sc.main;
                remove2x2(sc2.getPosX(), sc2.getPosY(), sc2);
            }
        }
        if (field instanceof School || field instanceof University){
            residentialList.forEach(item->updateConnectedEducation(item));
        }
    }

    /**
     * Method to set a Field in the Board class to a specific type (for DEBUGGING)
     * @param field
     * @param x
     * @param y
     */
    public void setField(Field field, int x, int y) {
        board.putIntoFields(field, x, y);
    }

    /**
     *
     */
    public void updateMoney(){
        this.money+=this.income;
    }

    /**
     * Calculates the average happiness, and ends the game if it's too low
     */
    public void setAvgHappiness() {
        int sum = 0;
        if(!pop.people.isEmpty()){
            for (Person person : pop.people){
                sum+=person.getHappiness();
            }
            avgHappiness = sum/pop.people.size();
            if(avgHappiness <= 10){
                gamegui.setPaused(true);
                String[] options = {"New Game", "Exit"};
                int x = JOptionPane.showOptionDialog(null, "You lost the game!",
                "Game Over",
                JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);
                if (x == 0) {
                    gamegui.restart();
                    //static import from GameWindow
                    date = LocalDate.now();
                    FREQ = 2000;
                    timer.setDelay(FREQ);
                } else {
                    System.exit(0);
                }
            }else if(avgHappiness >= 100){
                avgHappiness = 100;
            }
        }
    }
    
    /**
     * Calculates current income and deducts upkeep
     */
    public void setIncome() {
        //Upkeeps will also be here
        int sum=0;
        for (Residential residential: residentialList){
            sum+=residential.getTax();
        }
        for (Industrial industrial: industrialList){
            sum+=industrial.getTax();
        }
        for (Service service: serviceList){
            sum+=service.getTax();
        }
        int expense = 0;
        
        expense += roadList.get(0).getUpkeep()*roadList.size();
        
        if(!policeList.isEmpty()){
            expense += policeList.get(0).getUpkeep()*policeList.size();
        }
        if(!stadiumList.isEmpty()){
            expense += stadiumList.get(0).getUpkeep()*stadiumList.size();
        }
        if(!forestList.isEmpty()){
            expense += forestList.get(0).getUpkeep()*forestList.size();
        }
        if(!schoolList.isEmpty()){
            expense += schoolList.get(0).getUpkeep()*schoolList.size();
        }
        if(!universityList.isEmpty()){
            expense += universityList.get(0).getUpkeep()*universityList.size();
        }
        
        this.income=sum-expense;
        
        if(pop.people.isEmpty() && money < -5000){
            gamegui.setPaused(true);
            String[] options = {"New Game", "Exit"};
            int x = JOptionPane.showOptionDialog(null, "You lost the game!",
            "Game Over",
            JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);
            if (x == 0) {
                gamegui.restart();
                //static import from GameWindow
                date = LocalDate.now();
                FREQ = 2000;
                timer.setDelay(FREQ);
            } else {
                System.exit(0);
            }
        }
    }
    
    /**
     * Updates building statuses; if they are connected to the main road or not
     * @param zone
     */
    public void updateMainRoadConnection(Field zone) {
        if (zone instanceof Zone || zone instanceof SpecialBuilding) {
            //coords could be swapped here
            if (graphSearch.hasPathToMainRoad(zone.getPosX(), zone.getPosY())) {
                zone.connectToMainRoad();
            }
            else {
                zone.disconnectFromMainRoad();
            }
        }
        else {
            System.out.println("mainroadconnection fun called on wrong field");
        }
        // Has to reset visited properties of all fields or else everything breaks
        resetVisited();
    }
    
    /**
     * Helps with graph search.
     */
    public void resetVisited() {
        for (int i = 0; i < sizeX; i++) {
            for (int j = 0; j < sizeY; j++) {
                board.getFieldAt(i, j).setVisited(false);
            }
        }
    }
    
    /**
     * Updates the currently connected workplaces on specified residential building
     * @param residential
     */
    public void updateConnectedWorkplaces(Residential residential) {
        //System.out.println("Assign to work method called");
        if (residential.isResidential()) {
            residential.setConnectedWorkplaces(graphSearch.findPathToWork(residential.getPosX(), residential.getPosY()));
        }
        else {
            System.out.println("updateConnectedWorkplaces fun called on wrong field");
        }
        // Has to reset visited properties of all fields or else everything breaks
        resetVisited();
    }
    
    /**
     * Updates connected education buildings to zones
     * @param res 
     */
    public void updateConnectedEducation(Residential res){
        if (res.isResidential()){
            res.setConnectedEducation(graphSearch.findPathToEducation(res.getPosX(),res.getPosY()));
        }
        else{
            System.out.println("updateConnectedEdu fun called on wrong field");
        }
        resetVisited();
    }
    
    /**
     * 
     * @param gg 
     */
    public void setGamegui(GameGUI gg) {
        this.gamegui = gg;
    }
    
    /**
     * Function for changing roads while building.
     * @param road 
     */
    void setRoadDirection(Road road){
        int[] connections={0,0,0,0};
        int[] down={1,0,0,0};
        int[] left={0,1,0,0};
        int[] up={0,0,1,0};
        int[] right={0,0,0,1};
        int[] DtoLeft={1,1,0,0};
        int[] UtoRight={0,0,1,1};
        int[] DtoRight={1,0,0,1};
        int[] UtoLeft={0,1,1,0};
        int[] DtoUp={1,0,1,0};
        int[] LtoRight={0,1,0,1};
        int counter=0;
        int[][] directions = {{-1, 0}, {0, -1}, {1, 0}, {0, 1}}; // Up, Left, Down, Right
        for (int[] direction : directions) {
            int newX = road.getPosX() + direction[0];
            int newY = road.getPosY()+ direction[1];
            if ((newX >= 0 && newX < board.getSizeX() && newY >= 0 && newY < board.getSizeY())) {
                Field newField = board.getFieldAt(newX, newY);
                    if (newField instanceof Road) {
                        if(counter==0){
                            connections[0]=1;
                        }
                        if(counter==1){
                            connections[1]=1;
                        }
                        if(counter==2){
                            connections[2]=1;
                        }
                        if(counter==3){
                            connections[3]=1;
                        }
                }
            }
                        counter++;
        }
        boolean isSimple=Arrays.stream(connections).sum()<3;
        if (isSimple){
            if (Arrays.equals(connections,up)){   
                road.setImage("roadNorth");
            }else if (Arrays.equals(connections,DtoUp)){   
                road.setImage("roadNorth");
            }else if (Arrays.equals(connections,down)){   
                road.setImage("roadNorth");
            }else if (Arrays.equals(connections,left)){   
                road.setImage("road");
            }else if (Arrays.equals(connections,LtoRight)){   
                road.setImage("road");
            }else if (Arrays.equals(connections,right)){   
                road.setImage("road");
            }else if (Arrays.equals(connections,DtoLeft)){   
                road.setImage("roadTurn1");
            }else if (Arrays.equals(connections,DtoRight)){   
                road.setImage("roadTurn2");
            }else if (Arrays.equals(connections,UtoRight)){   
                road.setImage("roadTurn");
            }else if (Arrays.equals(connections,UtoLeft)){   
                road.setImage("roadTurn3");
            }
        }
        else{
            road.setImage("roadMid");
        }
    }
    
    /**
     *
     * @return
     */
    public Catastrophe getCatastrophe() {
        return catastrophe;
    }
    
    /**
     * Starting state of the map.
     */
    public void mapSpawn(){
        Road mainroad = new Road(MainroadY, MainroadX);
        mainroad.setMainRoad();
        roadList.add(mainroad);
        board.putIntoFields(mainroad,MainroadY,MainroadX);
        board.putIntoFields(new Rock(MainroadY+1,MainroadX), MainroadY+1, MainroadX);
        board.putIntoFields(new Rock(MainroadY-11,MainroadX), MainroadY-1, MainroadX);
        board.putIntoFields(new Rock(5,10), 5, 10);
        board.putIntoFields(new Rock(13,5), 13, 5); 
        Forest f1 = new Forest(2, 15);
        Forest f2 = new Forest(16, 3);
        Forest f3 = new Forest(5,5);
        f1.setUpkeep(0);
        f2.setUpkeep(0);
        f3.setUpkeep(0);
        board.putIntoFields(f1, 2, 15);
        board.putIntoFields(f2, 16, 3);
        board.putIntoFields(f3, 5, 5);
        forestList.add(f1); specialList.add(f1);
        forestList.add(f2); specialList.add(f2);
        forestList.add(f3); specialList.add(f3);
    }
    
    //setter, getter

    /**
     *
     * @return
     */
    public Board getBoard() {
        return board;
    }

    /**
     *
     * @return
     */
    public int getMoney() {
        return money;
    }

    /**
     *
     * @return
     */
    public int getAvgHappiness() {
        return avgHappiness;
    }
    
    /**
     *
     * @return
     */
    public int getBudget() {
        return money;
    }
    
    /**
     *
     * @return
     */
    public int getIncome() {
        return income;
    }
    
    /**
     *
     * @return
     */
    public int getPop() {
        return pop.getSize();
    }
    
    /**
     *
     * @param money
     */
    public void setMoney(int money) {
        this.money = money;
    }
}
