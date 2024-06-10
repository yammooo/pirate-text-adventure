package org.example.newgamecreator.map_creation;

import org.example.model.entities.Map;
import org.example.model.entities.Location;
import org.example.newgamecreator.entities_creation.EntitiesCreator;
import org.example.newgamecreator.map_creation.boriana_island.CobblestoneBeach;
import org.example.newgamecreator.map_creation.boriana_island.CoconutForest;
import org.example.newgamecreator.map_creation.boriana_island.DarkHouse;
import org.example.newgamecreator.map_creation.rosita_island.AbandonedHouse;
import org.example.newgamecreator.map_creation.rosita_island.PinkTree;
import org.example.newgamecreator.map_creation.rosita_island.PinkBeach;
import org.example.newgamecreator.map_creation.volcania_island.TreasureIsland;
import org.example.newgamecreator.map_creation.volcania_island.Volcano;
import org.example.newgamecreator.map_creation.volcania_island.WhiteBeach;
import org.example.pair.Pair;
import org.example.model.entities.Obstacle;

import java.util.ArrayList;
import java.util.HashMap;

import static org.example.newgamecreator.map_creation.volcania_island.GoldenBeach.createGoldenBeach;

public class MapCreator {

    private static final int NON_REQUIRED = 0;

    // Non-special ids are generated from this key
    private static final int FIRST_OBSTACLE_ID = 1;
    private static int obstacleIdCounter = FIRST_OBSTACLE_ID;

    // Treasure Island
    public static final int TREASURE_ISLAND_ID = 0;

    // Boriana Island



    // Rosita Island
    public static final int ABANDONED_HOUSE_ID = 10;
    public static final int PINK_TREE_ID = 11;
    public static final int GOLDEN_BEACH_ID = 12;


    // Volcania Island
    public static final int PINK_BEACH_ID = 20;
    public static final int WHITE_BEACH_ID = 21;
    public static final int VOLCANO_ID = 22;
    public static final int ABANDONED_ID = 23;
    public static final int COBBLESTONEBEACH_ID = 24;
    public static final int COCONUTFOREST_ID = 25;
    public static final int DARK_HOUSE_ID = 26;

    public static Map createMap() {

        ArrayList<Location> locations = new ArrayList<>();

        // GOLDEN BEACH LOCATION
        Location goldenBeach = createGoldenBeach(GOLDEN_BEACH_ID);
        goldenBeach.addAdjacentLocation(PINK_BEACH_ID);
        goldenBeach.addAdjacentLocation(VOLCANO_ID);
        goldenBeach.addAdjacentLocation(WHITE_BEACH_ID);
        locations.add(goldenBeach);
        //BLACK BEACH LOCATION
        Location blackBeach = PinkBeach.createPinkBeach(PINK_BEACH_ID);
        blackBeach.addAdjacentLocation(GOLDEN_BEACH_ID);
        blackBeach.addAdjacentLocation(PINK_TREE_ID);
        blackBeach.addAdjacentLocation(ABANDONED_ID);
        locations.add(blackBeach);
        //PINK FOREST
        Location pinkForest = PinkTree.createPinkTree(PINK_TREE_ID);
        pinkForest.addAdjacentLocation(PINK_BEACH_ID);
        locations.add(pinkForest);
        //VOLCANO LOCATION
        Location volcano = Volcano.createVolcano(VOLCANO_ID);
        volcano.addAdjacentLocation(GOLDEN_BEACH_ID);
        volcano.addAdjacentLocation(TREASURE_ISLAND_ID);
        locations.add(volcano);
        //TREASURE ISLAND LOCATION
        Location treasureIsland = TreasureIsland.createTreasureIsland(TREASURE_ISLAND_ID);
        treasureIsland.addAdjacentLocation(VOLCANO_ID);
        locations.add(treasureIsland);
        //ABANDONEDHOUSE
        Location abandonedHouse = AbandonedHouse.createAbandonedHouse(ABANDONED_ID);
        abandonedHouse.addAdjacentLocation(PINK_TREE_ID);
        abandonedHouse.addAdjacentLocation(PINK_BEACH_ID);
        locations.add(abandonedHouse);
        //WHITEBEACH
        Location whiteBeach = WhiteBeach.createWhiteBeach(WHITE_BEACH_ID);
        whiteBeach.addAdjacentLocation(VOLCANO_ID);
        whiteBeach.addAdjacentLocation(GOLDEN_BEACH_ID);
        whiteBeach.addAdjacentLocation(COBBLESTONEBEACH_ID);
        locations.add(whiteBeach);
        //COBBLESTONE_BEACH
        Location cobblestoneBeach= CobblestoneBeach.createCobblestoneBeach(COBBLESTONEBEACH_ID);
        cobblestoneBeach.addAdjacentLocation(WHITE_BEACH_ID);
        cobblestoneBeach.addAdjacentLocation(COCONUTFOREST_ID);
        locations.add(cobblestoneBeach);
        //COCONUT_FOREST
        Location coconut_forest= CoconutForest.createCoconutForest(COCONUTFOREST_ID);
        coconut_forest.addAdjacentLocation(COBBLESTONEBEACH_ID);
        coconut_forest.addAdjacentLocation(DARK_HOUSE_ID);
        locations.add(coconut_forest);
        //DARKHOUSE
        Location dark_house= DarkHouse.createDarkHouse(DARK_HOUSE_ID);
        dark_house.addAdjacentLocation(COCONUTFOREST_ID);
        locations.add(dark_house);


        // Create obstacles
        Obstacle sharkObstacle = new Obstacle(obstacleIdCounter++, "Shark", "A bloodthirsty hammerhead shark", EntitiesCreator.SPEAR_ID);
        Obstacle EelObstacle = new Obstacle(obstacleIdCounter++,"Eel","A gigantic electric eel hungry for you",EntitiesCreator.SWORD_ID);
        Obstacle PortalObstacle = new Obstacle(obstacleIdCounter++,"Portal", "A glowing dark blue portal that has the symbol of tree keys engraved on it",4);
        // Add obstacles to a map
        HashMap<Pair<Integer, Integer>, Obstacle> obstacles = new HashMap<>();
        obstacles.put(new Pair<>(GOLDEN_BEACH_ID, PINK_BEACH_ID), sharkObstacle);
        obstacles.put(new Pair<>(VOLCANO_ID, TREASURE_ISLAND_ID), PortalObstacle);
        obstacles.put(new Pair<>(WHITE_BEACH_ID, COBBLESTONEBEACH_ID), EelObstacle);
        // Create and return the map
        return new Map(locations, obstacles, GOLDEN_BEACH_ID);
    }
}