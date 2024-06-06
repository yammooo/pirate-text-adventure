package org.example.model;

import org.example.exceptions.AWSException;
import org.example.exceptions.BackpackWeightExceededException;
import org.example.exceptions.ItemNotFoundException;
import org.example.model.entities.*;
import org.example.model.entities.enums.WindowState;
import org.example.pair.Pair;
import org.example.util.AWSHandler;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

/*
 * in order to succeed in all tests of this class, both an internet access and resources/AWS/credentials.properties file are required
 */
class AppHandlerTest {

    @Test
    void singletonTest() {
        AppHandler instance1 = AppHandler.getInstance();
        AppHandler instance2 = AppHandler.getInstance();
        assertSame(instance1, instance2);
    }

    @Test
    void startNewGameTest() {
        AppHandler.getInstance().startNewGame();
        assertEquals(WindowState.GAME, AppHandler.getInstance().getAppState().getCurrentWindow());  // check if the state of the game has turned into GAME
    }

    @Test
    void startSavedGameTest() {
        AppHandler.getInstance().startSavedGame(1);
        assertEquals(WindowState.GAME, AppHandler.getInstance().getAppState().getCurrentWindow());  // check if the state of the game has turned into GAME
    }

    @Test
    void startSavedGameFailureTest() {
        try {
            AppHandler.getInstance().startSavedGame(AWSHandler.getInstance().countSavedGames() + 1);
            assertEquals(WindowState.MENU, AppHandler.getInstance().getAppState().getCurrentWindow());  // check if the state of the game remains set to MENU
        } catch (AWSException e) {
            fail("AWSException should not have been thrown here."); // this exception would be caught if countSaveGames() trigger it
        }
    }

    @Test
    void exitToMenuTest() {
        AppHandler.getInstance().exitToMenu();
        assertEquals(WindowState.MENU, AppHandler.getInstance().getAppState().getCurrentWindow());  // check if the state of the game has turned into MENU
    }

    @Test
    void moveWithNoObstacleTest() {
        Pirate pirate = new Pirate(new Backpack(10), 3);

        ArrayList<Integer> adjacentLocations1 = new ArrayList<>();
        adjacentLocations1.add(2);

        ArrayList<Location> locations = new ArrayList<>();
        Location location1 = new Location(1, "Location1", "Description1", new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), adjacentLocations1);
        Location location2 = new Location(2, "Location2", "Description2", new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
        locations.add(location1);
        locations.add(location2);

        // no obstacles in the path from location 1 to location 2

        Map map = new Map(locations, new HashMap<>(), 1);
        AppHandler.getInstance().getAppState().setGameState(new GameState(pirate, map, "Title1"));

        AppHandler.getInstance().move(2);
        assertEquals(2, AppHandler.getInstance().getAppState().getGameState().getMap().getPirateLocationID());  // check if current location of pirate has changed
    }

    @Test
    void moveLosingFightAgainstObstacleTest() {
        Pirate pirate = new Pirate(new Backpack(10), 3);

        // no items in the backpack, let alone the item to defeat the obstacle

        ArrayList<Integer> adjacentLocations1 = new ArrayList<>();
        adjacentLocations1.add(2);

        ArrayList<Location> locations = new ArrayList<>();
        Location location1 = new Location(1, "Location1", "Description1", new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), adjacentLocations1);
        Location location2 = new Location(2, "Location2", "Description2", new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
        locations.add(location1);
        locations.add(location2);

        HashMap<Pair<Integer, Integer>, Obstacle> obstacles = new HashMap<>();
        Obstacle obstacle = new Obstacle(1, "Obstacle1", "Description1", 1);
        obstacles.put(new Pair<>(1, 2), obstacle);

        Map map = new Map(locations, obstacles, 1);
        AppHandler.getInstance().getAppState().setGameState(new GameState(pirate, map, "Title1"));

        AppHandler.getInstance().move(2);
        assertEquals(1, AppHandler.getInstance().getAppState().getGameState().getMap().getPirateLocationID());  // check if pirate remains in the same location
    }

    @Test
    void moveWinningFightAgainstObstacleTest() {
        Backpack backpack = new Backpack(10);
        CollectableItem itemToDefeatObstacle = new CollectableItem(1, "Item1", "Description1", 1, 0);
        try {
            backpack.addItem(itemToDefeatObstacle); // item needed for defeat obstacle equipped
        } catch (BackpackWeightExceededException e) {
            fail("AWSException should not have been thrown here.");
        }

        Pirate pirate = new Pirate(backpack, 3);

        ArrayList<Integer> adjacentLocations1 = new ArrayList<>();
        adjacentLocations1.add(2);

        ArrayList<Location> locations = new ArrayList<>();
        Location location1 = new Location(1, "Location1", "Description1", new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), adjacentLocations1);
        Location location2 = new Location(2, "Location2", "Description2", new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
        locations.add(location1);
        locations.add(location2);

        HashMap<Pair<Integer, Integer>, Obstacle> obstacles = new HashMap<>();
        Obstacle obstacle = new Obstacle(1, "Obstacle1", "Description1", 1);
        obstacles.put(new Pair<>(1, 2), obstacle);

        Map map = new Map(locations, obstacles, 1);
        AppHandler.getInstance().getAppState().setGameState(new GameState(pirate, map, "Title1"));

        AppHandler.getInstance().move(2);
        assertEquals(2, AppHandler.getInstance().getAppState().getGameState().getMap().getPirateLocationID());  // check if locations has changed
    }

