package org.example.command;

import org.example.model.AppHandler;

// Command to exit to the menu
public class ExitToMenuCommand implements Command {
    @Override
    public void execute() {
        AppHandler.getInstance().exitToMenu();
    }
}