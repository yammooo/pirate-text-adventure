package org.example.command;

import org.example.model.AppHandler;
import org.example.view.handlers.CommandPanelHandler;

public class MoveCommand implements Command {
    private int locationID;

    public MoveCommand(int locationID) {
        this.locationID = locationID;
    }

    @Override
    public void execute(CommandPanelHandler handler) {
        AppHandler.getInstance().move(locationID);
    }
}