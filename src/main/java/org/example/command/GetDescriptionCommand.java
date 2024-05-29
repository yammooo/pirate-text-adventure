package org.example.command;

import org.example.model.AppHandler;
import org.example.view.handlers.CommandPanelHandler;

// Command to get the description of an entity
public class GetDescriptionCommand implements Command {
    private int entityID;

    public GetDescriptionCommand(int entityID) {
        this.entityID = entityID;
    }

    @Override
    public void execute(CommandPanelHandler handler) {
        String description = AppHandler.getInstance().getDescription(entityID);
        handler.showMessage(description);
    }
}