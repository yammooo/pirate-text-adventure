package org.example.util;

import org.example.model.GameState;
import com.google.gson.Gson;

public class GameStateTranslator {

    //JSON ----->>>>> GAMESTATE

    public GameState jsonToGameState(String json) {

        Gson gson = new Gson();

        if (json != null) {
            return gson.fromJson(json, GameState.class);
        } else {
            throw new IllegalArgumentException("GameState creation failed");
        }
    }

    //GAMESTATE ----->>>> JSON

    public static String gameStateToJson(GameState gameState) {

        Gson gson = new Gson();

        String json = gson.toJson(gameState);

        if (json == null) {
            throw new IllegalArgumentException("Json creation failed");
        }

        return json;

    }

}