    @Test
    void gameOverTest() {
        Pirate pirate = new Pirate(new Backpack(10), 1);    // 1 life only and no items in the backpack

        ArrayList<Integer> adjacentLocations1 = new ArrayList<>();
        adjacentLocations1.add(2);

        ArrayList<Location> locations = new ArrayList<>();
        Location location1 = new Location(1, "Location1", "Description1", new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), adjacentLocations1);
        Location location2 = new Location(2, "Location2", "Description2", new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
        locations.add(location1);
        locations.add(location2);

        HashMap<Pair<Integer, Integer>, Obstacle> obstacles = new HashMap<>();
        Obstacle obstacle = new Obstacle(1, "Obstacle1", "Description1", 1);
        obstacles.put(new Pair<>(1, 2), obstacle);

        Map map = new Map(locations, obstacles, 1);
        AppHandler.getInstance().getAppState().setGameState(new GameState(pirate, map, "Title1"));

        AppHandler.getInstance().move(2);
        assertEquals(WindowState.GAME_OVER, AppHandler.getInstance().getAppState().getCurrentWindow()); // check if the state of the game has turned into GAME_OVER
    }

    @Test
    void gameWinTest() {
        Backpack backpack = new Backpack(10);
        CollectableItem itemToDefeatObstacle = new CollectableItem(4, "Legendary Key", "legendary key description", 1, 0);
        try {
            backpack.addItem(itemToDefeatObstacle); // item needed for pass through the portal that bring to the treasure island
        } catch (BackpackWeightExceededException e) {
            fail("AWSException should not have been thrown here.");
        }

        Pirate pirate = new Pirate(backpack, 3);

        ArrayList<Integer> adjacentLocations1 = new ArrayList<>();
        adjacentLocations1.add(0);  // 0 is the id of the treasure island location

        ArrayList<Location> locations = new ArrayList<>();
        Location location1 = new Location(1, "Location1", "Description1", new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), adjacentLocations1);
        Location location2 = new Location(0, "Treasure Island", "treasure island description", new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
        locations.add(location1);
        locations.add(location2);

        Map map = new Map(locations, new HashMap<>(), 1);
        AppHandler.getInstance().getAppState().setGameState(new GameState(pirate, map, "Title1"));

        AppHandler.getInstance().move(0);
        assertEquals(WindowState.GAME_WIN, AppHandler.getInstance().getAppState().getCurrentWindow()); // check if the state of the game has turned into GAME_WIN
    }


    @Test
    void pickUpItemWithNoRequiredItemTest() {
        Pirate pirate = new Pirate(new Backpack(10), 3);

        ArrayList<Location> locations = new ArrayList<>();
        Location location = new Location(1, "Location1", "Description1", new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
        location.addCollectableItem(new CollectableItem(1, "Item1", "Description1", 1, 0)); // item to collect with no required item
        locations.add(location);

        Map map = new Map(locations, new HashMap<>(), 1);
        AppHandler.getInstance().getAppState().setGameState(new GameState(pirate, map, "Title1"));

        AppHandler.getInstance().pickUpItem(1);
        assertTrue(pirate.getBackpack().isItemEquipped(1));
    }

    @Test
    void pickUpItemWithRequiredItemTest() {
        Pirate pirate = new Pirate(new Backpack(10), 3);

        ArrayList<Location> locations = new ArrayList<>();
        Location location = new Location(1, "Location1", "Description1", new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
        location.addCollectableItem(new CollectableItem(1, "Item1", "Description1", 1, 2)); // item to collect with required item
        locations.add(location);

        Map map = new Map(locations, new HashMap<>(), 1);
        AppHandler.getInstance().getAppState().setGameState(new GameState(pirate, map, "Title1"));

        AppHandler.getInstance().pickUpItem(1);
        assertFalse(pirate.getBackpack().isItemEquipped(1));
    }

    @Test
    void dropItemTest() {
        try {
            Pirate pirate = new Pirate(new Backpack(10), 3);
            CollectableItem itemToDrop = new CollectableItem(1, "Item1", "Description1", 1, 0); // item to drop
            pirate.getBackpack().addItem(itemToDrop);

            ArrayList<Location> locations = new ArrayList<>();
            Location location = new Location(1, "Location1", "Description1", new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
            locations.add(location);

            Map map = new Map(locations, new HashMap<>(), 1);
            AppHandler.getInstance().getAppState().setGameState(new GameState(pirate, map, "Title1"));

            AppHandler.getInstance().dropItem(1);
            // check both whether the item is not in the backpack anymore and if it has added in the current island
            assertTrue(!AppHandler.getInstance().getAppState().getGameState().getPirate().getBackpack().isItemEquipped(1) && location.getCollectableItemByID(1) == itemToDrop);
        } catch (BackpackWeightExceededException | ItemNotFoundException e) {
            fail("BackpackWeightExceededException should not have been thrown.");
        }
    }
}