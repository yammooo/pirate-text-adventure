package org.example.command;

import org.example.model.AppHandler;
import org.example.view.handlers.CommandPanelHandler;

public class UseItemCommand implements Command {
    private int itemID;

    public UseItemCommand(int itemID) {
        this.itemID = itemID;
    }

    @Override
    public void execute(CommandPanelHandler handler) {
        AppHandler.getInstance().useItem(itemID);
    }
}