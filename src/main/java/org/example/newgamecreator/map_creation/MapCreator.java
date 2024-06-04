package org.example.newgamecreator.map_creation;

import org.example.model.entities.Map;
import org.example.model.entities.Location;
import org.example.newgamecreator.entities_creation.EntitiesCreator;
import org.example.newgamecreator.map_creation.rosita_island.PinkForest;
import org.example.newgamecreator.map_creation.volcania_island.BlackBeach;
import org.example.pair.Pair;
import org.example.model.entities.Obstacle;

import java.util.ArrayList;
import java.util.HashMap;

import static org.example.newgamecreator.map_creation.rosita_island.GoldenBeach.createGoldenBeach;

public class MapCreator {

    private static final int NON_REQUIRED = 0;

    // Non-special ids are generated from this key
    private static final int FIRST_OBSTACLE_ID = 1;
    private static int obstacleIdCounter = FIRST_OBSTACLE_ID;

    // Boriana Island



    // Rosita Island
    public static final int ABANDONED_HOUSE_ID = 10;
    public static final int PINK_FOREST_ID = 11;
    public static final int GOLDEN_BEACH_ID = 12;


    // Volcania Island
    public static final int BLACK_BEACH_ID = 20;
    public static final int WHITE_BEACH_ID = 21;

    public static Map createMap() {

        ArrayList<Location> locations = new ArrayList<>();

        // Create locations
        Location goldenBeach = createGoldenBeach(GOLDEN_BEACH_ID);
        goldenBeach.addAdjacentLocation(BLACK_BEACH_ID);
        locations.add(goldenBeach);

        Location blackBeach = BlackBeach.createBlackBeach(BLACK_BEACH_ID);
        blackBeach.addAdjacentLocation(GOLDEN_BEACH_ID);
        blackBeach.addAdjacentLocation(PINK_FOREST_ID);


        locations.add(blackBeach);

        Location pinkForest = PinkForest.createPinkForest(PINK_FOREST_ID);
        pinkForest.addAdjacentLocation(BLACK_BEACH_ID);
        locations.add(pinkForest);


        // Create obstacles
        Obstacle sharkObstacle = new Obstacle(obstacleIdCounter++, "Shark", "A bloodthirsty hammerhead shark", EntitiesCreator.SPEAR_ID);

        // Add obstacles to a map
        HashMap<Pair<Integer, Integer>, Obstacle> obstacles = new HashMap<>();
        obstacles.put(new Pair<>(GOLDEN_BEACH_ID, BLACK_BEACH_ID), sharkObstacle);

        // Create and return the map
        return new Map(locations, obstacles, GOLDEN_BEACH_ID);
    }
}