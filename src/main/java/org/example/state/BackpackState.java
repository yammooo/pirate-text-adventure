package org.example.state;

import org.example.exceptions.ItemNotFoundException;
import org.example.model.AppHandler;
import org.example.model.GameState;
import org.example.model.entities.CollectableItem;
import org.example.model.entities.Entity;
import org.example.view.handlers.CommandPanelHandler;
import org.example.view.panels.CommandPanel;

/**
 * The BackpackState class is a concrete implementation of the InteractionState interface.
 * It represents the state where the user is interacting with the pirate's backpack.
 *
 * <p>
 * This state allows the user to view the contents of their backpack, select an item by its ID,
 * and transition to the corresponding item action state or return to the main game state.
 * </p>
 *
 * <p>
 * The BackpackState class plays manages the user's interaction with the backpack,
 * providing a seamless experience for inspecting items and making decisions based on the items carried.
 * </p>
 *
 * @see InteractionState
 */
public class BackpackState implements InteractionState {

    /**
     * Handles the user's input based on the current state (BackpackState).
     *
     * <p>
     * - If the input is "back", the state transitions to InitGameState.
     * - If the input is a valid item ID, the state transitions to ItemActionState for the selected item.
     * - For invalid inputs, the user is prompted to enter a valid ID.
     * </p>
     *
     * @param context The context (handler) which currently holds the state.
     * @param input   The user input to be handled.
     */
    @Override
    public void handleInput(CommandPanelHandler context, String input) {
        if (input.equalsIgnoreCase("back")) {
            context.setState(new InitGameState());
            return;
        }

        Entity entity;
        try {
            int itemId = Integer.parseInt(input);
            entity = AppHandler.getInstance().getAppState().getGameState().getPirate().getBackpack().getItemById(itemId);
            context.setState(new ItemActionState(entity));
        } catch (NumberFormatException e) {
            context.getCommandPanel().showSystemMessage("Invalid input. Please enter a valid ID.");
            display(context.getCommandPanel());
        } catch (ItemNotFoundException e) {
            context.getCommandPanel().showSystemMessage("Invalid ID. Please enter a valid ID.");
            display(context.getCommandPanel());
        }
    }

    /**
     * Updates the display of the command panel based on the current state (BackpackState).
     *
     * <p>
     * Displays the contents of the backpack, showing the ID and name of each item.
     * Prompts the user to enter an item ID for more actions or type 'back' to return to the main menu.
     * </p>
     *
     * @param commandPanel The CommandPanel that needs to be updated.
     */
    @Override
    public void display(CommandPanel commandPanel) {
        GameState gameState = AppHandler.getInstance().getAppState().getGameState();
        StringBuilder message = new StringBuilder("It contains:\n");
        for (CollectableItem item : gameState.getPirate().getBackpack().getItems()) {
            message.append("> ").append(item.getID()).append(": ").append(item.getName()).append("\n");
        }
        message.append("\nEnter the ID of an item to get more actions\nor type 'back' to return to the main menu:\n");
        commandPanel.showSystemMessage(message.toString());
    }
}