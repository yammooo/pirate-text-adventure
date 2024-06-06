package org.example.state;

import org.example.model.AppHandler;
import org.example.model.GameState;
import org.example.model.entities.Location;
import org.example.model.entities.Map;
import org.example.view.handlers.CommandPanelHandler;
import org.example.view.panels.CommandPanel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import static org.mockito.Mockito.*;

class InitGameStateTest {

    private AppHandler appHandler;
    private CommandPanelHandler commandPanelHandler;
    private CommandPanel commandPanel;
    private InitGameState initGameState;

    @BeforeEach
    void setUp() {
        appHandler = mock(AppHandler.class);
        commandPanel = mock(CommandPanel.class);
        commandPanelHandler = mock(CommandPanelHandler.class);
        initGameState = new InitGameState();
    }

    @Test
    void handleInput_ValidInput_ViewSurroundings() {
        try (MockedStatic<AppHandler> mockedAppHandler = Mockito.mockStatic(AppHandler.class)) {
            mockedAppHandler.when(AppHandler::getInstance).thenReturn(appHandler);
            initGameState.handleInput(commandPanelHandler, "1");
            verify(commandPanelHandler, times(1)).setState(any(SurroundingsState.class));
        }
    }

    @Test
    void handleInput_ValidInput_MoveToAnotherLocation() {
        try (MockedStatic<AppHandler> mockedAppHandler = Mockito.mockStatic(AppHandler.class)) {
            mockedAppHandler.when(AppHandler::getInstance).thenReturn(appHandler);
            initGameState.handleInput(commandPanelHandler, "2");
            verify(commandPanelHandler, times(1)).setState(any(MoveState.class));
        }
    }

    @Test
    void handleInput_ValidInput_ShowBackpack() {
        try (MockedStatic<AppHandler> mockedAppHandler = Mockito.mockStatic(AppHandler.class)) {
            mockedAppHandler.when(AppHandler::getInstance).thenReturn(appHandler);
            initGameState.handleInput(commandPanelHandler, "3");
            verify(commandPanelHandler, times(1)).setState(any(BackpackState.class));
        }
    }

    @Test
    void handleInput_ExitToMenu_InvokesExitToMenu() {
        try (MockedStatic<AppHandler> mockedAppHandler = Mockito.mockStatic(AppHandler.class)) {
            mockedAppHandler.when(AppHandler::getInstance).thenReturn(appHandler);
            initGameState.handleInput(commandPanelHandler, "4");
            verify(appHandler, times(1)).exitToMenu();
        }
    }

    @Test
    void handleInput_InvalidInput_ShowErrorMessage() {
        when(commandPanelHandler.getCommandPanel()).thenReturn(commandPanel);
        initGameState.handleInput(commandPanelHandler, "invalid");
        verify(commandPanel, times(1)).showSystemMessage("Invalid input. Please enter a valid choice.");
    }

    @Test
    void display_ShowsCorrectMessage() {
        try (MockedStatic<AppHandler> mockedAppHandler = Mockito.mockStatic(AppHandler.class)) {
            GameState mockGameState = mock(GameState.class);
            Location mockLocation = mock(Location.class);
            Map mockMap = mock(Map.class);
            when(mockLocation.getName()).thenReturn("Test Location");
            when(mockLocation.getDescription()).thenReturn("Test Description");
            when(mockGameState.getMap()).thenReturn(mockMap);
            when(mockMap.getPirateLocationID()).thenReturn(1);
            when(mockMap.getLocationById(anyInt())).thenReturn(mockLocation);

            when(AppHandler.getInstance().getAppState().getGameState()).thenReturn(mockGameState);

            initGameState.display(commandPanel);
            verify(commandPanel, times(1)).showSystemMessage(contains("What do you want to do?"));
        }
    }
}