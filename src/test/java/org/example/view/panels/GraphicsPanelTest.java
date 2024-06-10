package org.example.view.panels;

import org.example.model.AppState;
import org.example.model.GameState;
import org.example.model.entities.Backpack;
import org.example.model.entities.Map;
import org.example.model.entities.Pirate;
import org.example.model.entities.enums.WindowState;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


public class GraphicsPanelTest {

    private GraphicsPanel graphicsPanel;
    private AppState appStateMock;
    private GameState gameStateMock;
    private Pirate pirateMock;
    private Map mapMock;
    private Backpack backpackMock;
    private MockedStatic<AppState> mockedAppState;

    @BeforeEach
    public void setUp() {
        graphicsPanel = new GraphicsPanel();

        // Mock the AppState and related classes
        appStateMock = mock(AppState.class);
        gameStateMock = mock(GameState.class);
        pirateMock = mock(Pirate.class);
        mapMock = mock(Map.class);
        backpackMock = mock(Backpack.class);

        mockedAppState = mockStatic(AppState.class);
        mockedAppState.when(AppState::getInstance).thenReturn(appStateMock);
        when(appStateMock.getGameState()).thenReturn(gameStateMock);
        when(gameStateMock.getPirate()).thenReturn(pirateMock);
        when(gameStateMock.getMap()).thenReturn(mapMock);
        when(pirateMock.getBackpack()).thenReturn(backpackMock);
    }

    @AfterEach
    public void tearDown() {
        // Close the static mock to deregister it
        mockedAppState.close();
    }

    @Test
    public void constructorTest() {
        assertNotNull(graphicsPanel);
    }

    @Test
    public void updateTest() {
        GraphicsPanel spyPanel = spy(graphicsPanel);
        spyPanel.update();
        verify(spyPanel).repaint();
    }

    @Test
    public void setBackgroundImageTest() {
        graphicsPanel.setBackgroundImage(null);
        graphicsPanel.setBackgroundImage("src/main/resources/assets/images/test-image.png");
        // Use reflection to access the private backgroundImage field
        try {
            Field backgroundImageField = GraphicsPanel.class.getDeclaredField("backgroundImage");
            backgroundImageField.setAccessible(true);
            Image backgroundImage = (Image) backgroundImageField.get(graphicsPanel);
            assertNotNull(backgroundImage);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            fail("Exception occurred while accessing backgroundImage field: " + e.getMessage());
        }
    }

    @Test
    public void paintComponentTest() {
        // Setup mock AppState and WindowState
        when(appStateMock.getCurrentWindow()).thenReturn(WindowState.GAME);
        when(mapMock.getPirateLocationID()).thenReturn(1);
        when(pirateMock.getCurrentLives()).thenReturn(1);
        when(backpackMock.getMaxWeight()).thenReturn(1);
        when(backpackMock.getTotalWeight()).thenReturn(1);

        BufferedImage bufferedImage = new BufferedImage(100, 100, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = bufferedImage.createGraphics();

        graphicsPanel.paintComponent(g2d);

        // Verify the correct methods were called
        verify(appStateMock).getCurrentWindow();
        verify(appStateMock.getGameState()).getMap();
        verify(mapMock).getPirateLocationID();
        verify(pirateMock).getCurrentLives();
        verify(backpackMock).getMaxWeight();
        verify(backpackMock).getTotalWeight();

        g2d.dispose();
    }

    // Additional tests for displayMenu, displayGameOver, displayGameWin, displayGame, drawHearts, and drawBackpackStatus methods

}
