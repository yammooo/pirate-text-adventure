package org.example.model;

import org.example.observer.Observable;
import org.example.observer.Observer;
import org.example.util.AWSHandler;
import org.example.util.Event;
import org.example.util.SaveEvent;
import org.example.util.UIEvent;
import org.example.view.handlers.CommandPanelHandler;
import org.example.view.handlers.GraphicsPanelHandler;

import java.util.ArrayList;

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
    public void notifyObservers(Event e) {
        if(e instanceof UIEvent) {
            notifyUI(e);
        }
        else if(e instanceof SaveEvent) {
            notifySave(e);
        }
    }

    private void notifySave(Event e) {
        for (Observer o : observers) {
            if(o instanceof AWSHandler) {
                o.update();
            }
        }
    }

    private void notifyUI(Event e) {
        for (Observer o : observers) {
            if(o instanceof CommandPanelHandler || o instanceof GraphicsPanelHandler) {
                o.update();
            }
        }
    }

    public void startNewGame() {
        // TODO: Implement the logic to start a new game
    }

    public void startSavedGame(int gameID) {
        // TODO: Implement the logic to start a saved game
    }

    public int getSavedGames() {
        // TODO: Implement the logic to get saved games
        return 0;
    }

    public void exitToMenu() {
        //TODO
    }

    public void move(int locationID) {
        //TODO
    }

    public void pickUpItem(int entityID) {
        //TODO
    }

    public void dropItem(int entityID) {
        //TODO
    }

    public String getHelp() {
        //TODO
        return null;
    }

    public String getDialogue(int entityID) {
        //TODO
        return null;
    }

    public String viewEntity(int entityID) {
        //TODO
        return null;
    }

    public AppState getAppState() {
        return appState;
    }
}
