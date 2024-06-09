package org.example.model;

import org.example.model.entities.enums.WindowState;

/**
 * Maintains the current state of the entire application by keeping saved all its data
 */
public class AppState {
    private static AppState instance;
    private GameState gameState;
    private WindowState currentWindow;
    private final UserQueryResult lastUserQueryResult;

    /**
     * Private constructor to implement Singleton pattern.
     * Initializes the current window state to MENU.
     */
    private AppState() {
        gameState = null;
        currentWindow = WindowState.MENU;
        lastUserQueryResult = new UserQueryResult("App state successfully created", true);
    }

    /**
     * Returns the singleton instance of AppState.
     * @return the singleton instance of AppState.
     */
    public static AppState getInstance() {
        if (instance == null) {
            instance = new AppState();
        }
        return instance;
    }

    /**
     * Returns the current GameState.
     * @return the current GameState.
     */
    public GameState getGameState() {
        return gameState;
    }

    /**
     * Sets the current GameState.
     * @param gameState the new GameState to set.
     */
    public void setGameState(GameState gameState) {
        this.gameState = gameState;
    }

    /**
     * Returns the current window state.
     * @return the current window state.
     */
    public WindowState getCurrentWindow() {
        return currentWindow;
    }

    /**
     * Sets the current window state to MENU.
     */
    public void setWindowToMenu() {
        currentWindow = WindowState.MENU;
    }

    /**
     * Sets the current window state to GAME.
     */
    public void setWindowToGame() {
        currentWindow = WindowState.GAME;
    }

    /**
     * Sets the current window state to GAME_OVER.
     */
    public void setWindowToGameOver() {
        currentWindow = WindowState.GAME_OVER;
    }

    /**
     * Sets the current window state to GAME_WIN.
     */
    public void setWindowToGameWin() {
        currentWindow = WindowState.GAME_WIN;
    }

    /**
     * Returns the result of the last user query.
     * @return the result of the last user query.
     */
    public UserQueryResult getLastUserQueryResult() {
        return lastUserQueryResult;
    }
}
