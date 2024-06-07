package org.example.json_translator;

import com.google.gson.GsonBuilder;
import org.example.model.GameState;
import com.google.gson.Gson;
import org.example.pair.Pair;

public class GameStateTranslator {

    private static final GsonBuilder builder = new GsonBuilder()
            .registerTypeAdapter(Pair.class, new PairAdapter())
            .setPrettyPrinting();

    private static final Gson gson = builder.create();

    //JSON ----->>>>> GAMESTATE

    public static GameState jsonToGameState(String json) {

        if (json != null) {
            System.out.println("GameState creation successful");
            return gson.fromJson(json, GameState.class);
        } else {
            System.out.println("GameState creation failed");
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