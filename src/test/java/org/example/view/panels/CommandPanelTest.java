package org.example.view.panels;

import org.example.view.handlers.CommandPanelHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.swing.*;
import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.*;

class CommandPanelTest {

    CommandPanel commandPanel;

    @BeforeEach
    void setUp() {
        commandPanel = new CommandPanel();
    }

    @Test
    void testShowSystemMessage() {
        try {
            String message = "Test system message";

            // Access the private field commandPanelHandler
            Field handlerField = CommandPanel.class.getDeclaredField("commandPanelHandler");
            handlerField.setAccessible(true);
            CommandPanelHandler mockCommandPanelHandler = mock(CommandPanelHandler.class);
            handlerField.set(commandPanel, mockCommandPanelHandler);

            // Invoke the method to be tested
            commandPanel.showSystemMessage(message);

            // Verify interactions
            verify(mockCommandPanelHandler, never()).handleUserInput(anyString());
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
            fail("Exception during test setup: " + e.getMessage());
        }
    }

    @Test
    void testHandleUserInput() {
        try {
            String userInput = "Test input";

            // Access the private field commandPanelHandler
            Field handlerField = CommandPanel.class.getDeclaredField("commandPanelHandler");
            handlerField.setAccessible(true);
            CommandPanelHandler mockCommandPanelHandler = mock(CommandPanelHandler.class);
            handlerField.set(commandPanel, mockCommandPanelHandler);

            // Access the private field inputField
            Field inputFieldField = CommandPanel.class.getDeclaredField("inputField");
            inputFieldField.setAccessible(true);
            JTextField inputField = (JTextField) inputFieldField.get(commandPanel);

            // Simulate user input
            inputField.setText(userInput);
            inputField.postActionEvent();

            // Verify that the handler processes the input
            verify(mockCommandPanelHandler).handleUserInput(eq(userInput));
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
            fail("Exception during test setup: " + e.getMessage());
        }
    }
}
