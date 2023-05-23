package model.buildings;

/**
 * Implementing 2x2 stadiums in the game
 * @author Arni
 */
public class Stadium extends SpecialBuilding {
    private final int radius = 7;

    /**
     * Needed because of 2x2 building
     */
    public boolean topLeft = false;

    /**
     * Needed because of 2x2 building, helps at removing
     */
    public Stadium main;

    /**
     *
     * @param x
     * @param y
     */
    public Stadium(int x, int y) {
        super(x, y);
        this.buildingCost = 2500;
        this.image = "stadium";
    }
    
    /**
     *
     * @return
     */
    public int getRadius() {
        return this.radius;
    }

    /**
     *
     * @return
     */
    public boolean isTopLeft() {
        return topLeft;
    }

    /**
     *
     * @return
     */
    public Stadium getMain() {
        return main;
    }

    /**
     *
     * @return
     */
    @Override
    public int getUpkeep() {
        return main.upkeep;
    }
    
    /**
     *
     * @return
     */
    @Override
    public boolean isConnectedToMainRoad() {
        return main.isConnectedToMainRoad;
    }
    
    /**
     *
     */
    @Override
    public void connectToMainRoad() {
        isConnectedToMainRoad = true;
        main.isConnectedToMainRoad = true;
    }
    
    /**
     *
     */
    @Override
    public void disconnectFromMainRoad() {
        isConnectedToMainRoad = false;
        main.isConnectedToMainRoad = false;
    }
}
