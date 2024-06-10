package org.example.state;

import org.example.model.AppHandler;
import org.example.view.handlers.CommandPanelHandler;
import org.example.view.panels.CommandPanel;

/**
 * The MenuState class represents the main menu state in the game.
 * It implements the InteractionState interface to handle user inputs and update the display
 * when the game is in the main menu.
 *
 * <p>
 * It allows the user to start a new game, load a saved game, or quit the application. By implementing the
 * InteractionState interface, it ensures that user inputs are handled appropriately and that the corresponding
 * display updates are performed.
 * </p>
 *
 * @see InteractionState
 */
public class MenuState implements InteractionState {

    /**
     * Handles the user's input when the game is in the main menu state.
     * Depending on the input, the method can start a new game, load a saved game,
     * or quit the application. If the input is invalid, it prompts the user to enter a valid option.
     *
     * @param context The context (handler) which currently holds this state.
     * @param input   The user input to be handled.
     */
    @Override
    public void handleInput(CommandPanelHandler context, String input) {
        switch (input) {
            case "1":
                AppHandler.getInstance().startNewGame();
                // In case starting a new game fails, remain in the menu state
                break;
            case "2":
                context.setState(new LoadGameState());
                break;
            case "3":
                System.exit(0);
                break;
            default:
                context.getCommandPanel().showSystemMessage("Invalid input. Please choose a valid option.");
                display(context.getCommandPanel());
                break;
        }
    }

    /**
     * Updates the command panel to display the main menu options.
     * Provides options for starting a new game, loading a saved game or quitting the application.
     *
     * @param commandPanel The CommandPanel that needs to be updated.
     */
    @Override
    public void display(CommandPanel commandPanel) {
        StringBuilder message = new StringBuilder("Main Menu:\n");
        message.append("1. Start New Game\n");
        message.append("2. Load Saved Game\n");
        message.append("3. Quit\n");
        message.append("Enter your choice:\n");
        commandPanel.showSystemMessage(message.toString());
    }
}