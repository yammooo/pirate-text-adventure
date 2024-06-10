package org.example.json_translator;

import com.google.gson.GsonBuilder;
import org.example.model.GameState;
import com.google.gson.Gson;
import org.example.pair.Pair;

/**
 * GameStateTranslator is a utility class responsible for translating GameStates to JSON format
 * and vice versa. It utilizes the Gson library for JSON serialization and deserialization.
 * This class also interfaces with other components such as PairAdapter for custom serialization
 * of Pair objects.
 */
public class GameStateTranslator {

    private static final GsonBuilder builder = new GsonBuilder()
            .registerTypeAdapter(Pair.class, new PairAdapter())
            .setPrettyPrinting();

    private static final Gson gson = builder.create();

    /**
     * Translates JSON data into a GameState object.
     *
     * @param json the JSON representation of the GameState
     * @return the reconstructed GameState object
     * @throws IllegalArgumentException if the JSON parsing fails
     */
    public static GameState jsonToGameState(String json) {

        if (json != null) {
            System.out.println("GameState creation successful");
            return gson.fromJson(json, GameState.class);
        } else {
            System.out.println("GameState creation failed");
            throw new IllegalArgumentException("GameState creation failed");
        }
    }

    /**
     * Translates a GameState object into JSON format.
     *
     * @param gameState the GameState object to be translated
     * @return the JSON representation of the GameState
     * @throws IllegalArgumentException if the JSON creation fails
     */
    public static String gameStateToJson(GameState gameState) {

        String json = gson.toJson(gameState);

        if (json == null) {
            throw new IllegalArgumentException("Json creation failed");
        }

        return json;
    }
}
