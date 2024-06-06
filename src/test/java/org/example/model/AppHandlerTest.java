package org.example.model;

import org.example.exceptions.AWSException;
import org.example.exceptions.BackpackWeightExceededException;
import org.example.model.entities.*;
import org.example.model.entities.enums.WindowState;
import org.example.util.AWSHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class AppHandlerTest {

    private AppHandler appHandler;
    private AppState appState;
    private AWSHandler awsHandler;

    @BeforeEach
    void setUp() {
        appHandler = AppHandler.getInstance();
        appState = AppState.getInstance();
        awsHandler = mock(AWSHandler.class);
        appHandler.addObserver(awsHandler);
    }

    @Test
    void getInstanceReturnsSingletonInstance() {
        AppHandler instance1 = AppHandler.getInstance();
        AppHandler instance2 = AppHandler.getInstance();
        assertSame(instance1, instance2);
    }

    @Test
    void getAppStateReturnsCorrectAppState() {
        assertEquals(appState, appHandler.getAppState());
    }

    @Test
    void startNewGameChangesWindowToGame() {
        appHandler.startNewGame();
        assertEquals(WindowState.GAME, appState.getCurrentWindow());
    }

    @Test
    void startSavedGameChangesWindowToGame() {
        try {
            when(awsHandler.getSavedGames(anyInt())).thenReturn("{}");
            appHandler.startSavedGame(1);
            assertEquals(WindowState.GAME, appState.getCurrentWindow());
        } catch (AWSException e) {
            fail("AWSException should not have been thrown.");
        }
    }

    @Test
    void startSavedGameFailsGracefullyWhenAWSExceptionOccurs() {
        try {
            when(awsHandler.getSavedGames(anyInt())).thenThrow(new AWSException("AWS error"));
            appHandler.startSavedGame(1);
            assertEquals(WindowState.MENU, appState.getCurrentWindow());
        } catch (AWSException e) {
            // Expected exception
        }
    }

    @Test
    void exitToMenuChangesWindowToMenu() {
        appHandler.exitToMenu();
        assertEquals(WindowState.MENU, appState.getCurrentWindow());
    }

    @Test
    void moveChangesLocationWhenNoObstacle() {
        Pirate pirate = new Pirate(new Backpack(50), 3);

        ArrayList<Integer> adjecentLocations = new ArrayList<>();
        adjecentLocations.add(2);
        adjecentLocations.add(1);

        ArrayList<Location> locations = new ArrayList<>();
        Location location1 = new Location(1, "Location1", "Description1", new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), adjecentLocations);
        Location location2 = new Location(2, "Location2", "Description2", new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), adjecentLocations);
        locations.add(location1);
        locations.add(location2);

        Map map = new Map(locations, new HashMap<>(), 1);
        appState.setGameState(new GameState(pirate, map, "Title1"));
        appHandler.move(2);
        assertEquals(2, appState.getGameState().getMap().getPirateLocationID());
    }

    @Test
    void pickUpItemAddsItemToBackpack() {
        Pirate pirate = new Pirate(new Backpack(50), 3);
        Map map = new Map(new ArrayList<>(), new HashMap<>(), 1);
        appState.setGameState(new GameState(pirate, map, "Title1"));
        appHandler.pickUpItem(1);
        assertTrue(pirate.getBackpack().isItemEquipped(1));
    }

    @Test
    void dropItemRemovesItemFromBackpack() {
        try {
            Pirate pirate = new Pirate(new Backpack(50), 3);
            Map map = new Map(new ArrayList<>(), new HashMap<>(), 1);
            appState.setGameState(new GameState(pirate, map, "Title1"));
            pirate.getBackpack().addItem(new CollectableItem(1, "Item1", "Description1", 1, 0));
            appHandler.dropItem(1);
            assertFalse(pirate.getBackpack().isItemEquipped(1));
        } catch (BackpackWeightExceededException e) {
            fail("BackpackWeightExceededException should not have been thrown.");
        }
    }
}