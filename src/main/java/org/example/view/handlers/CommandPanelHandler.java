package org.example.view.handlers;

import org.example.model.AppHandler;
import org.example.model.entities.enums.WindowState;
import org.example.observer.Observer;
import org.example.state.InitGameState;
import org.example.state.MenuState;
import org.example.state.InteractionState;
import org.example.view.panels.CommandPanel;

public class CommandPanelHandler implements Observer {
    private final CommandPanel commandPanel;
    private InteractionState currentState;

    @Override
    public void update() {
        String queryResult = AppHandler.getInstance().getAppState().getLastUserQueryResult().getResult();

        if (queryResult != null && !queryResult.isEmpty()) {
            commandPanel.showSystemMessage(queryResult);
        }

        WindowState windowState = AppHandler.getInstance().getAppState().getCurrentWindow();
        System.out.println("CommandPanelHandler: " + windowState);

        if (windowState == WindowState.MENU) {
            setState(new MenuState());
        } else if (windowState == WindowState.GAME) {
            setState(new InitGameState());
        }
    }

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
