package org.example.state;

import org.example.model.AppHandler;
import org.example.view.handlers.CommandPanelHandler;
import org.example.view.panels.CommandPanel;

public class LoadGameState implements InteractionState {
    @Override
    public void handleInput(CommandPanelHandler context, String input) {

        if (input.equalsIgnoreCase("back")) {
            context.setState(new MenuState());
            return;
        }

        try {
            int saveId = Integer.parseInt(input);

            if (saveId < 1 || saveId > AppHandler.getInstance().getSavedGames()) {
                context.getCommandPanel().showSystemMessage("Invalid input. Please enter a valid save ID.");
                display(context.getCommandPanel());
                return;
            }

            AppHandler.getInstance().startSavedGame(saveId);
            //context.setState(new MainMenuState());

            // will not  change state to ShowLocationState, so in case new game fails to load
            // the user will be able to choose another option from the main menu

        } catch (NumberFormatException e) {
            context.getCommandPanel().showSystemMessage("Invalid input. Please enter a valid save ID.");
            display(context.getCommandPanel());
        }
    }

    @Override
    public void display(CommandPanel commandPanel) {
        StringBuilder message = new StringBuilder("You have " + AppHandler.getInstance().getSavedGames() + " saved games.\n");

        message.append("Enter the saved game ID to load or type 'back'\nto return to the menu:\n");
        commandPanel.showSystemMessage(message.toString());
    }
}
