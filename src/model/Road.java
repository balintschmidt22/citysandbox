package model;

/**
 * Class for roads in the game
 * @author Bálint
 */
public class Road extends Field{
    private boolean isMainRoad = false;

    /**
     *
     */
    protected int upkeep = 50;

    /**
     *
     * @param x
     * @param y
     */
    public Road(int x, int y) {
        super(x, y);
        buildingCost = 100;
        image = "road";
    }
    
    /**
     *
     * @return
     */
    @Override
    public boolean isRoad() {
        return true;
    }
    
    /**
     *
     */
    public void setMainRoad() {
        isMainRoad = true;
    }

    /**
     *
     * @return
     */
    public boolean isMainRoad() {
        return isMainRoad;
    }

    /**
     *
     * @return
     */
    public int getUpkeep() {
        return upkeep;
    }
    
}
