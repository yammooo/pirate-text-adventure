package org.example.view.handlers;

import org.example.view.panels.CommandPanel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

public class CommandPanelHandlerTest {

    private CommandPanel commandPanel;
    private CommandPanelHandler commandPanelHandler;

    @BeforeEach
    public void setUp() {
        commandPanel = mock(CommandPanel.class);
        commandPanelHandler = new CommandPanelHandler(commandPanel);
    }

//    @Test
//    public void handleMoveCommand() {
//        commandPanelHandler.handleCommand("move 1");
//        verify(commandPanel, times(1)).showMessage(anyString());
//    }
//
//    @Test
//    public void handlePickUpCommand() {
//        commandPanelHandler.handleCommand("pick 1");
//        verify(commandPanel, times(1)).showMessage(anyString());
//    }
//
//    @Test
//    public void handleUseCommand() {
//        commandPanelHandler.handleCommand("use 1");
//        verify(commandPanel, times(1)).showMessage(anyString());
//    }
//
//    @Test
//    public void handleDropCommand() {
//        commandPanelHandler.handleCommand("drop 1");
//        verify(commandPanel, times(1)).showMessage(anyString());
//    }
//
//    @Test
//    public void handleStartNewGameCommand() {
//        commandPanelHandler.handleCommand("start new game");
//        verify(commandPanel, times(1)).showMessage(anyString());
//    }
//
//    @Test
//    public void handleLoadGameCommand() {
//        commandPanelHandler.handleCommand("load game 1");
//        verify(commandPanel, times(1)).showMessage(anyString());
//    }

    @Test
    public void handleInvalidCommand() {
        commandPanelHandler.handleCommand("invalid command");
        verify(commandPanel, times(1)).showMessage("Invalid command: Unknown command");
    }

    @Test
    public void handleCommandWithInvalidFormat() {
        commandPanelHandler.handleCommand("move");
        verify(commandPanel, times(1)).showMessage("Invalid command: Invalid command format");
    }

    @Test
    public void handleCommandWithNullString() {
        commandPanelHandler.handleCommand(null);
        verify(commandPanel, times(1)).showMessage("Invalid command: Command cannot be null");
    }

    @Test
    public void handleCommandWithEmptyString() {
        commandPanelHandler.handleCommand("");
        verify(commandPanel, times(1)).showMessage("Invalid command: Invalid command format");
    }
}