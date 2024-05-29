package org.example.command;

import org.example.model.AppHandler;

// Command to drop an item
public class DropItemCommand implements Command {
    private final int itemID;

    public DropItemCommand(int itemID) {
        this.itemID = itemID;
    }

    @Override
    public void execute() {
        AppHandler.getInstance().dropItem(itemID);
    }
}