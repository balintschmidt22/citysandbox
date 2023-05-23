package model;


/**
 * This class represents the model part of the game's board, containing the several Fields it is made up from.    
 * @author Bálint
 */
public class Board {
    private final int sizeX;
    private final int sizeY;
    private final Field[][] fields;

    /**
     *
     * @param sizeX X size of the board
     * @param sizeY Y size of the board
     */
    public Board(int sizeX, int sizeY) {
        this.sizeX = sizeX;
        this.sizeY = sizeY;
        fields = new Field[this.sizeX][this.sizeY];
    }
    
    /**
     * Returns a field instance at specified coordinates
     * @param x X position
     * @param y Y position
     * @return A field instance
     */
    public Field getFieldAt(int x, int y){
        return fields[x][y];
    }

    /**
     *
     * @return
     */
    public int getSizeX() {
        return sizeX;
    }

    /**
     *
     * @return
     */
    public int getSizeY() {
        return sizeY;
    }

    /**
     * 
     * @return
     */
    public Field[][] getFields() {
        return fields;
    }
    
    /**
     *
     * @param f The field instance that should be placed in the array
     * @param i X position
     * @param j Y position
     */
    public void putIntoFields(Field f, int i, int j){
        fields[i][j] = f;
    }

}