package org.example.command;

import org.example.model.AppHandler;
import org.example.view.handlers.CommandPanelHandler;

// Command to load a game
public class LoadGameCommand implements Command {
    private int gameID;

    public LoadGameCommand(int gameID) {
        this.gameID = gameID;
    }

    @Override
    public void execute(CommandPanelHandler handler) {
        AppHandler.getInstance().startSavedGame(gameID);
    }
}