package org.example.state;

import org.example.exceptions.ItemNotFoundException;
import org.example.model.AppHandler;
import org.example.model.GameState;
import org.example.model.entities.*;
import org.example.view.handlers.CommandPanelHandler;
import org.example.view.panels.CommandPanel;

/**
 * The SurroundingsState class represents the state where the player is looking around in their current location.
 * It handles the display of the surroundings, including collectable items, viewable items, and NPCs,
 * and manages user input to interact with those entities.
 *
 * <p>
 * It provides the player with a detailed view of their surroundings and allows them to select entities
 * for further actions or return to a previous state. This class is vital for ensuring the player can make
 * informed decisions based on their current environment.
 * </p>
 *
 * @see InteractionState
 */
public class SurroundingsState implements InteractionState {

    /**
     * Handles the user's input based on the current state.
     * If the input is "back", it transitions to the InitGameState.
     * If the input is a valid entity ID, it transitions to the EntitySelectState.
     * Otherwise, it shows an error message for invalid input.
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
            Map map = AppHandler.getInstance().getAppState().getGameState().getMap();
            int entityId = Integer.parseInt(input);
            entity = map.getLocationById(map.getPirateLocationID()).getEntityById(entityId);
            context.setState(new EntitySelectState(entity));
        } catch (NumberFormatException e) {
            context.getCommandPanel().showSystemMessage("Invalid input. Please enter a valid ID.");
            display(context.getCommandPanel());
        } catch (ItemNotFoundException e) {
            context.getCommandPanel().showSystemMessage("Invalid ID. Please enter a valid ID.");
            display(context.getCommandPanel());
        }
    }

    /**
     * Updates the display of the command panel based on the current state.
     * It shows the details of the current location, including collectable items, viewable items, and NPCs.
     * If no entities are present, it shows a message indicating that there is nothing to see.
     *
     * @param commandPanel The CommandPanel that needs to be updated.
     */
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