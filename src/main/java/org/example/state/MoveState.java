package org.example.state;

import org.example.model.AppHandler;
import org.example.model.GameState;
import org.example.model.entities.Location;
import org.example.view.handlers.CommandPanelHandler;
import org.example.view.panels.CommandPanel;

import java.util.ArrayList;
import java.util.Map;

public class MoveState implements InteractionState {
    @Override
    public void handleInput(CommandPanelHandler context, String input) {

        if (input.equalsIgnoreCase("back")) {
            context.setState(new InitGameState());
            return;
        }

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

        ArrayList<Integer> adjacentLocations = currentLocation.getAdjacentLocations();

        StringBuilder message = new StringBuilder("Where do you want to move?\n");

        for (Integer locationId : adjacentLocations) {
            Location location = gameState.getMap().getLocationById(locationId);
            message.append("> ").append(location.getID()).append(": ").append(location.getName()).append("\n");
        }

        message.append("\nEnter the ID of the location you want to move to\nor type 'back' to return to the main menu:\n");

        commandPanel.showSystemMessage(message.toString());
    }
}