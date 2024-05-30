package org.example.model;

import org.example.exceptions.BackpackWeightExceededException;
import org.example.exceptions.InvalidRequestException;
import org.example.exceptions.ItemNotFoundException;
import org.example.model.entities.CollectableItem;
import org.example.model.entities.Entity;
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
                o.update(); // TODO: gestire eccezione e modificare lastUserQuery
            }
        }
    }

    private void notifyUI() {
        for (Observer o : observers) {
            if(o instanceof CommandPanelHandler || o instanceof GraphicsPanelHandler) {
                o.update();
            }
        }
    }

    public void startNewGame() {
        // TODO: caricare il gamestate di default usando il file json "template"
        // fare un getCount dall'AWS per sapere ID del game corrente ??? (salvataggio solo quando cambia stanza)
        appState.setWindowToGame();
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

            // TODO: chiedere all'AWSHandler l'elenco delle istanze salvate e creare l'output da stampare sul panel all'utente --> getGameTitles()

            appState.getLastUserQueryResult().setResult(renderizedOutput);
            appState.getLastUserQueryResult().setSuccess(true);

        } catch(eccezione_generata_dall_AWS e) {
            appState.getLastUserQueryResult().setResult("Error occurred: Failed to communicate with AWS.");
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
        // TODO: gestire interazione con Obstacles
        appState.getGameState().getMap().setPirateLocationID(locationID);
        notifyObservers();  // notify also Save only if room has changed
    }

    public void pickUpItem(int entityID) {
        // TODO: serve la modifica del metodo in Backpack.java
        // TODO: appState.getGameState().getPirate().getBackpack().addItem(entityID);
        // TODO: prima di raccoglire l'oggetto ì, controllare che si abbia il requestedItemID nel backpack

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

    /*
    public void useItem(int entityID) {
        // TODO: cosa fa sto metodo??? --> NIENTE
        notifyObservers(new UIEvent());
    }
    */

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

    public AppState getAppState() {
        return appState;
    }

}
