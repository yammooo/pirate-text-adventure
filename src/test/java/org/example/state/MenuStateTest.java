package org.example.state;

import org.example.model.AppHandler;
import org.example.view.handlers.CommandPanelHandler;
import org.example.view.panels.CommandPanel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import static org.mockito.Mockito.*;

class MenuStateTest {

    @Mock
    private AppHandler mockAppHandler;

    @Mock
    private CommandPanelHandler mockCommandPanelHandler;

    @Mock
    private CommandPanel mockCommandPanel;

    private MenuState menuState;

    @BeforeEach
    void setUp() {
        mockAppHandler = mock(AppHandler.class);
        mockCommandPanelHandler = mock(CommandPanelHandler.class);
        mockCommandPanel = mock(CommandPanel.class);

        menuState = new MenuState();
    }

    @Test
    void handleInput_StartNewGame_CallsStartNewGame() {
        try (MockedStatic<AppHandler> mockedAppHandler = Mockito.mockStatic(AppHandler.class)) {
            mockedAppHandler.when(AppHandler::getInstance).thenReturn(mockAppHandler);

            menuState.handleInput(mockCommandPanelHandler, "1");

            verify(mockAppHandler, times(1)).startNewGame();
        }
    }

    @Test
    void handleInput_LoadGame_ChangesStateToLoadGameState() {
        menuState.handleInput(mockCommandPanelHandler, "2");

        verify(mockCommandPanelHandler, times(1)).setState(any(LoadGameState.class));
    }

//    Problematic System class mocking
//
//    @Test
//    void handleInput_Quit_CallsSystemExit() {
//        try (MockedStatic<System> mockedSystem = Mockito.mockStatic(System.class)) {
//            menuState.handleInput(mockCommandPanelHandler, "3");
//
//            mockedSystem.verify(() -> System.exit(0), times(1));
//        }
//    }

    @Test
    void handleInput_InvalidInput_ShowsErrorMessage() {
        when(mockCommandPanelHandler.getCommandPanel()).thenReturn(mockCommandPanel);

        menuState.handleInput(mockCommandPanelHandler, "invalid");

        verify(mockCommandPanel, times(1)).showSystemMessage(contains("Invalid input"));
    }

    @Test
    void display_ShowsMainMenu() {
        when(mockCommandPanelHandler.getCommandPanel()).thenReturn(mockCommandPanel);

        menuState.display(mockCommandPanel);

        verify(mockCommandPanel, times(1)).showSystemMessage(contains("Main Menu:"));
        verify(mockCommandPanel, times(1)).showSystemMessage(contains("1. Start New Game"));
        verify(mockCommandPanel, times(1)).showSystemMessage(contains("2. Load Saved Game"));
        verify(mockCommandPanel, times(1)).showSystemMessage(contains("3. Quit"));
        verify(mockCommandPanel, times(1)).showSystemMessage(contains("Enter your choice:"));
    }
}
