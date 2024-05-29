package org.example.command;

import org.example.model.AppHandler;
import org.example.view.handlers.CommandPanelHandler;

// Command to exit to the menu
public class ExitToMenuCommand implements Command {
    @Override
    public void execute(CommandPanelHandler handler) {
        AppHandler.getInstance().exitToMenu();
    }
}