package org.example.view;

import org.example.model.AppHandler;
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

                // Set layout manager
                setLayout(new BorderLayout());


                // Add panels to the frame
                add(graphicsPanel, BorderLayout.NORTH);
                add(commandPanel, BorderLayout.CENTER);

                // Initialize controllers with references to their panels
                CommandPanelHandler commandPanelHandler = new CommandPanelHandler(commandPanel);
                AppHandler.getInstance().addObserver(commandPanelHandler);

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
        });
    }

    public CommandPanel getCommandPanel() {
        return commandPanel;
    }
}