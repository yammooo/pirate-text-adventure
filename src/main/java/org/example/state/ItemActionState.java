package org.example.state;

import org.example.model.AppHandler;
import org.example.model.entities.Entity;
import org.example.view.handlers.CommandPanelHandler;
import org.example.view.panels.CommandPanel;

/**
 * The ItemActionState class is a concrete implementation of the InteractionState interface.
 * It handles the state of the application when the user selects an item from their backpack.
 *
 * <p>
 * This class is responsible for managing user interactions specific to an item from
 * the backpack, such as looking closer at the item or dropping it. It transitions
 * back to the BackpackState when the user chooses to go back. It ensures the pirate's
 * interactions with items are handled appropriately and feedback is given.
 * </p>
 *
 * @see InteractionState
 */

public class ItemActionState implements InteractionState {

    private final Entity entity;

    /**
     * Constructs a new ItemActionState with the given entity.
     *
     * @param entity The entity representing the item to be interacted with.
     */
    public ItemActionState(Entity entity) {
        this.entity = entity;
    }

    /**
     * Handles the user's input when they are in the item action state.
     * It allows the user to look closer at the item, drop the item, or go back to the backpack.
     *
     * @param context The context (handler) which currently holds this state.
     * @param input   The user input to be handled.
     */
    @Override
    public void handleInput(CommandPanelHandler context, String input) {
        if (input.equalsIgnoreCase("back")) {
            context.setState(new BackpackState());
            return;
        }
        switch (input) {
            case "1":
                context.getCommandPanel().showSystemMessage(entity.getDescription());
                context.setState(new InitGameState());
                break;
            case "2":
                AppHandler.getInstance().dropItem(entity.getID());
                break;
            default:
                context.getCommandPanel().showSystemMessage("Invalid input. Please choose a valid action.");
                display(context.getCommandPanel());
                break;
        }
    }

    /**
     * Displays the options available to the user when they have selected an item from their backpack.
     * It provides options to look closer at the item, drop the item, or return to the backpack.
     *
     * @param commandPanel The CommandPanel to be updated with the display message.
     */
    @Override
    public void display(CommandPanel commandPanel) {
        StringBuilder message = new StringBuilder("You have selected an item from your backpack.\n");
        message.append("1. Look closer\n");
        message.append("2. Drop Item\n");
        message.append("\nEnter your choice or type 'back' to return to\nthe backpack:\n");
        commandPanel.showSystemMessage(message.toString());
    }
}
