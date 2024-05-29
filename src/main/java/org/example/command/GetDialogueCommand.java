package org.example.command;

import org.example.model.AppHandler;

// Command to get the dialogue from an entity
public class GetDialogueCommand implements Command {
    private final int entityID;

    public GetDialogueCommand(int entityID) {
        this.entityID = entityID;
    }

    @Override
    public void execute() {
        AppHandler.getInstance().getDialogue(entityID);
    }
}