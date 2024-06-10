package org.example.state;

import org.example.model.AppHandler;
import org.example.model.entities.*;
import org.example.view.handlers.CommandPanelHandler;
import org.example.view.panels.CommandPanel;

/**
 * EntitySelectState handles user interactions when an entity is selected.
 * It allows the user to look closer, pick up items, or talk to NPCs depending on the type of entity.
 * <p>
 * When an entity (such as a CollectableItem, NPC, or ViewableItem) is selected, this state will manage the specific interactions allowed with that entity.
 * </p>
 *
 * @see InteractionState
 */
public class EntitySelectState implements InteractionState {

    private final Entity entity;

    /**
     * Constructor for the EntitySelectState.
     * Initializes the state with the selected entity.
     *
     * @param entity The entity that has been selected.
     */
    public EntitySelectState(Entity entity) {
        this.entity = entity;
    }

    /**
     * Handles the user's input when interacting with the selected entity.
     * It provides specific actions based on the type of entity (e.g., looking closer, picking up items, or talking to NPCs).
     *
     * @param context The context (handler) which currently holds the state.
     * @param input   The user input to be handled.
     */
    @Override
    public void handleInput(CommandPanelHandler context, String input) {
        if (input.equalsIgnoreCase("back")) {
            context.setState(new SurroundingsState());
            return;
        }

        switch (entity) {
            case CollectableItem collectableItem -> {
                switch (input) {
                    case "1":
                        context.getCommandPanel().showSystemMessage(entity.getDescription());
                        context.setState(new InitGameState());
                        break;
                    case "2":
                        AppHandler.getInstance().pickUpItem(entity.getID());
                        context.setState(new InitGameState());
                        break;
                    default:
                        context.getCommandPanel().showSystemMessage("Invalid input. Please choose a valid action.");
                        display(context.getCommandPanel());
                        break;
                }
            }
            case NPC npc -> {
                switch (input) {
                    case "1":
                        context.getCommandPanel().showSystemMessage(entity.getDescription());
                        context.setState(new InitGameState());
                        break;
                    case "2":
                        context.getCommandPanel().showSystemMessage(((NPC) entity).getDialogue());
                        context.setState(new InitGameState());
                        break;
                    default:
                        context.getCommandPanel().showSystemMessage("Invalid input. Please choose a valid action.");
                        display(context.getCommandPanel());
                        context.setState(new InitGameState());
                        break;
                }
            }
            case ViewableItem viewableItem -> {
                if (input.equals("1")) {
                    context.getCommandPanel().showSystemMessage(entity.getDescription());
                    context.setState(new InitGameState());
                } else {
                    context.getCommandPanel().showSystemMessage("Invalid input. Please choose a valid action.");
                    display(context.getCommandPanel());
                }
            }
            default -> {
                context.getCommandPanel().showSystemMessage("Invalid entity type.");
                display(context.getCommandPanel());
            }
        }
    }

    /**
     * Updates the display of the command panel with the options available for the selected entity.
     * The options vary depending on the type of entity (e.g., CollectableItem, NPC, ViewableItem).
     *
     * @param commandPanel The CommandPanel that needs to be updated.
     */
    @Override
    public void display(CommandPanel commandPanel) {
        StringBuilder message = new StringBuilder("You selected: " + entity.getName() + "\n");
        message.append("Choose an action:\n");

        switch (entity) {
            case CollectableItem collectableItem -> {
                message.append("1. Look closer\n");
                message.append("2. Pick up item\n");
            }
            case NPC npc -> {
                message.append("1. Look closer\n");
                message.append("2. Talk to NPC\n");
            }
            case ViewableItem viewableItem -> message.append("1. Look closer\n");
            default -> message.append("Invalid entity type.\n");
        }

        message.append("\nEnter your choice or type 'back' to return\nto the surroundings:\n");

        commandPanel.showSystemMessage(message.toString());
    }
}