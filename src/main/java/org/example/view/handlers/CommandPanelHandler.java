package org.example.view.handlers;

import org.example.state.MainMenuState;
import org.example.state.UserInteractionState;
import org.example.view.panels.CommandPanel;

public class CommandPanelHandler {
    private final CommandPanel commandPanel;
    private UserInteractionState currentState;

    public CommandPanelHandler(CommandPanel commandPanel) {
        System.out.println("Creating CommandPanelHandler");
        this.commandPanel = commandPanel;
        this.currentState = new MainMenuState();
        this.currentState.display(commandPanel);
    }

    public void handleUserInput(String userInput) {
        System.out.println("Handling user input: " + userInput);
        currentState.handleInput(this, userInput);
    }

    public void setState(UserInteractionState state) {
        this.currentState = state;
        this.currentState.display(commandPanel);
    }

    public CommandPanel getCommandPanel() {
        return commandPanel;
    }
}
