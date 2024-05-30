package org.example.command;

import org.example.model.AppHandler;

// Command to get the description of an entity
public class ViewEntityCommand implements Command {
    private final int entityID;

    public ViewEntityCommand(int entityID) {
        this.entityID = entityID;
    }

    @Override
    public void execute() {
        AppHandler.getInstance().viewEntity(entityID);
    }
}