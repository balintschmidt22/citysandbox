package model;

import model.zones.Industrial;
import model.zones.Service;
import java.util.*;
import model.buildings.School;
import model.buildings.SpecialBuilding;
import model.buildings.University;
import model.zones.Zone;

/**
 * Used for the graph search algorithms to detect connections via roads
 * @author Arni
 */
public class GraphSearch {
    
    /**
     *
     */
    public Board board;
    
    /**
     * Returns if the field has a path to the main road
     * @param startX
     * @param startY
     * @return
     */
    public boolean hasPathToMainRoad(int startX, int startY) {
        // Check if the start position is valid
        if (!isValidPosition(startX, startY)) {
            return false;
        }

        // If the current cell is not a Residential/Industrial/Service zone or Road, return false
        Field startField = board.getFieldAt(startX, startY);
        if (!(startField instanceof Zone || startField instanceof Road || startField instanceof SpecialBuilding)) {
            return false;
        }

        // If the current cell is a Main road, return true
        if (startField instanceof Road && ((Road) startField).isMainRoad()) {
            return true;
        }

        // Mark the start position as visited
        startField.setVisited(true);

        // Explore neighbors of the current position
        int[][] directions = {{-1, 0}, {0, -1}, {1, 0}, {0, 1}}; // Up, Left, Down, Right
        for (int[] direction : directions) {
            int newX = startX + direction[0];
            int newY = startY + direction[1];
            if (isValidPosition(newX, newY)) {
                Field newField = board.getFieldAt(newX, newY);
                if (!newField.isVisited() && newField instanceof Road) {
                    if (hasPathToMainRoad(newX, newY)) {
                        //switch deletability to non-deletable
                        ((Road)newField).setUndeletable();
                        return true;
                    }
                }
            }
        }

        return false; // No path found
    }


    /**
     * Helper method to check if a position is valid within the board boundaries
     * @param x
     * @param y
     * @return
     */
    public boolean isValidPosition(int x, int y) {
        boolean value = (x >= 0 && x < board.getSizeX() && y >= 0 && y < board.getSizeY());
        return value;
    }
    
    
    //new part :D
    /**
     * Check if there is a path to an industrial or service zone from a residential zone, alongside roads.
     * @param startX The x-coordinate of the start position
     * @param startY The y-coordinate of the start position
     * @return An ArrayList of Field objects representing the industrial or service zones found, or an empty ArrayList if none found
     */
    public ArrayList<Field> findPathToWork(int startX, int startY) {
        ArrayList<Field> industrialOrServiceZones = new ArrayList<>();

        // Check if the start position is valid
        if (!isValidPosition(startX, startY)) {
            return industrialOrServiceZones;
        }

        // If the current cell is not a Residential zone, return empty ArrayList
        Field startField = board.getFieldAt(startX, startY);

        // Mark the start position as visited
        startField.setVisited(true);

        // Explore neighbors of the current position
        int[][] directions = {{-1, 0}, {0, -1}, {1, 0}, {0, 1}}; // Up, Left, Down, Right
        for (int[] direction : directions) {
            int newX = startX + direction[0];
            int newY = startY + direction[1];
            if (isValidPosition(newX, newY)) {
                Field newField = board.getFieldAt(newX, newY);
                if (!newField.isVisited()) {
                    if (newField instanceof Road) {
                        // If the neighbor is a Road, recursively check for industrial or service zones from that position
                        ArrayList<Field> zones = findPathToWork(newX, newY);
                        
                        industrialOrServiceZones.addAll(zones);
                    } else if (newField instanceof Industrial || newField instanceof Service) {
                        // If the neighbor is an Industrial or Service zone, add it to the result list
                        industrialOrServiceZones.add(newField);
                    }
                }
            }
        }
        
        //Made a modification so that only workplaces that are also connected
        //to the main road are added to the list
        
        ArrayList<Field> alsoConnectedToMainRoad = new ArrayList<>();
        for (Field field : industrialOrServiceZones) {
        if (((Zone)field).isConnectedToMainRoad()) {
            alsoConnectedToMainRoad.add(field);
        }
    }

        
        return alsoConnectedToMainRoad;
    }
    
    
    public ArrayList<Field> findPathToEducation(int startX, int startY) {
        ArrayList<Field> SchoolOrUniversity = new ArrayList<>();

        // Check if the start position is valid
        if (!isValidPosition(startX, startY)) {
            return SchoolOrUniversity;
        }

        // If the current cell is not a Residential zone, return empty ArrayList
        Field startField = board.getFieldAt(startX, startY);
//        if (!(startField instanceof Residential) || !(startField instanceof Road)) {
//            return industrialOrServiceZones;
//        }

        // Mark the start position as visited
        startField.setVisited(true);

        // Explore neighbors of the current position
        int[][] directions = {{-1, 0}, {0, -1}, {1, 0}, {0, 1}}; // Up, Left, Down, Right
        for (int[] direction : directions) {
            int newX = startX + direction[0];
            int newY = startY + direction[1];
            if (isValidPosition(newX, newY)) {
                Field newField = board.getFieldAt(newX, newY);
                if (!newField.isVisited()) {
                    if (newField instanceof Road) {
                        // If the neighbor is a Road, recursively check for industrial or service zones from that position
                        ArrayList<Field> zones = findPathToEducation(newX, newY);
                        
                        SchoolOrUniversity.addAll(zones);
                    } else if (newField instanceof School || newField instanceof University) {
                        // If the neighbor is an Industrial or Service zone, add it to the result list
                        SchoolOrUniversity.add(newField);
                    }
                }
            }
        }
        //System.out.println(industrialOrServiceZones);
        
        //Made a modification so that only workplaces that are also connected
        //to the main road are added to the list
        
        ArrayList<Field> alsoConnectedToMainRoad = new ArrayList<>();
        for (Field field : SchoolOrUniversity) {
        if (field.isConnectedToMainRoad()) {
            alsoConnectedToMainRoad.add(field);
        }
    }

        
        return alsoConnectedToMainRoad;
    }
}
