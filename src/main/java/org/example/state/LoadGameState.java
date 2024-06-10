package org.example.state;

import org.example.model.AppHandler;
import org.example.view.handlers.CommandPanelHandler;
import org.example.view.panels.CommandPanel;

/**
 * The LoadGameState class implements the InteractionState interface.
 * It defines the behavior for the state where the user can load a previously saved game.
 *
 * <p>
 * This class is responsible for handling user inputs when the game is in the load state,
 * and updating the display to guide the user on how to load a saved game or return to the main menu.
 * </p>
 *
 * @see InteractionState
 */
public class LoadGameState implements InteractionState {

    /**
     * Handles the user's input when in the load game state.
     * This method processes user commands to load a saved game or return to the main menu.
     *
     * @param context The context (handler) which currently holds the state.
     * @param input   The user input to be handled.
     */
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
        } catch (NumberFormatException e) {
            context.getCommandPanel().showSystemMessage("Invalid input. Please enter a valid save ID.");
            display(context.getCommandPanel());
        }
    }

    /**
     * Updates the display of the command panel when in the load game state.
     * This method shows the list of saved games or appropriate messages guiding the user.
     *
     * @param commandPanel The CommandPanel that needs to be updated.
     */
    @Override
    public void display(CommandPanel commandPanel) {
        int savedGameNumber = AppHandler.getInstance().getSavedGames();
        StringBuilder message = new StringBuilder();

        if (savedGameNumber == -1) {
            message.append("There are no saved games.\n");
            message.append("Enter 'back' to return to the menu:\n");
        } else {
            message.append("You have " + savedGameNumber + " saved games.\n");
            message.append("Enter the saved game ID to load or type 'back'\nto return to the menu:\n");
        }
        commandPanel.showSystemMessage(message.toString());
    }
}