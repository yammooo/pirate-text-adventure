package org.example.state;

import org.example.model.AppHandler;
import org.example.model.GameState;
import org.example.model.entities.Location;
import org.example.view.handlers.CommandPanelHandler;
import org.example.view.panels.CommandPanel;

/**
 * The InitGameState class represents the initial game state where the player starts or continues the game.
 * It implements the InteractionState interface, handling user input and updating the display accordingly.
 *
 * <p>
 * In this state, the player is presented with multiple options including viewing surroundings,
 * moving to a different location, checking the backpack, or exiting to the main menu.
 * This class is crucial for setting up the environment where the player's in-game journey begins and manages transitions to other states based on the player's choices.
 * </p>
 *
 * @see InteractionState
 */
public class InitGameState implements InteractionState {

    /**
     * Handles the user input based on the initial game state.
     * Transitions to different states depending on the user's choice.
     *
     * @param context The context (handler) which currently holds the state.
     * @param input   The user input to be handled.
     */
    @Override
    public void handleInput(CommandPanelHandler context, String input) {
        switch (input) {
            case "1" -> context.setState(new SurroundingsState());
            case "2" -> context.setState(new MoveState());
            case "3" -> context.setState(new BackpackState());
            case "4" -> AppHandler.getInstance().exitToMenu();
            default -> {
                context.getCommandPanel().showSystemMessage("Invalid input. Please enter a valid choice.");
                display(context.getCommandPanel());
            }
        }
    }

    /**
     * Updates the display of the CommandPanel when in the initial game state.
     * Shows the current location and options for the player to take their next action.
     *
     * @param commandPanel The CommandPanel that needs to be updated.
     */
    @Override
    public void display(CommandPanel commandPanel) {
        GameState gameState = AppHandler.getInstance().getAppState().getGameState();
        Location currentLocation = gameState.getMap().getLocationById(gameState.getMap().getPirateLocationID());
        StringBuilder message = new StringBuilder();
        message.append("You are at ").append(currentLocation.getName()).append(":\n").append(currentLocation.getDescription()).append("\n\n");
        message.append("What do you want to do?\n");
        message.append("1. View surroundings\n");
        message.append("2. Move to another location\n");
        message.append("3. Show backpack\n");
        message.append("4. Exit to menu\n");
        message.append("Enter your choice:\n");
        commandPanel.showSystemMessage(message.toString());
    }
}