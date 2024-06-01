package org.example.state;

import org.example.model.AppHandler;
import org.example.model.entities.Entity;
import org.example.view.handlers.CommandPanelHandler;
import org.example.view.panels.CommandPanel;

public class SelectBackpackItemState implements UserInteractionState {
    private final Entity entity;

    public SelectBackpackItemState(Entity entity) {
        this.entity = entity;
    }

    @Override
    public void handleInput(CommandPanelHandler context, String input) {
        switch (input) {
            case "1":
                context.getCommandPanel().showSystemMessage(entity.getDescription());
                context.setState(new ShowLocationState());
                break;
            case "2":
                AppHandler.getInstance().dropItem(entity.getID());
                context.setState(new ShowLocationState());
                break;
            default:
                context.getCommandPanel().showSystemMessage("Invalid input. Please choose a valid action.");
                display(context.getCommandPanel());
                break;
        }
    }

    @Override
    public void display(CommandPanel commandPanel) {
        StringBuilder message = new StringBuilder("You have selected an item from your backpack.\n");

        message.append("1. Look closer\n");
        message.append("2. Pick up item\n");

        message.append("Enter your choice:\n");

        commandPanel.showSystemMessage(message.toString());
    }
}