package org.example.command;

import org.example.model.AppHandler;
import org.example.view.handlers.CommandPanelHandler;

// Command to start a new game
public class StartNewGameCommand implements Command {
    @Override
    public void execute(CommandPanelHandler handler) {
        AppHandler.getInstance().startNewGame();
    }
}