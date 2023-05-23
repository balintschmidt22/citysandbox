package model.buildings;

/**
 * Class for police station.
 * @author Bálint
 */
public class Police extends SpecialBuilding{
    private final int radius = 6;
    
    public Police(int x, int y) {
        super(x, y);
        this.buildingCost = 1500;
        this.upkeep = 150;
        this.image = "police";
    }
    
    public int getRadius() {
        return this.radius;
    }
    
}
