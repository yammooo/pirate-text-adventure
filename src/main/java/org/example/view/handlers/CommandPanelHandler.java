package org.example.view.handlers;

import org.example.state.MenuState;
import org.example.state.InteractionState;
import org.example.view.panels.CommandPanel;

public class CommandPanelHandler {
    private final CommandPanel commandPanel;
    private InteractionState currentState;

    public CommandPanelHandler(CommandPanel commandPanel) {
        System.out.println("Creating CommandPanelHandler");
        this.commandPanel = commandPanel;
        this.currentState = new MenuState();
        this.currentState.display(commandPanel);
    }

    public void handleUserInput(String userInput) {
        System.out.println("Handling user input: " + userInput);
        currentState.handleInput(this, userInput);
    }

    public void setState(InteractionState state) {
        this.currentState = state;
        this.currentState.display(commandPanel);
    }

    public CommandPanel getCommandPanel() {
        return commandPanel;
    }
}
