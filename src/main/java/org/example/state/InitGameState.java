package org.example.state;

import org.example.model.AppHandler;
import org.example.model.GameState;
import org.example.model.entities.Location;
import org.example.view.handlers.CommandPanelHandler;
import org.example.view.panels.CommandPanel;

public class InitGameState implements InteractionState {
    @Override
    public void handleInput(CommandPanelHandler context, String input) {
        switch (input) {
            case "1" -> context.setState(new SurroundingsState());
            case "2" -> context.setState(new MoveState());
            case "3" -> context.setState(new BackpackState());
            case "4" -> AppHandler.getInstance().exitToMenu();
//            case "5" -> {
//                context.getCommandPanel().showSystemMessage(AppHandler.getInstance().getHelp());
//                display(context.getCommandPanel());
//            }
            default -> {
                display(context.getCommandPanel());
                ;
            }
        }
    }

    @Override
    public void display(CommandPanel commandPanel) {
        GameState gameState = AppHandler.getInstance().getAppState().getGameState();
        Location currentLocation = gameState.getMap().getLocationById(gameState.getMap().getPirateLocationID());

        StringBuilder message = new StringBuilder();
        message.append("You are at ").append(currentLocation.getName()).append("\n");
        message.append("What do you want to do?\n");
        message.append("1. View surroundings\n");
        message.append("2. Move to another location\n");
        message.append("3. Show backpack\n");
        message.append("4. Exit to menu\n");
//        message.append("5. Get help\n");

        message.append("Enter your choice:\n");

        commandPanel.showSystemMessage(message.toString());
    }
}