package org.example.view;

import org.example.view.panels.CommandPanel;
import org.example.view.panels.GraphicsPanel;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame{
    private final CommandPanel commandPanel;
    private final GraphicsPanel graphicsPanel;

    public MainFrame() {

        // Initialize panels
        commandPanel = new CommandPanel();
        graphicsPanel = new GraphicsPanel();

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                // Get screen size
                Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
                int frameHeight = (int) (screenSize.height * 0.8);
                int commandPanelHeight = commandPanel.getPreferredSize().height;
                int graphicsPanelSize = frameHeight - commandPanelHeight;

                // Set layout manager
                setLayout(new BorderLayout());

                // GraphicsPanel setup
                graphicsPanel.setPreferredSize(new Dimension(graphicsPanelSize, graphicsPanelSize));
                add(graphicsPanel, BorderLayout.CENTER);

                // CommandPanel setup
                add(commandPanel, BorderLayout.SOUTH);

                // Set frame size
                int frameWidth = graphicsPanelSize;
                setSize(new Dimension(frameWidth, frameHeight));
                setResizable(false);

                // Set frame parameters
                setTitle("Pirate Text Adventure");
                setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                setLocationRelativeTo(null);
                setVisible(true);
            }
        });
    }

    public CommandPanel getCommandPanel() {
        return this.commandPanel;
    }
    public GraphicsPanel getGraphicsPanel() {
        return this.graphicsPanel;
    }
}