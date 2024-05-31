package org.example.model;

import org.example.exceptions.BackpackWeightExceededException;
import org.example.exceptions.InvalidRequestException;
import org.example.exceptions.ItemNotFoundException;
import org.example.model.entities.CollectableItem;
import org.example.model.entities.Entity;
import org.example.model.entities.Location;
import org.example.model.entities.NPC;
import org.example.observer.Observable;
import org.example.observer.Observer;
import org.example.util.AWSHandler;
import org.example.util.Event;
import org.example.util.SaveEvent;
import org.example.util.UIEvent;
import org.example.view.handlers.CommandPanelHandler;
import org.example.view.handlers.GraphicsPanelHandler;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/*
 * This class aims to handle the entire game loop by coordinating different
 * game's components.
 * Observable and Singleton design patterns are implemented.
 */

public class AppHandler implements Observable {

    private static AppHandler instance = null;

    private ArrayList<Observer> observers = new ArrayList<Observer>();
    private AppState appState;

    private AppHandler() {
        appState = AppState.getInstance();
    }

    public static AppHandler getInstance() {
        if (instance == null) {
            instance = new AppHandler();
        }
        return instance;
    }

    @Override
    public void addObserver(Observer o) {
        observers.add(o);
    }

    @Override
    public void removeObserver(Observer o) {
        observers.remove(o);
    }

    @Override
    public void notifyObservers() {
        for (Observer o : observers) {
            o.update();
        }
    }

    @Override
    public void notifyObservers(Event e) {
        if(e instanceof UIEvent) {
            notifyUI();
        }
        else if(e instanceof SaveEvent) {
            notifySave();
        }
    }

    private void notifySave() {
        for (Observer o : observers) {
            if(o instanceof AWSHandler) {
                try{
                    o.update();
                } catch(AWSException e) {
                    /*
                    modificare lastUserQuery oppure no? --> si entra in questo scope solo per fare gli autosaves: poichè sono operazioni indpendenti dall'interazione utente-gioco, non ha poi così senso segnalaro in output all'utente e tanto meno vanno notificati il commandPanel/graphicsPanel

                    appState.getLastUserQueryResult().setResult(e.getMessage());
                    appState.getLastUserQueryResult().setSuccess(false);
                     */
                    System.err.println(e.getMessage());
                }
            }
        }
    }

    private void notifyUI() {

        for (Observer o : observers) {
            if(o instanceof CommandPanelHandler || o instanceof GraphicsPanelHandler) {
                try{
                    o.update();
                } catch(AWSException e) {
                    /*
                    modificare lastUserQuery oppure no? --> si entra in questo scope solo per fare gli autosaves: poichè sono operazioni indpendenti dall'interazione utente-gioco, non ha poi così senso segnalaro in output all'utente e tanto meno vanno notificati il commandPanel/graphicsPanel

                    appState.getLastUserQueryResult().setResult(e.getMessage());
                    appState.getLastUserQueryResult().setSuccess(false);
                     */
                    System.err.println(e.getMessage());
                }
            }
        }
    }

    public void startNewGame() {
        String fileName = "src/main/resources/assets.json/defaultGameState.json";

        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(fileName));

            String fileContent = "";
            String line = bufferedReader.readLine();

            while (line != null) {
                fileContent += line + "\n";
                line = bufferedReader.readLine(); // Read the next line of the file
            }

            bufferedReader.close();

            appState.setGameState(GamesStateTranslator.jsonToGameState(fileContent));

            appState.getLastUserQueryResult().setResult("New Game started.");
            appState.getLastUserQueryResult().setSuccess(true);

            appState.setWindowToGame();

