package org.example.model;

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

class GameStateTest {

    private GameState gameState;
    private Pirate pirate;
    private Map map;

    @BeforeEach
    void setUp() {
        Backpack backpack = new Backpack(50); // maximum weight is set to 50
        pirate = new Pirate(backpack, 3); // maximum lives is set to 3

        ArrayList<Location> locations = new ArrayList<>();
        HashMap<Pair<Integer, Integer>, Obstacle> obstacles = new HashMap<>();
        map = new Map(locations, obstacles, 1); // current pirate location ID is set to 1

        gameState = new GameState(pirate, map, "Title1");
    }

    @Test
    void getPirateReturnsCorrectPirate() {
        assertEquals(pirate, gameState.getPirate());
    }

    @Test
    void getMapReturnsCorrectMap() {
        assertEquals(map, gameState.getMap());
    }

    @Test
    void getTitleReturnsCorrectTitle() {
        assertEquals("Title1", gameState.getTitle());
    }

    @Test
    void setTitleChangesTitle() {
        gameState.setTitle("Title2");
        assertEquals("Title2", gameState.getTitle());
    }
}