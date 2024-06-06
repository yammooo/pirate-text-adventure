package org.example.util;

import com.google.gson.JsonSyntaxException;
import org.example.model.entities.*;
import org.example.pair.Pair;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class GameStateTranslatorTest {

    @Test
    void jsonToGameState_createsGameStateFromValidJson() {
        String validJson = "{\"key\":\"value\"}"; // Replace with a valid GameState JSON
        assertDoesNotThrow(() -> GameStateTranslator.jsonToGameState(validJson));
    }

    @Test
    void jsonToGameState_throwsExceptionWithNullJson() {
        assertThrows(IllegalArgumentException.class, () -> GameStateTranslator.jsonToGameState(null));
    }

    @Test
    void jsonToGameState_throwsExceptionWithInvalidJson() {
        String invalidJson = "invalid json";
        assertThrows(JsonSyntaxException.class, () -> GameStateTranslator.jsonToGameState(invalidJson));
    }

    @Test
    void gameStateToJson_createsJsonFromValidGameState() {
        Backpack backpack = new Backpack(10);
        Pirate pirate = new Pirate(backpack, 3);
        ArrayList<Location> locations = new ArrayList<>();
        HashMap<Pair<Integer, Integer>, Obstacle> obstacles = new HashMap<>();
        Map map = new Map(locations, obstacles, 1);
        org.example.model.GameState validGameState = new org.example.model.GameState(pirate, map, "Test Title");
        assertDoesNotThrow(() -> GameStateTranslator.gameStateToJson(validGameState));
    }

}