            notifyObservers(new SaveEvent());   // autosave when a new game starts

        } catch (IOException e) {
            appState.getLastUserQueryResult().setResult("Error occurred: Failed to open a file.");
            appState.getLastUserQueryResult().setSuccess(false);

            appState.setWindowToMenu();
        }

        notifyObservers(new UIEvent());
    }

    public void startSavedGame(int gameID) {
        // TODO: comunicare con AWSHandler (metodi statici)
        // (capire se è un controllo da gestire qui o nel panelHandler) controllare che il numero sia valido (tra 1 e n dove n è il numero di istanze sull'AWS) --> sono controlli gesititi sull'AWS
        // settare ID del game in GameState a gameID
        // TODO: gestire eccezione e modificare lastUserQuery
        appState.setWindowToGame(); // change only if correctly received the json from the AWS
    }

    public void getSavedGames() {
        try{
            ArrayList<String> gamesAvailable = AWSHandler.getGameTitles();

            String message = "";
            int counter = 0;
            for (String title : gamesAvailable) {
                message += counter + ". " + title + "\n";
                counter++;
            }

            appState.getLastUserQueryResult().setResult(message);
            appState.getLastUserQueryResult().setSuccess(true);

        } catch(AWSException e) {
            appState.getLastUserQueryResult().setResult(e.getMessage());
            appState.getLastUserQueryResult().setSuccess(false);
        }

        notifyObservers(new UIEvent());
    }

    public void exitToMenu() {
        appState.setWindowToMenu();
        appState.getLastUserQueryResult().setResult("Exiting the game.");
        appState.getLastUserQueryResult().setSuccess(true);
        notifyObservers(new UIEvent());
    }

    public void move(int locationID) {
        Location source = appState.getGameState().getMap().getLocationById(appState.getGameState().getMap().getPirateLocationID());
        Location destination = appState.getGameState().getMap().getLocationById(locationID);

        // if trying to move on the current location
        if(source.getID() == destination.getID()) {
            appState.getLastUserQueryResult().setResult("You're already on this location.");
            appState.getLastUserQueryResult().setSuccess(false);
        }
        // if moving on an adjacent location
        else if(source.isAdjacentLocation(destination)) {
            // TODO:
            // if obstacles
                // if pirate sconfigge obstacle (controllare nel backpack se c'è weapon)
                    // muovere pirate to new location
                    // ? rimuovere obstacle tra le due location ?
                    appState.getLastUserQueryResult().setResult("Obstacle warning: you won the fight.\nNew location reached.");
                    appState.getLastUserQueryResult().setSuccess(true);
                    // triggerare l'autosave
                // else (non sconfigge)
                    try{
                        // pirate perde vita
                        appState.getLastUserQueryResult().setResult("Obstacle warning: you lost the fight.\nYou remain on the same location.");
                        appState.getLastUserQueryResult().setSuccess(false);
                        // triggerare l'autosave
                    } catch(IllegalArgumentException e) {
                        GameOver();
                    }
            // else (no obstacles)
                // muovere pirate to new location
                appState.getLastUserQueryResult().setResult("New location reached.");
                appState.getLastUserQueryResult().setSuccess(true);
                // triggerare l'autosave


            // if pirateCurrentLocationID is (id della stanza viola)
                // se nel backpck ci sono le chiavi (servono i 3 ID delle chiavi)
                    // muovere user automaticamente nella treasure island
                    Win();
                // se non ha tutte e 3 le chiavi non serve fare nulla

        }
        // if trying to move on locations not directly reachable from the current one
        else {
            appState.getLastUserQueryResult().setResult("You can't move to that location from the current one.");
            appState.getLastUserQueryResult().setSuccess(false);
        }

        notifyObservers(new UIEvent());
    }

    public void pickUpItem(int entityID) {
        // TODO: serve la modifica del metodo in Backpack.java
        // TODO: appState.getGameState().getPirate().getBackpack().addItem(entityID);
        // TODO: prima di raccoglire l'oggetto, controllare che si abbia il requestedItemID nel backpack

        try {
            // ...

            appState.getLastUserQueryResult().setResult("Item equipped in the backpack.");
            appState.getLastUserQueryResult().setSuccess(true);

        } catch(BackpackWeightExceededException e) {
            appState.getLastUserQueryResult().setResult("Item too heavy. Drop something from the backpack before pick up this object.");
            appState.getLastUserQueryResult().setSuccess(false);
        }

        notifyObservers(new UIEvent());
    }

    public void dropItem(int entityID) {
        try {
            appState.getGameState().getPirate().getBackpack().removeItem(entityID);

            appState.getLastUserQueryResult().setResult("Item dropped from the backpack.");
            appState.getLastUserQueryResult().setSuccess(true);
        } catch (ItemNotFoundException e) {
            appState.getLastUserQueryResult().setResult("You can't drop items from the backpack if you aren't carrying them in it.");
            appState.getLastUserQueryResult().setSuccess(false);
        }

        notifyObservers(new UIEvent());
    }

    public void getHelp() {

        // TODO: edit help.txt

        String fileName = "src/main/resources/assets.help/help.txt";

        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(fileName));

            String fileContent = "";
            String line = bufferedReader.readLine();

            while (line != null) {
                fileContent += line + "\n";
                line = bufferedReader.readLine(); // Read the next line of the file
            }

            bufferedReader.close();

            appState.getLastUserQueryResult().setResult(fileContent);
            appState.getLastUserQueryResult().setSuccess(true);

        } catch (IOException e) {
            appState.getLastUserQueryResult().setResult("Error occurred: Failed to open a file.");
            appState.getLastUserQueryResult().setSuccess(false);
        }

        notifyObservers(new UIEvent());
    }

    public void getDialogue(int entityID) {
        int currentPirateLocation = appState.getGameState().getMap().getPirateLocationID();
        List<NPC> npcs = appState.getGameState().getMap().getLocationById(currentPirateLocation).getNpcs();

        boolean isValidRequest = false;

        for (NPC npc : npcs) {
            if(npc.getID() == entityID) {
                appState.getLastUserQueryResult().setResult(npc.getDialogue());
                appState.getLastUserQueryResult().setSuccess(true);
                isValidRequest = true;
            }
        }

        if(!isValidRequest) {
            appState.getLastUserQueryResult().setResult("Invalid ID: You can only interact with entities in the current location.");
            appState.getLastUserQueryResult().setSuccess(false);
        }

        notifyObservers(new UIEvent());
    }

    public void viewEntity(int entityID) {
        int currentPirateLocation = appState.getGameState().getMap().getPirateLocationID();
        // TODO: serve un metodo in Location che ritorni tutti gli items/entities nella location, cosìcche poi posso cercare se c'è entityID
        // TODO: nella location corrente e chiamarne il getDescription()

        boolean isValidRequest = false;

        for (Entity e : entities) {
            if(e.getID() == entityID) {
                appState.getLastUserQueryResult().setResult(e.getDescription());
                appState.getLastUserQueryResult().setSuccess(true);
                isValidRequest = true;
            }
        }

        if(!isValidRequest) {
            appState.getLastUserQueryResult().setResult("Invalid ID: You can only interact with entities in the current location.");
            appState.getLastUserQueryResult().setSuccess(false);
        }

        notifyObservers(new UIEvent());
    }

    private void GameOver() {
        // TODO: eliminare istanza di gioco da AWS; aggiornare lastUserQuery ("GameOver: you've died."); set to menu del windowState (in base a cosa mi dice Gian)
        // non server il notify() perchè questo metodo viene chiamato dentro il metodo move che ha già il notify() alla fine
    }

    private void Win() {
        // TODO: eliminare istanza di gioco da AWS; aggiornare lastUserQuery ("You won!"); set to menu del windowState (in base a cosa mi dice Gian)
        // non server il notify() perchè questo metodo viene chiamato dentro il metodo move che ha già il notify() alla fine
    }

    public AppState getAppState() {
        return appState;
    }

}
