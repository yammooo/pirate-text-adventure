package org.example.model;

import org.example.model.entities.Pirate;
import org.example.model.entities.Map;

public class GameState {
    private String title;
    private final Pirate pirate;
    private final Map map;

    private GameState(Pirate pirate, Map map) {
        this.pirate = pirate;
        this.map = map;
    }

    public Pirate getPirate() {
        return pirate;
    }

    public Map getMap() {
        return map;
    }

    public String getTitle() { return title; }

    public void setTitle(String newTitle) { title = newTitle; }

}