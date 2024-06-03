package org.example.view;

import org.example.view.handlers.CommandPanelHandler;
import org.example.view.panels.CommandPanel;
import org.example.view.panels.GraphicsPanel;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame{
    private CommandPanel commandPanel;
    private GraphicsPanel graphicsPanel;

    public MainFrame() {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                // Initialize panels
                commandPanel = new CommandPanel();
                graphicsPanel = new GraphicsPanel();

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
                setResizable(true);
                setTitle("2D Game");
                setLocationRelativeTo(null);
                setVisible(true);
            }
        });
    }

    public CommandPanel getCommandPanel() {
        return commandPanel;
    }
}