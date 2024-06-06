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

        // Initialize panels
        commandPanel = new CommandPanel();
        graphicsPanel = new GraphicsPanel();

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {

                // Set layout manager
                setLayout(new BorderLayout());

                // Add panels to the frame
                add(graphicsPanel, BorderLayout.NORTH);
                add(commandPanel, BorderLayout.CENTER);

                pack();

                // JFrame setup
                setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                setResizable(true);
                setTitle("Pirate Text Adventure");
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