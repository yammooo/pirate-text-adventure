package org.example.state;

import org.example.model.AppHandler;
import org.example.model.GameState;
import org.example.model.entities.Location;
import org.example.view.handlers.CommandPanelHandler;
import org.example.view.panels.CommandPanel;

import java.util.ArrayList;

/**
 * The MoveState class is a concrete implementation of the InteractionState interface.
 * It handles user inputs related to moving to different locations in the game and updates the display to show adjacent locations.
 * <p>
 * This class plays a crucial role in the game's navigation mechanics, allowing the user to explore different locations and progress through the game.
 * It ensures that the user can only enter valid location IDs and facilitates the transition between different states.
 * </p>
 *
 * @see InteractionState
 */
public class MoveState implements InteractionState {

    /**
     * Handles the user's input for moving to a new location.
     * If the input is "back", it sets the state to InitGameState to return to the main interaction state.
     * If the input is a valid location ID, it moves the player to the specified location.
     * Otherwise, it displays an error message and prompts the user again.
     *
     * @param context The context (handler) that holds the current state.
     * @param input   The user's input.
     */
    @Override
    public void handleInput(CommandPanelHandler context, String input) {
        if (input.equalsIgnoreCase("back")) {
            context.setState(new InitGameState());
            return;
        }
        try {
            int locationId = Integer.parseInt(input);
            AppHandler.getInstance().move(locationId);
        } catch (NumberFormatException e) {
            context.getCommandPanel().showSystemMessage("Invalid input. Please enter a valid location ID.");
            display(context.getCommandPanel());
        }
    }

    /**
     * Displays the adjacent locations where the user can move.
     * It fetches the current location of the pirate and lists all adjacent locations, prompting the user to choose one.
     *
     * @param commandPanel The CommandPanel that needs to be updated.
     */
    @Override
    public void display(CommandPanel commandPanel) {
        GameState gameState = AppHandler.getInstance().getAppState().getGameState();
        Location currentLocation = gameState.getMap().getLocationById(gameState.getMap().getPirateLocationID());
        ArrayList<Integer> adjacentLocations = currentLocation.getAdjacentLocations();

        StringBuilder message = new StringBuilder("Where do you want to move?\n");
        for (Integer locationId : adjacentLocations) {
            Location location = gameState.getMap().getLocationById(locationId);
            message.append("> ").append(location.getID()).append(": ").append(location.getName()).append("\n");
        }
        message.append("\nEnter the ID of the location you want to move to\nor type 'back' to return to the main menu:\n");
        commandPanel.showSystemMessage(message.toString());
    }
}