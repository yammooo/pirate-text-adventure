package org.example.state;

import org.example.view.handlers.CommandPanelHandler;
import org.example.view.panels.CommandPanel;

public interface UserInteractionState {
    void handleInput(CommandPanelHandler context, String input);
    void display(CommandPanel commandPanel);
}
