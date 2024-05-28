package org.example.util;

import org.example.model.GameState;
import com.google.gson.Gson;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class GameStateTranslator {

    private Gson gson;

    public void SaveLoadManager() {
        this.gson = new Gson();
    }


    // STRANA VERSIONE BREVE SENZA TRY CATCH CHE DA ERRORI

    /*
    public static GameState jsonToGameState(String json) {
        return gson.fromJson(json, GameState.class);
    }
    public static String gameStateToJson(GameState gameState) {
        return gson.toJson(gameState);
    }
    */

    //// VERSIONE LUNGA CON TRY CATCH



    //JSON ----->>>>> GAMESTATE

    public String fileFromPath(String filePath) {
        try {
            return new String(Files.readAllBytes(Paths.get(filePath)));
        } catch (IOException e) {

            throw new IllegalArgumentException("lettura file fallita");
        }
    }

    public GameState loadGameStateFromFile(String filePath) {
        String json = fileFromPath(filePath);
        if (json != null) {
            return recreateGameState(json);
        } else {
            throw new IllegalArgumentException("creazione json fallita");
        }
    }

    public GameState recreateGameState(String json) {
        return gson.fromJson(json, GameState.class);
    }

    //GAMESTATE ----->>>> JSON

    public String saveGameState(GameState gameState) {
        return gson.toJson(gameState);
    }

    public void saveGameStateToFile(GameState gameState, String filePath) {
        String json = saveGameState(gameState);
        if (json == null) {
            throw new IllegalArgumentException("creazione json fallita");
        }
        try (FileWriter file = new FileWriter(filePath)) {
            file.write(json);
        } catch (IOException e) {
            throw new IllegalArgumentException("salvataggio gamestate fallito");
        }
    }


}