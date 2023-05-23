package model;

/**
 *
 * @author Arni
 */

import java.util.ArrayList;
import java.util.Random;

/**
 * This class handles all logic behind the handling of catastrophes within the game.
 * @author Arni
 */
public class Catastrophe {

    GameEngine gameEngine = null;
    
    /**
     * Strikes a specified field.
     * @param field
     */
    public void lightningStrike(Field field) {
        field.makeDamaged();
    }
    
    /**
     * Used to roll a random chance for a disaster to happen, less than 4/5% each tick (month), and then strikes the randomly selected field
     */
    public void disasterRoll() {
        Random rand = new Random();
        int chance = rand.nextInt(500);

        if (chance <= 4) {
            // Disaster occurs
            ArrayList<? extends Field> chosenList;
            int listIndex = rand.nextInt(4);

            switch (listIndex) {
                case 0:
                    chosenList = gameEngine.residentialList;
                    break;
                case 1:
                    chosenList = gameEngine.industrialList;
                    break;
                case 2:
                    chosenList = gameEngine.serviceList;
                    break;
                case 3:
                    chosenList = gameEngine.specialList;
                    break;
                default:
                    // This should never happen, but just in case
                    return;
            }

            if (!chosenList.isEmpty()) {
                int index = rand.nextInt(chosenList.size());
                Field element = chosenList.get(index);

                element.makeDamaged();
                if (!gameEngine.damagedBuildings.contains(element)) {
                    gameEngine.damagedBuildings.add(element);
                }
                System.out.println("Disaster happened!");    
            }
        }
    }
    
    /**
     * For manually invoked disasters, same as the random roll function but without the initial chance for it to happen (still randomly selects building)
     * @param listIndex Used to select the specific building type
     */
    public void invokeDisaster(int listIndex) {
            Random rand = new Random();

            ArrayList<? extends Field> chosenList;

            switch (listIndex) {
                case 1:
                    chosenList = gameEngine.residentialList;
                    break;
                case 2:
                    chosenList = gameEngine.industrialList;
                    break;
                case 3:
                    chosenList = gameEngine.serviceList;
                    break;
                case 4:
                    chosenList = gameEngine.specialList;
                    break;
                default:
                    // This should never happen, but just in case
                    return;
            }

            if (!chosenList.isEmpty()) {
                int index = rand.nextInt(chosenList.size());
                Field element = chosenList.get(index);

                element.makeDamaged();
                if (!gameEngine.damagedBuildings.contains(element)) {
                    gameEngine.damagedBuildings.add(element);
                }
                System.out.println("Disaster happened!");    
            }
    }
    
    
}
