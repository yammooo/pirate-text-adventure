package org.example.state;

import org.example.model.AppHandler;
import org.example.model.GameState;
import org.example.model.entities.Location;
import org.example.view.handlers.CommandPanelHandler;
import org.example.view.panels.CommandPanel;

import java.util.Map;

public class MoveState implements UserInteractionState {
    @Override
    public void handleInput(CommandPanelHandler context, String input) {
        try {
            int locationId = Integer.parseInt(input);
            AppHandler.getInstance().move(locationId);
        } catch (NumberFormatException e) {
            context.getCommandPanel().showSystemMessage("Invalid input. Please enter a valid location ID.");
            display(context.getCommandPanel());
        }
    }

    @Override
    public void display(CommandPanel commandPanel) {
        GameState gameState = AppHandler.getInstance().getAppState().getGameState();
        Location currentLocation = gameState.getMap().getLocationById(gameState.getMap().getPirateLocationID());

        Map<String, Location> adjacentLocations = currentLocation.getAdjacentLocations();

        StringBuilder message = new StringBuilder("Where do you want to move?\n");
        for (Location location : adjacentLocations.values()) {
            message.append("- ").append(location.getName()).append(": ").append(location.getID()).append("\n");
        }

        message.append("\nEnter the ID of the location you want to move to:\n");

        commandPanel.showSystemMessage(message.toString());
    }
}