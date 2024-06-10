package org.example.state;

import org.example.model.AppHandler;
import org.example.view.handlers.CommandPanelHandler;
import org.example.view.panels.CommandPanel;

/**
 * EndGameState is a concrete implementation of the InteractionState interface.
 * It represents the state of the game when the game has ended, either by winning or losing.
 *
 * <p>
 * This class handles the specific interactions available during the end game state,
 * providing the user with the option to return to the main menu.
 * </p>
 *
 * @see InteractionState
 */
public class EndGameState implements InteractionState {

    /**
     * Handles the user's input during the end game state.
     * If the user inputs "exit", the game will transition back to the main menu.
     * Otherwise, it will notify the user of the invalid input and prompt them to try again.
     *
     * @param context The context (handler) which currently holds this state.
     * @param input   The user input to be handled.
     */
    @Override
    public void handleInput(CommandPanelHandler context, String input) {
        if (input.equalsIgnoreCase("exit")) {
            AppHandler.getInstance().exitToMenu();
        } else {
            context.getCommandPanel().showSystemMessage("Invalid input.");
            display(context.getCommandPanel());
        }
    }

    /**
     * Updates the display of the command panel during the end game state.
     * It prompts the user to enter 'exit' to return to the main menu.
     *
     * @param commandPanel The CommandPanel that needs to be updated.
     */
    @Override
    public void display(CommandPanel commandPanel) {
        String message = "Enter 'exit' to return to the menu:\n";
        commandPanel.showSystemMessage(message);
    }
}