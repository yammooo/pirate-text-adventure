package org.example.model.entities;

import org.example.pair.Pair;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * The Map class represents the game world.
 * It contains a list of locations, a map of obstacles between locations, and the current location of the pirate.
 * The pirate can move between locations, encounter obstacles, and interact with the entities in a location.
 */
public class Map{
    private final ArrayList<Location> locations;
    private final HashMap<Pair<Integer, Integer>, Obstacle> obstacles;
    private int currentPirateLocationID;

    /**
     * Constructs a new Map with the given locations, obstacles, and the initial location of the pirate.
     *
     * @param locations the locations in the Map
     * @param obstacles the obstacles between locations in the Map
     * @param currentPirateLocationID the initial location of the pirate
     */
    public Map(ArrayList<Location> locations, HashMap<Pair<Integer, Integer>, Obstacle> obstacles, int currentPirateLocationID) {
        this.locations = locations != null ? new ArrayList<>(locations) : new ArrayList<>();
        this.obstacles = obstacles != null ? new HashMap<>(obstacles) : new HashMap<>();
        this.currentPirateLocationID = currentPirateLocationID;
    }

    // Getters

    /**
     * Returns the Location with the given id.
     *
     * @param id the id of the Location
     * @return the Location with the given id, or null if no such Location exists
     */
    public Location getLocationById(int id) {
        for (Location location : locations) {
            if (location.getID() == id) {
                return location;
            }
        }
        return null;
    }

    /**
     * Returns the Obstacle between the Locations with the given ids.
     *
     * @param id1 the id of the first Location
     * @param id2 the id of the second Location
     * @return the Obstacle between the Locations with the given ids, or null if no such Obstacle exists
     */
    public Obstacle getObstacleByLocationsID(int id1, int id2) {
        Obstacle obstacle = obstacles.get(new Pair<>(id1, id2));
        if(obstacle == null)
            obstacle = obstacles.get(new Pair<>(id2, id1));
        return obstacle;
    }

    /**
     * Returns the id of the Location where the pirate is currently located.
     *
     * @return the id of the Location where the pirate is currently located
     */
    public int getPirateLocationID() {
        return currentPirateLocationID;
    }

    // Setters

    /**
     * Sets the id of the Location where the pirate is currently located.
     *
     * @param id the id of the Location where the pirate is currently located
     */
    public void setPirateLocationID(int id) {
        this.currentPirateLocationID = id;
    }

    /**
     * Removes the Obstacle between the Locations with the given ids.
     *
     * @param id1 the id of the first Location
     * @param id2 the id of the second Location
     * @return the removed Obstacle, or null if no such Obstacle existed
     */
    public Obstacle removeObstacleByLocationsID(int id1, int id2) {
        Obstacle obstacle = obstacles.remove(new Pair<>(id1, id2));
        if(obstacle == null)
            obstacle = obstacles.remove(new Pair<>(id2, id1));
        return obstacle;
    }

}