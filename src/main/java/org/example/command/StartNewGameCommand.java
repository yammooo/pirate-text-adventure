package org.example.command;

import org.example.model.AppHandler;

// Command to start a new game
public class StartNewGameCommand implements Command {
    @Override
    public void execute() {
        AppHandler.getInstance().startNewGame();
    }
}