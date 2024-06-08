package org.example.state;

import org.example.model.AppHandler;
import org.example.model.AppState;
import org.example.model.GameState;
import org.example.model.entities.Location;
import org.example.model.entities.Map;
import org.example.view.handlers.CommandPanelHandler;
import org.example.view.panels.CommandPanel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import java.util.ArrayList;

import static org.mockito.Mockito.*;

class MoveStateTest {

    @Mock
    private AppHandler mockAppHandler;

    @Mock
    private CommandPanelHandler mockCommandPanelHandler;

    @Mock
    private CommandPanel mockCommandPanel;

    @Mock
    private AppState mockAppState;

    @Mock
    private GameState mockGameState;

    @Mock
    private Location mockLocation;

    private MoveState moveState;

    @BeforeEach
    void setUp() {
        mockAppHandler = mock(AppHandler.class);
        mockCommandPanelHandler = mock(CommandPanelHandler.class);
        mockCommandPanel = mock(CommandPanel.class);
        mockAppState = mock(AppState.class);
        mockGameState = mock(GameState.class);
        mockLocation = mock(Location.class);

        moveState = new MoveState();

        when(mockAppHandler.getAppState()).thenReturn(mockAppState);
        when(mockAppState.getGameState()).thenReturn(mockGameState);
        when(mockGameState.getMap()).thenReturn(mock(Map.class));
        when(mockGameState.getMap().getLocationById(anyInt())).thenReturn(mockLocation);
    }

    @Test
    void handleInput_BackCommand_ChangesStateToInitGameState() {
        try (MockedStatic<AppHandler> mockedAppHandler = Mockito.mockStatic(AppHandler.class)) {
            mockedAppHandler.when(AppHandler::getInstance).thenReturn(mockAppHandler);
            moveState.handleInput(mockCommandPanelHandler, "back");
            verify(mockCommandPanelHandler, times(1)).setState(any(InitGameState.class));
        }
    }

    @Test
    void handleInput_ValidLocationId_CallsMove() {
        try (MockedStatic<AppHandler> mockedAppHandler = Mockito.mockStatic(AppHandler.class)) {
            mockedAppHandler.when(AppHandler::getInstance).thenReturn(mockAppHandler);

            moveState.handleInput(mockCommandPanelHandler, "1");

            verify(mockAppHandler, times(1)).move(1);
        }
    }

    @Test
    void handleInput_InvalidInput_ShowsErrorMessage() {
        try (MockedStatic<AppHandler> mockedAppHandler = Mockito.mockStatic(AppHandler.class)) {
            mockedAppHandler.when(AppHandler::getInstance).thenReturn(mockAppHandler);

            when(mockCommandPanelHandler.getCommandPanel()).thenReturn(mockCommandPanel);

            moveState.handleInput(mockCommandPanelHandler, "invalid");

            verify(mockCommandPanel, times(1)).showSystemMessage(contains("Invalid input"));
        }
    }

    @Test
    void display_ShowsAdjacentLocations() {
        try (MockedStatic<AppHandler> mockedAppHandler = Mockito.mockStatic(AppHandler.class)) {
            mockedAppHandler.when(AppHandler::getInstance).thenReturn(mockAppHandler);

            Location mockAdjacentLocation1 = mock(Location.class);
            Location mockAdjacentLocation2 = mock(Location.class);

            when(mockAdjacentLocation1.getID()).thenReturn(1);
            when(mockAdjacentLocation1.getName()).thenReturn("Location 1");

            when(mockAdjacentLocation2.getID()).thenReturn(2);
            when(mockAdjacentLocation2.getName()).thenReturn("Location 2");

            ArrayList<Integer> adjacentLocationIds = new ArrayList<>();
            adjacentLocationIds.add(mockAdjacentLocation1.getID());
            adjacentLocationIds.add(mockAdjacentLocation2.getID());

            when(mockLocation.getAdjacentLocations()).thenReturn(adjacentLocationIds);
            when(mockGameState.getMap().getLocationById(1)).thenReturn(mockAdjacentLocation1);
            when(mockGameState.getMap().getLocationById(2)).thenReturn(mockAdjacentLocation2);
            when(mockCommandPanelHandler.getCommandPanel()).thenReturn(mockCommandPanel);

            moveState.display(mockCommandPanel);

            verify(mockCommandPanel, times(1)).showSystemMessage(contains("Where do you want to move?"));
            verify(mockCommandPanel, times(1)).showSystemMessage(contains("1: Location 1"));
            verify(mockCommandPanel, times(1)).showSystemMessage(contains("2: Location 2"));
        }
    }
}
