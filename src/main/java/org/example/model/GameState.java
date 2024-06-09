package org.example.model;

import org.example.model.entities.Pirate;
import org.example.model.entities.Map;

/**
 * Represents the state of the game, including the pirate, the map, and the title of the game instance saved in the AWS.
 */
public class GameState {
    private String title;
    private final Pirate pirate;
    private final Map map;

    /**
     * Constructs a GameState with the specified pirate, map, and title.
     * @param pirate the pirate character in the game
     * @param map the map of the game world
     * @param title the title of the game
     */
    public GameState(Pirate pirate, Map map, String title) {
        this.pirate = pirate;
        this.map = map;
        this.title = title;
    }

    /**
     * Returns the pirate character in the game.
     * @return the pirate character
     */
    public Pirate getPirate() {
        return pirate;
    }

    /**
     * Returns the map of the game world.
     * @return the map of the game world
     */
    public Map getMap() {
        return map;
    }

    /**
     * Returns the title of the game.
     * @return the title of the game
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets a new title for the game.
     * @param newTitle the new title of the game
     */
    public void setTitle(String newTitle) {
        title = newTitle;
    }
}
