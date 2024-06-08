package org.example.state;

import org.example.model.AppHandler;
import org.example.model.entities.*;
import org.example.view.handlers.CommandPanelHandler;
import org.example.view.panels.CommandPanel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import static org.mockito.Mockito.*;

class EntitySelectStateTest {

    @Mock
    private AppHandler mockAppHandler;

    @Mock
    private CommandPanelHandler mockCommandPanelHandler;

    @Mock
    private CommandPanel mockCommandPanel;

    private EntitySelectState entitySelectState;

    @BeforeEach
    void setUp() {
        mockAppHandler = mock(AppHandler.class);
        mockCommandPanelHandler = mock(CommandPanelHandler.class);
        mockCommandPanel = mock(CommandPanel.class);

        when(mockCommandPanelHandler.getCommandPanel()).thenReturn(mockCommandPanel);
    }

    @Test
    void handleInput_BackCommand_ChangesStateToSurroundingsState() {
        Entity mockEntity = mock(Entity.class);
        entitySelectState = new EntitySelectState(mockEntity);

        entitySelectState.handleInput(mockCommandPanelHandler, "back");
        verify(mockCommandPanelHandler, times(1)).setState(any(SurroundingsState.class));
    }

    @Test
    void handleInput_ValidInputForCollectableItem_ShowsDescriptionAndChangesState() {
        try (MockedStatic<AppHandler> mockedAppHandler = Mockito.mockStatic(AppHandler.class)) {
            CollectableItem mockItem = mock(CollectableItem.class);
            when(mockItem.getDescription()).thenReturn("MockItem Description");

            entitySelectState = new EntitySelectState(mockItem);
            mockedAppHandler.when(AppHandler::getInstance).thenReturn(mockAppHandler);

            entitySelectState.handleInput(mockCommandPanelHandler, "1");

            verify(mockCommandPanel, times(1)).showSystemMessage(contains("MockItem Description"));
            verify(mockCommandPanelHandler, times(1)).setState(any(InitGameState.class));
        }
    }

    @Test
    void handleInput_PickUpCollectableItem_InvokesAppHandlerMethod() {
        try (MockedStatic<AppHandler> mockedAppHandler = Mockito.mockStatic(AppHandler.class)) {
            CollectableItem mockItem = mock(CollectableItem.class);
            when(mockItem.getID()).thenReturn(1);

            entitySelectState = new EntitySelectState(mockItem);
            mockedAppHandler.when(AppHandler::getInstance).thenReturn(mockAppHandler);

            entitySelectState.handleInput(mockCommandPanelHandler, "2");

            verify(mockAppHandler, times(1)).pickUpItem(1);
        }
    }

    @Test
    void handleInput_InvalidInputForCollectableItem_ShowsErrorMessage() {
        CollectableItem mockItem = mock(CollectableItem.class);
        entitySelectState = new EntitySelectState(mockItem);

        entitySelectState.handleInput(mockCommandPanelHandler, "invalid");

        verify(mockCommandPanel, times(1)).showSystemMessage(contains("Invalid input"));
    }

    @Test
    void handleInput_ValidInputForNPC_ShowsDescriptionAndChangesState() {
        NPC mockNpc = mock(NPC.class);
        when(mockNpc.getDescription()).thenReturn("MockNPC Description");

        entitySelectState = new EntitySelectState(mockNpc);

        entitySelectState.handleInput(mockCommandPanelHandler, "1");

        verify(mockCommandPanel, times(1)).showSystemMessage(contains("MockNPC Description"));
        verify(mockCommandPanelHandler, times(1)).setState(any(InitGameState.class));
    }

    @Test
    void handleInput_TalkToNPC_ShowsDialogueAndChangesState() {
        NPC mockNpc = mock(NPC.class);
        when(mockNpc.getDialogue()).thenReturn("MockNPC Dialogue");

        entitySelectState = new EntitySelectState(mockNpc);

        entitySelectState.handleInput(mockCommandPanelHandler, "2");

        verify(mockCommandPanel, times(1)).showSystemMessage(contains("MockNPC Dialogue"));
        verify(mockCommandPanelHandler, times(1)).setState(any(InitGameState.class));
    }

    @Test
    void handleInput_InvalidInputForNPC_ShowsErrorMessage() {
        NPC mockNpc = mock(NPC.class);
        entitySelectState = new EntitySelectState(mockNpc);

        entitySelectState.handleInput(mockCommandPanelHandler, "invalid");

        verify(mockCommandPanel, times(1)).showSystemMessage(contains("Invalid input"));
    }

    @Test
    void handleInput_ValidInputForViewableItem_ShowsDescriptionAndChangesState() {
        ViewableItem mockViewItem = mock(ViewableItem.class);
        when(mockViewItem.getDescription()).thenReturn("MockViewItem Description");

        entitySelectState = new EntitySelectState(mockViewItem);

        entitySelectState.handleInput(mockCommandPanelHandler, "1");

        verify(mockCommandPanel, times(1)).showSystemMessage(contains("MockViewItem Description"));
        verify(mockCommandPanelHandler, times(1)).setState(any(InitGameState.class));
    }

    @Test
    void handleInput_InvalidInputForViewableItem_ShowsErrorMessage() {
        ViewableItem mockViewItem = mock(ViewableItem.class);
        entitySelectState = new EntitySelectState(mockViewItem);

        entitySelectState.handleInput(mockCommandPanelHandler, "invalid");

        verify(mockCommandPanel, times(1)).showSystemMessage(contains("Invalid input"));
    }

    @Test
    void display_ShowsCorrectMessageForCollectableItem() {
        CollectableItem mockItem = mock(CollectableItem.class);
        when(mockItem.getName()).thenReturn("MockItem");

        entitySelectState = new EntitySelectState(mockItem);

        entitySelectState.display(mockCommandPanel);

        verify(mockCommandPanel, times(1)).showSystemMessage(contains("You selected: MockItem"));
        verify(mockCommandPanel, times(1)).showSystemMessage(contains("1. Look closer"));
        verify(mockCommandPanel, times(1)).showSystemMessage(contains("2. Pick up item"));
    }

    @Test
    void display_ShowsCorrectMessageForNPC() {
        NPC mockNpc = mock(NPC.class);
        when(mockNpc.getName()).thenReturn("MockNPC");

        entitySelectState = new EntitySelectState(mockNpc);

        entitySelectState.display(mockCommandPanel);

        verify(mockCommandPanel, times(1)).showSystemMessage(contains("You selected: MockNPC"));
        verify(mockCommandPanel, times(1)).showSystemMessage(contains("1. Look closer"));
        verify(mockCommandPanel, times(1)).showSystemMessage(contains("2. Talk to NPC"));
    }

    @Test
    void display_ShowsCorrectMessageForViewableItem() {
        ViewableItem mockViewItem = mock(ViewableItem.class);
        when(mockViewItem.getName()).thenReturn("MockViewItem");

        entitySelectState = new EntitySelectState(mockViewItem);

        entitySelectState.display(mockCommandPanel);

        verify(mockCommandPanel, times(1)).showSystemMessage(contains("You selected: MockViewItem"));
        verify(mockCommandPanel, times(1)).showSystemMessage(contains("1. Look closer"));
    }
}
