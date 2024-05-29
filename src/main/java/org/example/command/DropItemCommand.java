package org.example.command;

import org.example.model.AppHandler;
import org.example.view.handlers.CommandPanelHandler;

// Command to drop an item
public class DropItemCommand implements Command {
    private int itemID;

    public DropItemCommand(int itemID) {
        this.itemID = itemID;
    }

    @Override
    public void execute(CommandPanelHandler handler) {
        AppHandler.getInstance().dropItem(itemID);
    }
}