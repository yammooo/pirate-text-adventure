package org.example.state;

import org.example.model.AppHandler;
import org.example.model.entities.*;
import org.example.view.handlers.CommandPanelHandler;
import org.example.view.panels.CommandPanel;

public class SelectEntityState implements UserInteractionState {
    private final Entity entity;

    public SelectEntityState(Entity entity) {
        this.entity = entity;
    }

    @Override
    public void handleInput(CommandPanelHandler context, String input) {

        switch (entity) {
            case CollectableItem collectableItem -> {
                switch (input) {
                    case "1":
                        context.getCommandPanel().showSystemMessage(entity.getDescription());
                        context.setState(new ShowLocationState());
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
                        context.setState(new ShowLocationState());
                        break;
                    case "2":
                        context.getCommandPanel().showSystemMessage(((NPC)entity).getDialogue());
                        context.setState(new ShowLocationState());
                        break;
                    default:
                        context.getCommandPanel().showSystemMessage("Invalid input. Please choose a valid action.");
                        display(context.getCommandPanel());
                        context.setState(new ShowLocationState());
                        break;
                }
            }
            case ViewableItem viewableItem -> {
                if (input.equals("1")) {
                    context.getCommandPanel().showSystemMessage(entity.getDescription());
                    context.setState(new ShowLocationState());
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

        message.append("Enter your choice:\n");

        commandPanel.showSystemMessage(message.toString());
    }
}
