package org.example.command;

import org.example.model.AppHandler;

public class PickUpItemCommand implements Command {
    private final int itemID;

    public PickUpItemCommand(int itemID) {
        this.itemID = itemID;
    }

    @Override
    public void execute() {
        AppHandler.getInstance().pickUpItem(itemID);
    }
}