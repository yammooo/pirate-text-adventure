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

class LoadGameStateTest {

    @Mock
    private AppHandler mockAppHandler;

    @Mock
    private CommandPanelHandler mockCommandPanelHandler;

    @Mock
    private CommandPanel mockCommandPanel;

    private LoadGameState loadGameState;

    @BeforeEach
    void setUp() {
        mockAppHandler = mock(AppHandler.class);
        mockCommandPanelHandler = mock(CommandPanelHandler.class);
        mockCommandPanel = mock(CommandPanel.class);

        loadGameState = new LoadGameState();
    }

    @Test
    void handleInput_BackCommand_ChangesStateToMenuState() {
        loadGameState.handleInput(mockCommandPanelHandler, "back");

        verify(mockCommandPanelHandler, times(1)).setState(any(MenuState.class));
    }

    @Test
    void handleInput_ValidSaveId_CallsStartSavedGame() {
        try (MockedStatic<AppHandler> mockedAppHandler = Mockito.mockStatic(AppHandler.class)) {
            mockedAppHandler.when(AppHandler::getInstance).thenReturn(mockAppHandler);

            when(mockAppHandler.getSavedGames()).thenReturn(3);

            loadGameState.handleInput(mockCommandPanelHandler, "2");

            verify(mockAppHandler, times(1)).startSavedGame(2);
        }
    }

    @Test
    void handleInput_InvalidInput_ShowsErrorMessage() {
        when(mockCommandPanelHandler.getCommandPanel()).thenReturn(mockCommandPanel);

        loadGameState.handleInput(mockCommandPanelHandler, "invalid");

        verify(mockCommandPanel, times(1)).showSystemMessage(contains("Invalid input"));
    }

    @Test
    void display_NoSavedGames_ShowsNoSavedGamesMessage() {
        try (MockedStatic<AppHandler> mockedAppHandler = Mockito.mockStatic(AppHandler.class)) {
            mockedAppHandler.when(AppHandler::getInstance).thenReturn(mockAppHandler);

            when(mockAppHandler.getSavedGames()).thenReturn(-1);

            loadGameState.display(mockCommandPanel);

            verify(mockCommandPanel, times(1)).showSystemMessage(contains("There are no saved games"));
        }
    }

    @Test
    void display_SavedGamesAvailable_ShowsSavedGamesMessage() {
        try (MockedStatic<AppHandler> mockedAppHandler = Mockito.mockStatic(AppHandler.class)) {
            mockedAppHandler.when(AppHandler::getInstance).thenReturn(mockAppHandler);

            when(mockAppHandler.getSavedGames()).thenReturn(3);

            loadGameState.display(mockCommandPanel);

            verify(mockCommandPanel, times(1)).showSystemMessage(contains("You have 3 saved games"));
        }
    }
}
