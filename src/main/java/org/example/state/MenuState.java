package org.example.state;

import org.example.model.AppHandler;
import org.example.view.handlers.CommandPanelHandler;
import org.example.view.panels.CommandPanel;

public class MenuState implements InteractionState {
    @Override
    public void handleInput(CommandPanelHandler context, String input) {
        switch (input) {
            case "1":
                AppHandler.getInstance().startNewGame();
                //context.setState(new MainMenuState());

                // will not  change state to ShowLocationState, so in case new game fails to load
                // the user will be able to choose another option from the main menu

                break;
            case "2":
                context.setState(new LoadGameState());
                break;
            default:
                context.getCommandPanel().showSystemMessage("Invalid input. Please choose a valid option.");
                display(context.getCommandPanel());
                break;
        }
    }

    @Override
    public void display(CommandPanel commandPanel) {
        StringBuilder message = new StringBuilder("Main Menu:\n");
        message.append("1. Start New Game\n");
        message.append("2. Load Saved Game\n");
        message.append("Enter your choice:\n");
        commandPanel.showSystemMessage(message.toString());
    }
}
