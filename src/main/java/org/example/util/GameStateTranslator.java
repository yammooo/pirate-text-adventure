package org.example.util;

import com.google.gson.GsonBuilder;
import org.example.model.GameState;
import com.google.gson.Gson;

public class GameStateTranslator {

    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    //JSON ----->>>>> GAMESTATE

    public GameState jsonToGameState(String json) {

        if (json != null) {
            return gson.fromJson(json, GameState.class);
        } else {
            throw new IllegalArgumentException("GameState creation failed");
        }
    }

    //GAMESTATE ----->>>> JSON

    public static String gameStateToJson(GameState gameState) {

        String json = gson.toJson(gameState);

        if (json == null) {
            throw new IllegalArgumentException("Json creation failed");
        }

        return json;

    }

}