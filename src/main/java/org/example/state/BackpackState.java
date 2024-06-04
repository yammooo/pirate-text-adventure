package org.example.state;

import org.example.exceptions.ItemNotFoundException;
import org.example.model.AppHandler;
import org.example.model.GameState;
import org.example.model.entities.CollectableItem;
import org.example.model.entities.Entity;
import org.example.view.handlers.CommandPanelHandler;
import org.example.view.panels.CommandPanel;

public class BackpackState implements InteractionState {
    @Override
    public void handleInput(CommandPanelHandler context, String input) {

        if (input.equalsIgnoreCase("back")) {
            context.setState(new InitGameState());
            return;
        }

        Entity entity = null;

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

    @Override
    public void display(CommandPanel commandPanel) {
        GameState gameState = AppHandler.getInstance().getAppState().getGameState();
        StringBuilder message = new StringBuilder("Your backpack currently weights: " + gameState.getPirate().getBackpack().getTotalWeight() + "/" + gameState.getPirate().getBackpack().getMaxWeight() + "\n");

        message.append("It contains:\n");

        for (CollectableItem item : gameState.getPirate().getBackpack().getItems()) {
            message.append("> ").append(item.getID()).append(": ").append(item.getName()).append("\n");
        }

        message.append("\nEnter the ID of an item to get more actions\nor type 'back' to return to the main menu:\n");

        commandPanel.showSystemMessage(message.toString());
    }
}
