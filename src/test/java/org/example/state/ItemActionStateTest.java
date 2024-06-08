package org.example.state;

import org.example.model.AppHandler;
import org.example.model.entities.Entity;
import org.example.view.handlers.CommandPanelHandler;
import org.example.view.panels.CommandPanel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import static org.mockito.Mockito.*;

class ItemActionStateTest {

    @Mock
    private AppHandler mockAppHandler;

    @Mock
    private CommandPanelHandler mockCommandPanelHandler;

    @Mock
    private CommandPanel mockCommandPanel;

    @Mock
    private Entity mockEntity;

    private ItemActionState itemActionState;

    @BeforeEach
    void setUp() {
        mockAppHandler = mock(AppHandler.class);
        mockCommandPanelHandler = mock(CommandPanelHandler.class);
        mockCommandPanel = mock(CommandPanel.class);
        mockEntity = mock(Entity.class);

        itemActionState = new ItemActionState(mockEntity);
    }

    @Test
    void handleInput_BackCommand_ChangesStateToBackpackState() {
        itemActionState.handleInput(mockCommandPanelHandler, "back");
        verify(mockCommandPanelHandler, times(1)).setState(any(BackpackState.class));
    }

    @Test
    void handleInput_LookCloserCommand_ShowsEntityDescriptionAndChangesStateToInitGameState() {
        when(mockEntity.getDescription()).thenReturn("Mock Description");
        when(mockCommandPanelHandler.getCommandPanel()).thenReturn(mockCommandPanel);

        itemActionState.handleInput(mockCommandPanelHandler, "1");

        verify(mockCommandPanel, times(1)).showSystemMessage(contains("Mock Description"));
        verify(mockCommandPanelHandler, times(1)).setState(any(InitGameState.class));
    }

    @Test
    void handleInput_DropItemCommand_DropsItem() {
        try (MockedStatic<AppHandler> mockedAppHandler = Mockito.mockStatic(AppHandler.class)) {
            mockedAppHandler.when(AppHandler::getInstance).thenReturn(mockAppHandler);

            when(mockEntity.getID()).thenReturn(1);

            itemActionState.handleInput(mockCommandPanelHandler, "2");

            verify(mockAppHandler, times(1)).dropItem(1);
        }
    }

    @Test
    void handleInput_InvalidInput_ShowsErrorMessage() {
        when(mockCommandPanelHandler.getCommandPanel()).thenReturn(mockCommandPanel);

        itemActionState.handleInput(mockCommandPanelHandler, "invalid");

        verify(mockCommandPanel, times(1)).showSystemMessage(contains("Invalid input"));
    }

    @Test
    void display_ShowsAvailableActions() {
        itemActionState.display(mockCommandPanel);

        verify(mockCommandPanel, times(1)).showSystemMessage(contains("You have selected an item from your backpack."));
        verify(mockCommandPanel, times(1)).showSystemMessage(contains("1. Look closer"));
        verify(mockCommandPanel, times(1)).showSystemMessage(contains("2. Drop Item"));
        verify(mockCommandPanel, times(1)).showSystemMessage(contains("Enter your choice or type 'back' to return to"));
    }
}
