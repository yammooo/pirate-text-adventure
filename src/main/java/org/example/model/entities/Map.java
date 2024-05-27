package org.example.model.entities;

import java.util.ArrayList;
import java.util.HashMap;

import org.example.pair.Pair;

public class Map{
    private ArrayList<Location> locations;
    private HashMap<Pair<Integer, Integer>, Obstacle> obstacles;
    private int currentPirateLocationID;

    public Map(ArrayList<Location> locations, HashMap<Pair<Integer, Integer>, Obstacle> obstacles, int currentPirateLocationID) {
        this.locations = locations;
        this.obstacles = obstacles;
        this.currentPirateLocationID = currentPirateLocationID;
    }

    //Getters

    public Location getLocationById(int id) {
        for (Location location : locations) {
            if (location.getID() == id) {
                return location;
            }
        }
        return null;
    }

    public Obstacle getObstacleByLocationsID(int id1, int id2) {
        return obstacles.get(new Pair<>(id1, id2));
    }

    public int getPirateLocationID() {
        return currentPirateLocationID;
    }

    //Setters

    public void setPirateLocationID(int id) {
        this.currentPirateLocationID = id;
    }

    public void addLocation(Location location) {
        locations.add(location);
    }

    public void addObstacle(int id1, int id2, Obstacle obstacle) {
        obstacles.put(new Pair<>(id1, id2), obstacle);
    }


}
