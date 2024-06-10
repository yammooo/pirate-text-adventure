package org.example.view.handlers;

import org.example.model.AppHandler;
import org.example.state.EndGameState;
import org.example.state.InteractionState;
import org.example.state.MenuState;
import org.example.view.panels.CommandPanel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.*;

class CommandPanelHandlerTest {

    @Mock
    CommandPanel mockCommandPanel;

    CommandPanelHandler commandPanelHandler;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        commandPanelHandler = new CommandPanelHandler(mockCommandPanel);
    }

    @Test
    void updateSetStateTest() {
        AppHandler.getInstance().getAppState().setWindowToMenu();
        CommandPanelHandler spyPanel = spy(commandPanelHandler);
        spyPanel.update();
        verify(spyPanel).setState(any(MenuState.class));

        AppHandler.getInstance().getAppState().setWindowToGameOver();
        spyPanel = spy(commandPanelHandler);
        spyPanel.update();
        verify(spyPanel).setState(any(EndGameState.class));

        AppHandler.getInstance().getAppState().setWindowToGameWin();
        spyPanel = spy(commandPanelHandler);
        spyPanel.update();
        verify(spyPanel).setState(any(EndGameState.class));
    }

    @Test
    void handleUserInputTest() {
        String userInput = "test input";

        try {
            Field currentStateField = CommandPanelHandler.class.getDeclaredField("currentState");
            currentStateField.setAccessible(true);
            InteractionState mockState = mock(InteractionState.class);
            currentStateField.set(commandPanelHandler, mockState);

            commandPanelHandler.handleUserInput(userInput);

            verify(mockState).handleInput(eq(commandPanelHandler), eq(userInput));
        } catch (NoSuchFieldException | IllegalAccessException e) {
            // Handle the exception appropriately
            e.printStackTrace(); // Or log the exception
            fail("Exception occurred: " + e.getMessage());
        }
    }
}
