package org.example.util;

import org.example.model.GameState;

import java.io.FileWriter;
import java.io.IOException;

public class NewGameJsonCreator {

    public static void main(String[] args) {
        // Step 1: Create an instance of GameState and populate it with values
        GameState gameState = null;

        // Step 2: Use GameStateTranslator to convert the GameState instance to JSON
        String json = GameStateTranslator.gameStateToJson(gameState);

        // Step 3: Write the JSON string to a file in "src/main/resources"
        try (FileWriter file = new FileWriter("src/main/resources/gameState.json")) {
            file.write(json);
            System.out.println("Successfully Copied JSON Object to File...");
            System.out.println("\nJSON Object: " + json);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
