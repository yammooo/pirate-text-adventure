package org.example.state;

import org.example.model.AppHandler;
import org.example.model.entities.*;
import org.example.view.handlers.CommandPanelHandler;
import org.example.view.panels.CommandPanel;

public class EntitySelectState implements InteractionState {
    private final Entity entity;

    public EntitySelectState(Entity entity) {
        this.entity = entity;
    }

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
                        context.getCommandPanel().showSystemMessage(((NPC)entity).getDialogue());
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
            }
        }
    }

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
            default -> {
            }
        }

        message.append("\nEnter your choice or type 'back' to return\nto the surroundings:\n");

        commandPanel.showSystemMessage(message.toString());
    }
}