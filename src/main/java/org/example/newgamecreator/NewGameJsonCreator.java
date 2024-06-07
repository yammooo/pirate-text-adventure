package org.example.newgamecreator;
import org.example.model.GameState;
import org.example.model.entities.*;
import org.example.json_translator.GameStateTranslator;

import java.io.FileWriter;
import java.io.IOException;

import static org.example.newgamecreator.map_creation.MapCreator.createMap;

public class NewGameJsonCreator {

    private static final int MAX_BACKPACK_WEIGHT = 10;
    private static final int MAX_PLAYER_LIVES = 3;

    public static void main(String[] args) {
        // Step 1: Create an instance of GameState and populate it with values

        Map pirateMap = createMap();
        Pirate pirate = new Pirate(new Backpack(MAX_BACKPACK_WEIGHT), MAX_PLAYER_LIVES);
        GameState gameState = new GameState(pirate, pirateMap, "New Game State");


        // Step 2: Use GameStateTranslator to convert the GameState instance to JSON
        String json = GameStateTranslator.gameStateToJson(gameState);

        // Step 3: Write the JSON string to a file in "src/main/resources"
        try (FileWriter file = new FileWriter("src/main/resources/json/defaultGameState.json")) {
            file.write(json);
            System.out.println("Successfully Copied JSON Object to File...");
            System.out.println("\nJSON Object: " + json);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
