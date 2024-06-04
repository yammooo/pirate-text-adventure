package org.example.model;

import org.example.model.entities.enums.WindowState;

public class AppState {
    private static AppState instance;
    private GameState gameState;
    private WindowState currentWindow;
    private final UserQueryResult lastUserQueryResult;

    private AppState() {
        gameState = null;
        currentWindow = WindowState.MENU;
        lastUserQueryResult = new UserQueryResult("", false);
    }

    public static AppState getInstance() {
        if (instance == null) {
            instance = new AppState();
        }
        return instance;
    }

    public GameState getGameState() {
        return gameState;
    }

    public WindowState getCurrentWindow() {
        return currentWindow;
    }

    public UserQueryResult getLastUserQueryResult() {
        return lastUserQueryResult;
    }

    public void setGameState(GameState gameState) {
        this.gameState = gameState;
    }

    public void setWindowToMenu() {
        currentWindow = WindowState.MENU;
    }

    public void setWindowToGame() {
        currentWindow = WindowState.GAME;
    }
}