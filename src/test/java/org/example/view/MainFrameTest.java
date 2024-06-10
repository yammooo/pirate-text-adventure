package org.example.view;

import org.example.view.panels.CommandPanel;
import org.example.view.panels.GraphicsPanel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.swing.*;
import java.awt.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

public class MainFrameTest {

    private MainFrame mainFrame;
    private CommandPanel commandPanelMock;
    private GraphicsPanel graphicsPanelMock;

    @BeforeEach
    public void setUp() {
        // Mock the panels
        commandPanelMock = mock(CommandPanel.class);
        graphicsPanelMock = mock(GraphicsPanel.class);

        // Use reflection to inject the mocks into the MainFrame instance
        mainFrame = new MainFrame() {
            {
                // Inject mocks
                CommandPanel commandPanel = commandPanelMock;
                GraphicsPanel graphicsPanel = graphicsPanelMock;
            }
        };
    }

    @Test
    public void mainFrameInitializationTest() {
        SwingUtilities.invokeLater(() -> {
            assertNotNull(mainFrame.getCommandPanel());
            assertNotNull(mainFrame.getGraphicsPanel());
            assertEquals(commandPanelMock, mainFrame.getCommandPanel());
            assertEquals(graphicsPanelMock, mainFrame.getGraphicsPanel());

            // Ensure the JFrame is set up correctly
            assertEquals("Pirate Text Adventure", mainFrame.getTitle());
            assertEquals(JFrame.EXIT_ON_CLOSE, mainFrame.getDefaultCloseOperation());
            assertEquals(false, mainFrame.isResizable());

            // Verify layout and component addition
            LayoutManager layout = mainFrame.getLayout();
            assertNotNull(layout);
            assertEquals(BorderLayout.class, layout.getClass());

            // Check if the components are added
            verify(commandPanelMock, atLeastOnce()).getPreferredSize();
            verify(graphicsPanelMock, atLeastOnce()).setPreferredSize(any(Dimension.class));
        });
    }
}
