package org.example.command;

import org.example.model.AppHandler;

public class MoveCommand implements Command {
    private final int locationID;

    public MoveCommand(int locationID) {
        this.locationID = locationID;
    }

    @Override
    public void execute() {
        AppHandler.getInstance().move(locationID);
    }
}