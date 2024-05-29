package org.example.command;

import org.example.model.AppHandler;

// Command to get help information
public class GetHelpCommand implements Command {
    @Override
    public void execute() {
        AppHandler.getInstance().getHelp();
    }
}