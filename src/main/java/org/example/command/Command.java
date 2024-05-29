package org.example.command;

import org.example.view.handlers.CommandPanelHandler;

public interface Command {
    void execute(CommandPanelHandler handler);
}
