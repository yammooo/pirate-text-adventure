package org.example.view.panels;

import org.example.view.handlers.CommandPanelHandler;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame{
    private CommandPanel commandPanel;
    private GraphicsPanel graphicsPanel;

    public MainFrame() {

        // Initialize panels
        this.commandPanel = new CommandPanel();
        this.graphicsPanel = new GraphicsPanel();

        // Add panels to the frame
        add(graphicsPanel, BorderLayout.NORTH);
        add(commandPanel, BorderLayout.SOUTH);

        // Initialize controllers with references to their panels
        CommandPanelHandler commandPanelHandler = new CommandPanelHandler(commandPanel);

        // Set controllers to panels
        commandPanel.setHandler(commandPanelHandler);

        pack();

        // JFrame setup
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setTitle("2D Game");
        setLocationRelativeTo(null);
        setVisible(true);
    }
}