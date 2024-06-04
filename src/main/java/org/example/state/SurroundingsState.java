package org.example.state;

import org.example.exceptions.ItemNotFoundException;
import org.example.model.AppHandler;
import org.example.model.GameState;
import org.example.model.entities.*;
import org.example.view.handlers.CommandPanelHandler;
import org.example.view.panels.CommandPanel;


public class SurroundingsState implements InteractionState {
    @Override
    public void handleInput(CommandPanelHandler context, String input) {

        if (input.equalsIgnoreCase("back")) {
            context.setState(new InitGameState());
            return;
        }

        Entity entity;

        try {

            int entityId = Integer.parseInt(input);
            entity = AppHandler.getInstance().getAppState().getGameState().getMap().getLocationById(AppHandler.getInstance().getAppState().getGameState().getMap().getPirateLocationID()).getEntityById(entityId);
            context.setState(new EntitySelectState(entity));

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
        Location currentLocation = gameState.getMap().getLocationById(gameState.getMap().getPirateLocationID());

        StringBuilder message = new StringBuilder();

        if (currentLocation.getCollectableItems().isEmpty() && currentLocation.getViewableItems().isEmpty() && currentLocation.getNPC().isEmpty()) {
            message.append("Nothing to see here.\n");
        } else {

            message.append("You look around and you see:\n");

            // Print collectable items
            if (!currentLocation.getCollectableItems().isEmpty()) {
                message.append("Collectable Items:\n");
                for (CollectableItem item : currentLocation.getCollectableItems()) {
                    message.append("> ").append(item.getID()).append(": ").append(item.getName()).append("\n");
                }
            }

            // Print viewable items
            if (!currentLocation.getViewableItems().isEmpty()) {
                message.append("Viewable Items:\n");
                for (ViewableItem item : currentLocation.getViewableItems()) {
                    message.append("> ").append(item.getID()).append(": ").append(item.getName()).append("\n");
                }
            }

            // Print NPCs
            if (!currentLocation.getNPC().isEmpty()){
                message.append("NPCs:\n");
                for (NPC npc : currentLocation.getNPC()) {
                    message.append("> ").append(npc.getID()).append(": ").append(npc.getName()).append("\n");
                }
            }

            message.append("\nEnter the ID of an item to get more actions\nor type 'back' to return to the main menu:\n");
        }

        commandPanel.showSystemMessage(message.toString());
    }
}