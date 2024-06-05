package org.example.view.handlers;

import org.example.model.AppHandler;
import org.example.model.entities.enums.WindowState;
import org.example.observer.Observer;
import org.example.state.EndGameState;
import org.example.state.InitGameState;
import org.example.state.MenuState;
import org.example.state.InteractionState;
import org.example.view.panels.CommandPanel;

/**
 * This class is responsible for handling the commands from the CommandPanel.
 * It implements the Observer interface and updates the state of the application based on the user's input.
 */
public class CommandPanelHandler implements Observer {
    private final CommandPanel commandPanel;
    private InteractionState currentState;

    /**
     * This method is called when the observed object is changed.
     * It updates the state of the application based on the user's input and the current window state.
     */
    @Override
    public void update() {
        String queryResult = AppHandler.getInstance().getAppState().getLastUserQueryResult().getResult();

        // If the query result is not null and not empty, show it as a system message in the command panel.
        if (queryResult != null && !queryResult.isEmpty()) {
            commandPanel.showSystemMessage(queryResult);
        }

        WindowState windowState = AppHandler.getInstance().getAppState().getCurrentWindow();
        System.out.println("CommandPanelHandler: " + windowState);

        // Update the state based on the current window state.
        if (windowState == WindowState.MENU) {
            setState(new MenuState());
        } else if (windowState == WindowState.GAME) {
            setState(new InitGameState());
        } else if (windowState == WindowState.GAME_OVER || windowState == WindowState.GAME_WIN) {
            setState(new EndGameState());
        }
    }

    /**
     * Constructor for the CommandPanelHandler.
     * It initializes the command panel and sets the initial state to MenuState.
     *
     * @param commandPanel The command panel to be handled.
     */
    public CommandPanelHandler(CommandPanel commandPanel) {
        System.out.println("Creating CommandPanelHandler");
        this.commandPanel = commandPanel;
        this.currentState = new MenuState();
        this.currentState.display(commandPanel);
    }

    /**
     * This method handles the user's input.
     * It delegates the handling to the current state.
     *
     * @param userInput The user's input to be handled.
     */
    public void handleUserInput(String userInput) {
        System.out.println("Handling user input: " + userInput);
        currentState.handleInput(this, userInput);
    }

    /**
     * This method sets the current state of the application.
     * It also displays the new state in the command panel.
     *
     * @param state The new state to be set.
     */
    public void setState(InteractionState state) {
        this.currentState = state;
        this.currentState.display(commandPanel);
    }

    /**
     * This method returns the command panel that this handler is responsible for.
     *
     * @return The command panel.
     */
    public CommandPanel getCommandPanel() {
        return commandPanel;
    }

}