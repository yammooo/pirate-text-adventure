package org.example.command;

import org.example.model.AppHandler;
import org.example.view.handlers.CommandPanelHandler;

public class PickUpItemCommand implements Command {
    private int itemID;

    public PickUpItemCommand(int itemID) {
        this.itemID = itemID;
    }

    @Override
    public void execute(CommandPanelHandler handler) {
        AppHandler.getInstance().pickUpItem(itemID);
    }
}