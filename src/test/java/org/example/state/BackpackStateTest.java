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

class BackpackStateTest {

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
    private Pirate mockPirate;

    @Mock
    private Backpack mockBackpack;

    private BackpackState backpackState;

    @BeforeEach
    void setUp() {
        mockAppHandler = mock(AppHandler.class);
        mockCommandPanelHandler = mock(CommandPanelHandler.class);
        mockCommandPanel = mock(CommandPanel.class);
        mockAppState = mock(AppState.class);
        mockGameState = mock(GameState.class);
        mockPirate = mock(Pirate.class);
        mockBackpack = mock(Backpack.class);

        backpackState = new BackpackState();

        when(mockAppHandler.getAppState()).thenReturn(mockAppState);
        when(mockAppState.getGameState()).thenReturn(mockGameState);
        when(mockGameState.getPirate()).thenReturn(mockPirate);
        when(mockPirate.getBackpack()).thenReturn(mockBackpack);
    }

    @Test
    void handleInput_BackCommand_ChangesStateToInitGameState() {
        try (MockedStatic<AppHandler> mockedAppHandler = Mockito.mockStatic(AppHandler.class)) {
            mockedAppHandler.when(AppHandler::getInstance).thenReturn(mockAppHandler);
            backpackState.handleInput(mockCommandPanelHandler, "back");
            verify(mockCommandPanelHandler, times(1)).setState(any(InitGameState.class));
        }
    }

    @Test
    void handleInput_ValidItemId_ChangesStateToItemActionState() {
        try (MockedStatic<AppHandler> mockedAppHandler = Mockito.mockStatic(AppHandler.class)) {
            mockedAppHandler.when(AppHandler::getInstance).thenReturn(mockAppHandler);

            CollectableItem mockItem = mock(CollectableItem.class);
            when(mockItem.getID()).thenReturn(1);
            when(mockItem.getName()).thenReturn("MockItem");

            try {
                when(mockBackpack.getItemById(anyInt())).thenReturn(mockItem);
            } catch (ItemNotFoundException e) {
                e.printStackTrace();
            }

            backpackState.handleInput(mockCommandPanelHandler, "1");

            verify(mockCommandPanelHandler, times(1)).setState(any(ItemActionState.class));
        }
    }

    @Test
    void handleInput_InvalidInput_ShowsErrorMessage() {
        try (MockedStatic<AppHandler> mockedAppHandler = Mockito.mockStatic(AppHandler.class)) {
            mockedAppHandler.when(AppHandler::getInstance).thenReturn(mockAppHandler);

            when(mockCommandPanelHandler.getCommandPanel()).thenReturn(mockCommandPanel);

            backpackState.handleInput(mockCommandPanelHandler, "invalid");
            verify(mockCommandPanel, times(1)).showSystemMessage(contains("Invalid input"));
        }
    }

    @Test
    void handleInput_InvalidItemId_ShowsErrorMessage() {
        try (MockedStatic<AppHandler> mockedAppHandler = Mockito.mockStatic(AppHandler.class)) {
            mockedAppHandler.when(AppHandler::getInstance).thenReturn(mockAppHandler);

            when(mockCommandPanelHandler.getCommandPanel()).thenReturn(mockCommandPanel);
            try {
                when(mockBackpack.getItemById(anyInt())).thenThrow(ItemNotFoundException.class);
            } catch (ItemNotFoundException e) {
                e.printStackTrace();
            }

            backpackState.handleInput(mockCommandPanelHandler, "999");
            verify(mockCommandPanel, times(1)).showSystemMessage(contains("Invalid ID"));
        }
    }

    @Test
    void display_ShowsItemsInBackpack() {
        try (MockedStatic<AppHandler> mockedAppHandler = Mockito.mockStatic(AppHandler.class)) {
            mockedAppHandler.when(AppHandler::getInstance).thenReturn(mockAppHandler);

            CollectableItem mockItem = mock(CollectableItem.class);
            when(mockItem.getID()).thenReturn(1);
            when(mockItem.getName()).thenReturn("MockItem");

            ArrayList<CollectableItem> items = new ArrayList<>();
            items.add(mockItem);

            when(mockBackpack.getItems()).thenReturn(items);
            when(mockCommandPanelHandler.getCommandPanel()).thenReturn(mockCommandPanel);

            backpackState.display(mockCommandPanel);

            verify(mockCommandPanel, times(1)).showSystemMessage(contains("It contains:"));
            verify(mockCommandPanel, times(1)).showSystemMessage(contains("MockItem"));
            verify(mockCommandPanel, times(1)).showSystemMessage(contains("Enter the ID of an item to get more actions"));
        }
    }
}
