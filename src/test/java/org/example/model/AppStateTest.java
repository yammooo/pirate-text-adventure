package org.example.model;

import org.example.model.entities.enums.WindowState;
import org.example.model.entities.Pirate;
import org.example.model.entities.Map;
import org.example.model.entities.Backpack;
import org.example.model.entities.Location;
import org.example.pair.Pair;
import org.example.model.entities.Obstacle;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class AppStateTest {

    private AppState appState;
    private GameState gameState;

    @BeforeEach
    void setUp() {
        Backpack backpack = new Backpack(50); // maximum weight is set to 50
        Pirate pirate = new Pirate(backpack, 3); // maximum lives is set to 3

        ArrayList<Location> locations = new ArrayList<>();
        HashMap<Pair<Integer, Integer>, Obstacle> obstacles = new HashMap<>();
        Map map = new Map(locations, obstacles, 1); // current pirate location ID is set to 1

        gameState = new GameState(pirate, map, "Title1");
        appState = AppState.getInstance();
    }

    @Test
    void getInstanceReturnsSingletonInstance() {
        AppState instance1 = AppState.getInstance();
        AppState instance2 = AppState.getInstance();
        assertSame(instance1, instance2);
    }

    @Test
    void getGameStateReturnsCorrectGameState() {
        appState.setGameState(gameState);
        assertEquals(gameState, appState.getGameState());
    }

    @Test
    void getCurrentWindowReturnsCorrectWindow() {
        AppState.getInstance().setWindowToMenu();
        assertEquals(WindowState.MENU, appState.getCurrentWindow());
    }

    @Test
    void getLastUserQueryResultReturnsCorrectResult() {
        UserQueryResult result = appState.getLastUserQueryResult();
        assertNotNull(result);
        assertFalse(result.getSuccess());
        assertEquals("", result.getResult());
    }

    @Test
    void setGameStateChangesGameState() {
        appState.setGameState(gameState);
        assertEquals(gameState, appState.getGameState());
    }

    @Test
    void setWindowToMenuChangesWindow() {
        appState.setWindowToMenu();
        assertEquals(WindowState.MENU, appState.getCurrentWindow());
    }

    @Test
    void setWindowToGameChangesWindow() {
        appState.setWindowToGame();
        assertEquals(WindowState.GAME, appState.getCurrentWindow());
    }

    @Test
    void setWindowToGameOverChangesWindow() {
        appState.setWindowToGameOver();
        assertEquals(WindowState.GAME_OVER, appState.getCurrentWindow());
    }

    @Test
    void setWindowToGameWinChangesWindow() {
        appState.setWindowToGameWin();
        assertEquals(WindowState.GAME_WIN, appState.getCurrentWindow());
    }
}