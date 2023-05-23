package model.buildings;

import java.time.LocalDate;
import view.GameWindow;

/**
 * Class for forest feature
 * @author Arni
 */
public class Forest extends SpecialBuilding {
    private LocalDate date;
    private final int radius = 3;
    private int bonus;
    
    /**
     *
     * @param x
     * @param y
     */
    public Forest(int x, int y) {
        super(x, y);
        this.buildingCost = 1000;
        this.upkeep = 100;
        this.image = "forest1";
        this.bonus = 1;
        this.date = GameWindow.date.minusMonths(1);
    }
    
    /**
     * Makes the forest age, and manages the upkeep
     */
    public void updateState(){
        long year = java.time.temporal.ChronoUnit.YEARS.between(date, GameWindow.date);
        
        if(year >= 3.0 && year <= 7.0){
            this.image = "forest2";
            this.bonus = 2;
        }else if(year <= 10.0){
            this.bonus = 3;
        }else{
            this.image = "forest";
            this.bonus = 1;
            this.upkeep = 0;
        }
    }

    /**
     *
     * @return
     */
    public LocalDate getDate() {
        return date;
    }

    /**
     *
     * @return
     */
    public int getBonus() {
        return bonus;
    }

    /**
     *
     * @return
     */
    public int getRadius() {
        return radius;
    }
}
