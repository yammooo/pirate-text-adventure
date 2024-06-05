package org.example.state;

import org.example.model.AppHandler;
import org.example.view.handlers.CommandPanelHandler;
import org.example.view.panels.CommandPanel;

public class EndGameState implements InteractionState{
    @Override
    public void handleInput(CommandPanelHandler context, String input) {
        if (input.equalsIgnoreCase("exit")) {
            AppHandler.getInstance().exitToMenu();
        }
        else {
            context.getCommandPanel().showSystemMessage("Invalid input.");
            display(context.getCommandPanel());
        }
    }

    @Override
    public void display(CommandPanel commandPanel) {
        String message = "Enter 'exit' to return to the menu:\n";
        commandPanel.showSystemMessage(message);
    }
}
