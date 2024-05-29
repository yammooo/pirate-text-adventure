package org.example.command;

import org.example.model.AppHandler;

// Command to get the description of an entity
public class GetDescriptionCommand implements Command {
    private final int entityID;

    public GetDescriptionCommand(int entityID) {
        this.entityID = entityID;
    }

    @Override
    public void execute() {
        AppHandler.getInstance().getDescription(entityID);
    }
}