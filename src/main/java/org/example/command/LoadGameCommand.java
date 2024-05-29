package org.example.command;

import org.example.model.AppHandler;

// Command to load a game
public class LoadGameCommand implements Command {
    private final int gameID;

    public LoadGameCommand(int gameID) {
        this.gameID = gameID;
    }

    @Override
    public void execute() {
        AppHandler.getInstance().startSavedGame(gameID);
    }
}