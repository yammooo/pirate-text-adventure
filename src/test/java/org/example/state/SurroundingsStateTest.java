package org.example.state;

import org.example.exceptions.ItemNotFoundException;
import org.example.model.AppHandler;
import org.example.model.AppState;
import org.example.model.GameState;
import org.example.model.entities.*;
import org.example.view.handlers.CommandPanelHandler;
import org.example.view.panels.CommandPanel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import java.util.ArrayList;

import static org.mockito.Mockito.*;

class SurroundingsStateTest {

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
    private Map mockMap;

    @Mock
    private Location mockLocation;

    private SurroundingsState surroundingsState;

    @BeforeEach
    void setUp() {
        mockAppHandler = mock(AppHandler.class);
        mockCommandPanelHandler = mock(CommandPanelHandler.class);
        mockCommandPanel = mock(CommandPanel.class);
        mockAppState = mock(AppState.class);
        mockGameState = mock(GameState.class);
        mockMap = mock(Map.class);
        mockLocation = mock(Location.class);

        surroundingsState = new SurroundingsState();

        when(mockAppHandler.getAppState()).thenReturn(mockAppState);
        when(mockAppState.getGameState()).thenReturn(mockGameState);
        when(mockGameState.getMap()).thenReturn(mockMap);
        when(mockMap.getLocationById(anyInt())).thenReturn(mockLocation);
    }

    @Test
    void handleInput_BackCommand_ChangesStateToInitGameState() {
        try (MockedStatic<AppHandler> mockedAppHandler = Mockito.mockStatic(AppHandler.class)) {
            mockedAppHandler.when(AppHandler::getInstance).thenReturn(mockAppHandler);
            surroundingsState.handleInput(mockCommandPanelHandler, "back");
            verify(mockCommandPanelHandler, times(1)).setState(any(InitGameState.class));
        }
    }

    @Test
    void handleInput_ValidEntityId_ChangesStateToEntitySelectState() {
        try (MockedStatic<AppHandler> mockedAppHandler = Mockito.mockStatic(AppHandler.class)) {

            mockedAppHandler.when(AppHandler::getInstance).thenReturn(mockAppHandler);

            Entity mockEntity = mock(Entity.class);
            when(mockMap.getPirateLocationID()).thenReturn(1);

            try {
                when(mockLocation.getEntityById(anyInt())).thenReturn(mockEntity);
            } catch (ItemNotFoundException e) {
                e.printStackTrace();
            }

            surroundingsState.handleInput(mockCommandPanelHandler, "1");

            verify(mockCommandPanelHandler, times(1)).setState(any(EntitySelectState.class));
        }
    }

    @Test
    void handleInput_InvalidInput_ShowsErrorMessage() {
        try (MockedStatic<AppHandler> mockedAppHandler = Mockito.mockStatic(AppHandler.class)) {

            mockedAppHandler.when(AppHandler::getInstance).thenReturn(mockAppHandler);

            when(mockMap.getPirateLocationID()).thenReturn(1);
            when(mockCommandPanelHandler.getCommandPanel()).thenReturn(mockCommandPanel);

            surroundingsState.handleInput(mockCommandPanelHandler, "invalid");
            verify(mockCommandPanel, times(1)).showSystemMessage(contains("Invalid input"));
        }
    }

    @Test
    void handleInput_InvalidEntityId_ShowsErrorMessage() {
        try (MockedStatic<AppHandler> mockedAppHandler = Mockito.mockStatic(AppHandler.class)) {

            mockedAppHandler.when(AppHandler::getInstance).thenReturn(mockAppHandler);

            when(mockMap.getPirateLocationID()).thenReturn(1);

            try {
                when(mockLocation.getEntityById(anyInt())).thenThrow(ItemNotFoundException.class);
            } catch (ItemNotFoundException e) {
                e.printStackTrace();
            }

            when(mockCommandPanelHandler.getCommandPanel()).thenReturn(mockCommandPanel);
            surroundingsState.handleInput(mockCommandPanelHandler, "999");

            verify(mockCommandPanel, times(1)).showSystemMessage(contains("Invalid ID"));
        }
    }

    @Test
    void display_ShowsNothingToSeeHere() {
        try (MockedStatic<AppHandler> mockedAppHandler = Mockito.mockStatic(AppHandler.class)) {

            mockedAppHandler.when(AppHandler::getInstance).thenReturn(mockAppHandler);

            when(mockLocation.getCollectableItems()).thenReturn(new ArrayList<CollectableItem>());
            when(mockLocation.getViewableItems()).thenReturn(new ArrayList<ViewableItem>());
            when(mockLocation.getNPC()).thenReturn(new ArrayList<NPC>());
            when(mockMap.getPirateLocationID()).thenReturn(1);
            when(mockCommandPanelHandler.getCommandPanel()).thenReturn(mockCommandPanel);

            surroundingsState.display(mockCommandPanel);
            verify(mockCommandPanel, times(1)).showSystemMessage(contains("Nothing to see here"));
        }
    }

    @Test
    void display_ShowsEntities() {
        try (MockedStatic<AppHandler> mockedAppHandler = Mockito.mockStatic(AppHandler.class)) {

            mockedAppHandler.when(AppHandler::getInstance).thenReturn(mockAppHandler);

            CollectableItem mockItem = mock(CollectableItem.class);
            ViewableItem mockViewItem = mock(ViewableItem.class);
            NPC mockNpc = mock(NPC.class);

            when(mockLocation.getName()).thenReturn("Test Location");
            when(mockLocation.getDescription()).thenReturn("Test Description");

            when(mockItem.getID()).thenReturn(1);
            when(mockItem.getName()).thenReturn("MockItem");

            when(mockViewItem.getID()).thenReturn(2);
            when(mockViewItem.getName()).thenReturn("MockViewItem");

            when(mockNpc.getID()).thenReturn(3);
            when(mockNpc.getName()).thenReturn("MockNPC");

            ArrayList<CollectableItem> collectableItems = new ArrayList<CollectableItem>();
            collectableItems.add(mockItem);

            ArrayList<ViewableItem> viewableItems = new ArrayList<ViewableItem>();
            viewableItems.add(mockViewItem);

            ArrayList<NPC> npcs = new ArrayList<NPC>();
            npcs.add(mockNpc);

            when(mockLocation.getCollectableItems()).thenReturn(collectableItems);
            when(mockLocation.getViewableItems()).thenReturn(viewableItems);
            when(mockLocation.getNPC()).thenReturn(npcs);

            when(mockMap.getPirateLocationID()).thenReturn(1);

            surroundingsState.display(mockCommandPanel);

            verify(mockCommandPanel, times(1)).showSystemMessage(contains("You look around and you see"));
            verify(mockCommandPanel, times(1)).showSystemMessage(contains("Collectable Items"));
            verify(mockCommandPanel, times(1)).showSystemMessage(contains("MockItem"));
            verify(mockCommandPanel, times(1)).showSystemMessage(contains("Viewable Items"));
            verify(mockCommandPanel, times(1)).showSystemMessage(contains("MockViewItem"));
            verify(mockCommandPanel, times(1)).showSystemMessage(contains("NPCs"));
            verify(mockCommandPanel, times(1)).showSystemMessage(contains("MockNPC"));
        }
    }
}
