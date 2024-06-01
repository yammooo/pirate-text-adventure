package org.example.model;

import org.example.model.entities.Pirate;
import org.example.model.entities.Map;

public class GameState {
    private String title;
    private final Pirate pirate;
    private final Map map;

    public GameState(Pirate pirate, Map map, String title) {
        this.pirate = pirate;
        this.map = map;
        this.title = title;
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