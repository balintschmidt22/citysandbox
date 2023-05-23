package model.buildings;

import model.Field;

/**
 *
 * @author Bálint
 */
public class SpecialBuilding extends Field{
    protected int upkeep;
    
    public SpecialBuilding(int x, int y) {
        super(x, y);
        this.image = "police";
    }

    public int getUpkeep() {
        return upkeep;
    }
    public void setUpkeep(int value) {
        upkeep=value;
    }
}
