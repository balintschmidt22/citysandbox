package model;

/**
 * This class serves as the model representation of a Field on the game board.                    
 * @author Bálint
 **/
public class Field {
    private int posX;
    private int posY;

    /**
     *
     */
    protected int buildingCost = 0;

    /**
     *
     */
    protected String image = "noimage";

    /**
     *
     */
    protected String damagedImage = "damaged";
    private boolean isDeletable = true;

    /**
     *
     */
    protected boolean isConnectedToMainRoad = false;
    
    // Part of the catastrophe update

    /**
     *
     */
    protected boolean damaged = false;
    // This has to get reset after the repairing is done, and is used to
    // indicate how many months of work has been done

    /**
     *
     */
    protected int repairState = 0;
    
    private boolean isVisited = false;

    /**
     *
     * @param posX
     * @param posY
     */
    public Field(int posX, int posY) {
        this.posX = posX;
        this.posY = posY;
    }
    
    /**
     *
     * @return
     */
    public int getPosX() {
        return posX;
    }

    /**
     *
     * @return
     */
    public int getPosY() {
        return posY;
    }

    /**
     *
     * @return
     */
    public int getBuildingCost() {
        return buildingCost;
    }

    /**
     *
     * @param posX
     */
    public void setPosX(int posX) {
        this.posX = posX;
    }

    /**
     *
     * @param posY
     */
    public void setPosY(int posY) {
        this.posY = posY;
    }

    /**
     *
     * @param buildingCost
     */
    public void setBuildingCost(int buildingCost) {
        this.buildingCost = buildingCost;
    }
    
    /**
     *
     * @return
     */
    public String getImage() {
        if (!damaged) {
            return this.image;
        }
        else {
            return this.damagedImage;
        }
    }
    
    /**
     *
     * @return
     */
    public boolean isRoad() {
        return false;
    }

    /**
     *
     * @param img
     */
    public void setImage(String img){
        this.image=img;
    }
    
    // Determines if a field object is connectable by road (zones)

    /**
     *
     * @return
     */
    public boolean isConnectable() {
        return false;
    }
    
    /**
     *
     * @return
     */
    public boolean isResidential() {
        return false;
    }
    
    /**
     *
     * @return
     */
    public boolean isService() {
        return false;
    }
    
    /**
     *
     * @return
     */
    public boolean isIndustrial(){
        return false;
    }
    
    /**
     *
     * @param bool
     */
    public void setVisited(boolean bool) {
        isVisited = bool;
    }

    /**
     *
     * @return
     */
    public boolean isVisited() {
        return isVisited;
    }
    
    /**
     *
     */
    public void setUndeletable() {
        isDeletable = false;
    }

    /**
     *
     * @return
     */
    public boolean deletability() {
        return isDeletable;
    }
    
    /**
     *
     * @return
     */
    public boolean isConnectedToMainRoad() {
        return isConnectedToMainRoad;
    }
    
    /**
     *
     */
    public void connectToMainRoad() {
        isConnectedToMainRoad = true;
    }

    /**
     *
     */
    public void disconnectFromMainRoad() {
        isConnectedToMainRoad = false;
    }
    
    /**
     *
     */
    public void makeDamaged() {
        damaged = true;
    }

    /**
     *
     * @return
     */
    public boolean isDamaged() {
        return damaged;
    }

    /**
     *
     * @return
     */
    public boolean isRepairing() {
        return repairState != 0;
    }
    
    /**
     *
     * @return
     */
    public boolean repairTick() {
        boolean success = false;
        if (repairState < 6) {
            ++repairState;
            System.out.println("Repairing..."+repairState);
        }
        else {
            damaged = false;
            repairState = 0;
            System.out.println("Repaired!");
            success = true;
        }
        return success;
    }
}
