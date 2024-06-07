package org.example.model;

import org.example.exceptions.AWSException;
import org.example.exceptions.BackpackWeightExceededException;
import org.example.exceptions.ItemNotFoundException;
import org.example.exceptions.RunOutOfLivesException;
import org.example.model.entities.*;
import org.example.observer.Observable;
import org.example.observer.Observer;
import org.example.util.AWSHandler;
import org.example.util.Event;
import org.example.util.SaveEvent;
import org.example.util.UIEvent;
import org.example.view.handlers.CommandPanelHandler;
import org.example.json_translator.GameStateTranslator;
import org.example.view.panels.GraphicsPanel;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/*
 * This class aims to handle the entire game interactions process by coordinating different
 * game's components.
 * Observable and Singleton design patterns are implemented.
 */

public class AppHandler implements Observable {

    private static AppHandler instance = null;

    private final ArrayList<Observer> observers = new ArrayList<Observer>();
    private final AppState appState;

    private AppHandler() {
        appState = AppState.getInstance();
    }

    public static AppHandler getInstance() {
        if (instance == null) {
            instance = new AppHandler();
        }
        return instance;
    }

    public AppState getAppState() {
        return appState;
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
                    System.err.println(e.getMessage());
                }
            }
        }
    }

    private void notifyUI() {

        for (Observer o : observers) {
            if(o instanceof CommandPanelHandler || o instanceof GraphicsPanel) {
                try{
                    o.update();
                } catch(AWSException e) {   // Exception handled also here because thrown by Observer.update()
                    System.err.println(e.getMessage());
                }
            }
        }
    }

    /*
     * create a new game using a default game template saved in the defaultGameState.json file and then start it if all correctly happened
     */
    public void startNewGame() {
        String fileName = "src/main/resources/json/defaultGameState.json";

        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(fileName));

            String fileContent = "";
            String line = bufferedReader.readLine();

            while (line != null) {
                fileContent += line + "\n";
                line = bufferedReader.readLine(); // Read the next line of the file
            }

            bufferedReader.close();

            appState.setGameState(GameStateTranslator.jsonToGameState(fileContent));

            appState.getLastUserQueryResult().setResult("New Game has started.");
            appState.getLastUserQueryResult().setSuccess(true);

            appState.setWindowToGame(); // turn state to GAME for let the graphics handler know about window status

            System.out.println(appState.getCurrentWindow());

            notifyObservers(new SaveEvent());   // trigger an autosave to the AWS

        } catch (IOException e) {
            appState.getLastUserQueryResult().setResult("Error occurred: Failed to open a file.");
            appState.getLastUserQueryResult().setSuccess(false);

            appState.setWindowToMenu(); // turn state to MENU for let the graphics handler know about window status
        }

        notifyObservers(new UIEvent());
    }

    /*
     * load a saved game from the AWS and then start it if all correctly happened
     */
    public void startSavedGame(int gameID) {
        try{
            String savedGameJson = AWSHandler.getInstance().getSavedGames(gameID);
            appState.setGameState(GameStateTranslator.jsonToGameState(savedGameJson));

            appState.getLastUserQueryResult().setResult("Saved Game loaded.");
            appState.getLastUserQueryResult().setSuccess(true);

            appState.setWindowToGame(); // turn state to GAME for let the graphics handler know about window status

        } catch(AWSException e) {
            System.err.println(e.getMessage());
            appState.getLastUserQueryResult().setResult(e.getMessage());
            appState.getLastUserQueryResult().setSuccess(false);

            appState.setWindowToMenu(); // turn state to MENU for let the graphics handler know about window status
        }

        notifyObservers(new UIEvent());
    }

    /*
     * create a list of all saved games in the AWS showing their titles associated to a number starting from 1
     */
    public int getSavedGames() {

        try {
            appState.getLastUserQueryResult().setResult("Number of saved games: " + AWSHandler.getInstance().countSavedGames());
            appState.getLastUserQueryResult().setSuccess(true);
            return AWSHandler.getInstance().countSavedGames();
        } catch (AWSException e) {
            appState.getLastUserQueryResult().setResult(e.getMessage());
            appState.getLastUserQueryResult().setSuccess(false);
        }

        return -1;
    }

    /*
     * turn the window state from GAME to MENU
     */
    public void exitToMenu() {
        appState.setWindowToMenu();

        appState.getLastUserQueryResult().setResult("Exiting the game.\n\n");
        appState.getLastUserQueryResult().setSuccess(true);

        notifyObservers(new UIEvent());
    }

    /*
     * handle the logic behind pirate's movements between locations
     */
    public void move(int locationID) {
        Location source = appState.getGameState().getMap().getLocationById(appState.getGameState().getMap().getPirateLocationID());
        Location destination = appState.getGameState().getMap().getLocationById(locationID);

        // if any location with that locationID doesn't exist in the Map
        if(destination == null) {
            appState.getLastUserQueryResult().setResult("No location with the selected location ID.");
            appState.getLastUserQueryResult().setSuccess(false);
        }
        // if trying to move on the current location
        else if(source.getID() == destination.getID()) {
            appState.getLastUserQueryResult().setResult("You're already on this location.");
            appState.getLastUserQueryResult().setSuccess(false);
        }
        // if moving on an adjacent location
        else if(source.isAdjacentLocation(destination)) {
            Obstacle obstacle = appState.getGameState().getMap().getObstacleByLocationsID(source.getID(), destination.getID());

            // if trying to move on the treasure island (id = 0), then check if the legendary key (id = 4) is in the backpack
            if(destination.getID() == 0 && !appState.getGameState().getPirate().getBackpack().isItemEquipped(4)) {
                appState.getLastUserQueryResult().setResult("To reach the treasure island you have to collect all 3 keys in order to pass through the portal.");
                appState.getLastUserQueryResult().setSuccess(false);
            }
            // if there are obstacles during transit from source to destination
            else if(obstacle != null) {
                // if pirate has the object to defeat the obstacle in the backpack
                if(appState.getGameState().getPirate().getBackpack().isItemEquipped(obstacle.getItemToDefeatID())) {
                    appState.getGameState().getMap().setPirateLocationID(destination.getID());
                    appState.getGameState().getMap().removeObstacleByLocationsID(source.getID(), destination.getID());  // remove obstacle in the path source-destination

                    appState.getLastUserQueryResult().setResult("Obstacle warning: you won the fight.\nNew location reached.");
                    appState.getLastUserQueryResult().setSuccess(true);

                    notifyObservers(new SaveEvent());
                }
                // losing fight against obstacle
                else {
                    try{
                        appState.getGameState().getPirate().setCurrentLives(appState.getGameState().getPirate().getCurrentLives() - 1);

                        appState.getLastUserQueryResult().setResult("Obstacle warning: you lost the fight.\nYou remain on the same location.");
                        appState.getLastUserQueryResult().setSuccess(false);

                        notifyObservers(new SaveEvent());

                    } catch(RunOutOfLivesException e) {   // exception thrown if the pirate ends its lives
                        gameOver();
                        return;
                    }
                }
            }
            else {
                appState.getGameState().getMap().setPirateLocationID(destination.getID());

                appState.getLastUserQueryResult().setResult("New location reached.");
                appState.getLastUserQueryResult().setSuccess(true);

                notifyObservers(new SaveEvent());
            }

            // if pirate reaches the treasure island (location with id = 0)
            if(appState.getGameState().getMap().getPirateLocationID() == 0) {
                win();
                return;
            }
        }
        // if trying to move on locations not directly reachable from the current one
        else {
            appState.getLastUserQueryResult().setResult("You can't move to that location from the current one.");
            appState.getLastUserQueryResult().setSuccess(false);
        }

        notifyObservers(new UIEvent());
    }

    /*
     * collect the desired item if it is possible
     */
    public void pickUpItem(int itemID) {
        try {
            Location currentLocation = appState.getGameState().getMap().getLocationById(appState.getGameState().getMap().getPirateLocationID());
            CollectableItem itemToPickUp = currentLocation.getCollectableItemByID(itemID);

            // check if the item to pick up requires an item to have in the backpack
            if(itemToPickUp.getRequiredEntityID() == 0 || appState.getGameState().getPirate().getBackpack().isItemEquipped(itemToPickUp.getRequiredEntityID())) {
                // add to the backpack the item to pick up
                appState.getGameState().getPirate().getBackpack().addItem(itemToPickUp);
                currentLocation.removeItem(itemID);

                // check if all 3 keys are in the backpack
                if(checkKeys() == 3) {
                    // merge 3 keys into the unique legendary key, i.e. remove from the backpack the 3 keys and add the legendary one
                    CollectableItem legendaryKey = new CollectableItem(4, "Legendary key", "The legendary key just wants to meet the portal.", 1, 0);

                    appState.getGameState().getPirate().getBackpack().removeItem(1);
                    appState.getGameState().getPirate().getBackpack().removeItem(2);
                    appState.getGameState().getPirate().getBackpack().removeItem(3);

                    appState.getGameState().getPirate().getBackpack().addItem(legendaryKey);

                    appState.getLastUserQueryResult().setResult("All keys found... They are merging together... You now have the legendary key!");
                    appState.getLastUserQueryResult().setSuccess(true);
                }
                else {
                    appState.getLastUserQueryResult().setResult("Item equipped");
                    appState.getLastUserQueryResult().setSuccess(true);
                }
            }
            else {
                appState.getLastUserQueryResult().setResult("Before pick up this item, you need to equip the item " + itemToPickUp.getRequiredEntityID());
                appState.getLastUserQueryResult().setSuccess(false);
            }

        } catch(BackpackWeightExceededException | ItemNotFoundException e) {
            appState.getLastUserQueryResult().setResult(e.getMessage());
            appState.getLastUserQueryResult().setSuccess(false);
        }

        notifyObservers(new UIEvent());
    }

    /*
     * remove the desired item from the pirate's backpack and leave it in the current location
     */
    public void dropItem(int entityID) {
        try {
            // remove from backpack the item
            CollectableItem droppedItem = appState.getGameState().getPirate().getBackpack().removeItem(entityID);

            // adding the dropped item to the current location
            Location currentLocation = appState.getGameState().getMap().getLocationById(appState.getGameState().getMap().getPirateLocationID());
            currentLocation.addCollectableItem(droppedItem);

            appState.getLastUserQueryResult().setResult("Item dropped from the backpack.");
            appState.getLastUserQueryResult().setSuccess(true);
        } catch (ItemNotFoundException e) {
            appState.getLastUserQueryResult().setResult("You can't drop items from the backpack if you aren't carrying them in it.");
            appState.getLastUserQueryResult().setSuccess(false);
        }

        notifyObservers(new UIEvent());
    }

    /*
     * when a pirate dies, then the user loses: game instance saved in the AWS will be deleted
     */
    private void gameOver() {
        try{
            AWSHandler.getInstance().deleteGame(appState.getGameState().getTitle());
        } catch(AWSException e) {
            System.err.println(e.getMessage());
        }
        appState.getLastUserQueryResult().setResult("GameOver: you've died.");
        appState.getLastUserQueryResult().setSuccess(false);

        // Game Over graphics for a few time
        appState.setWindowToGameOver();
        notifyObservers(new UIEvent());
    }

    /*
     * when a pirate reaches the treasure island, then the user wins: game instance saved in the AWS will be deleted
     */
    private void win() {
        try{
            AWSHandler.getInstance().deleteGame(appState.getGameState().getTitle());
        } catch(AWSException e) {
            System.err.println(e.getMessage());
        }
        appState.getLastUserQueryResult().setResult("Congrats! You won!");
        appState.getLastUserQueryResult().setSuccess(true);

        // Game Win graphics for a few time
        appState.setWindowToGameWin();
        notifyObservers(new UIEvent());
    }

    // return the number of currently collected keys in the backpack
    private int checkKeys() {
        ArrayList<CollectableItem> backpackItems = appState.getGameState().getPirate().getBackpack().getItems();

        int keyCounter = 0; // number of total keys
        for (CollectableItem backpackItem : backpackItems) {
            // keys have got following IDs: 1, 2, 3
            if(backpackItem.getID() >= 1 && backpackItem.getID() <= 3)
                keyCounter++;
        }

        return keyCounter;
    }

}
