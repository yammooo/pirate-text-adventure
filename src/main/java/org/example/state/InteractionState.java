package org.example.state;

import org.example.view.handlers.CommandPanelHandler;
import org.example.view.panels.CommandPanel;

/**
 * The InteractionState interface is part of the state design pattern used in the game.
 * It defines the contract for different states that can handle user input and update the display on the CommandPanel.
 *
 * <p>
 * This interface ensures that each state is responsible for implementing its own behavior for handling input and updating the display,
 * providing a clear separation of concerns and promoting the Single Responsibility Principle.
 * </p>
 *
 * <p>
 * The InteractionState interface plays a crucial role in the overall architecture of the game.
 * It supports the handling of different states of the command panel dynamically,
 * depending on the current state of the game. By delegating specific input handling and display logic to the respective state classes,
 * it makes the system more maintainable, extensible, and easier to manage.
 * </p>
 *
 * <p>
 * Concrete implementations of this interface will handle user inputs and provide custom display logic for different states.
 * </p>
 */
public interface InteractionState {

    /**
     * Handles the user's input based on the current state.
     * Each state will define its specific behavior for handling input.
     *
     * @param context The context (handler) which currently holds the state.
     * @param input   The user input to be handled.
     */
    void handleInput(CommandPanelHandler context, String input);

    /**
     * Updates the display of the command panel based on the current state.
     * Each state will define its specific behavior for updating the display.
     *
     * @param commandPanel The CommandPanel that needs to be updated.
     */
    void display(CommandPanel commandPanel);
}