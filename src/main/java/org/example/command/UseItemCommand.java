package org.example.command;

import org.example.model.AppHandler;

public class UseItemCommand implements Command {
    private final int itemID;

    public UseItemCommand(int itemID) {
        this.itemID = itemID;
    }

    @Override
    public void execute() {
        AppHandler.getInstance().useItem(itemID);
    }
}