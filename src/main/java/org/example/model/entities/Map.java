package org.example.model.entities;

import java.util.ArrayList;
import java.util.HashMap;

import org.example.pair.Pair;

public class Map{
    private final ArrayList<Location> locations;
    private final HashMap<Pair<Integer, Integer>, Obstacle> obstacles;
    private int currentPirateLocationID;

    public Map(ArrayList<Location> locations, HashMap<Pair<Integer, Integer>, Obstacle> obstacles, int currentPirateLocationID) {
        this.locations = locations != null ? new ArrayList<>(locations) : new ArrayList<>();
        this.obstacles = obstacles != null ? new HashMap<>(obstacles) : new HashMap<>();
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

    // id1->id2 path is the same of id2->id1
    public Obstacle getObstacleByLocationsID(int id1, int id2) {
        Obstacle obstacle = obstacles.get(new Pair<>(id1, id2));
        if(obstacle == null)
            obstacle = obstacles.get(new Pair<>(id2, id1));
        return obstacle;
    }

    public int getPirateLocationID() {
        return currentPirateLocationID;
    }


    //Setters

    public void setPirateLocationID(int id) {
        this.currentPirateLocationID = id;
    }



    public Obstacle removeObstacleByLocationsID(int id1, int id2) {
        Obstacle obstacle = obstacles.remove(new Pair<>(id1, id2));
        if(obstacle == null)
            obstacle = obstacles.remove(new Pair<>(id2, id1));
        return obstacle;
    }

}