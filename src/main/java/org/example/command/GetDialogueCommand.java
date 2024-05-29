package org.example.command;

import org.example.model.AppHandler;
import org.example.view.handlers.CommandPanelHandler;

// Command to get the dialogue from an entity
public class GetDialogueCommand implements Command {
    private int entityID;

    public GetDialogueCommand(int entityID) {
        this.entityID = entityID;
    }

    @Override
    public void execute(CommandPanelHandler handler) {
        String dialogue = AppHandler.getInstance().getDialogue(entityID);
        handler.showMessage(dialogue);
    }
}