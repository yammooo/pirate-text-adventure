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

        Entity entity = null;

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

        StringBuilder message = new StringBuilder("You look around and you see:\n");

        // Print collectable items
        message.append("Collectable Items:\n");
        for (CollectableItem item : currentLocation.getCollectableItems()) {
            message.append("> ").append(item.getName()).append(": ").append(item.getID()).append("\n");
        }

        // Print viewable items
        message.append("Viewable Items:\n");
        for (ViewableItem item : currentLocation.getViewableItems()) {
            message.append("> ").append(item.getName()).append(": ").append(item.getID()).append("\n");
        }

        // Print NPCs
        message.append("NPCs:\n");
        for (NPC npc : currentLocation.getNPC()) {
            message.append("> ").append(npc.getName()).append(": ").append(npc.getID()).append("\n");
        }

        message.append("\nEnter the ID of an item to get more actions:\n");

        commandPanel.showSystemMessage(message.toString());
    }
}