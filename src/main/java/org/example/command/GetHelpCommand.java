package org.example.command;

import org.example.model.AppHandler;
import org.example.view.handlers.CommandPanelHandler;

// Command to get help information
public class GetHelpCommand implements Command {
    @Override
    public void execute(CommandPanelHandler handler) {
        String helpMessage = AppHandler.getInstance().getHelp();
        handler.showMessage(helpMessage);
    }
}