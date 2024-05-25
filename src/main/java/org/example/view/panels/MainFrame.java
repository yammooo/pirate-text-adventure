package org.example.view.panels;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame{
    private CommandPanel commandPanel;
    private GraphicsPanel graphicsPanel;
    private MenuPanel menuPanel;

    public MainFrame() {
        this.commandPanel = new CommandPanel();
        this.graphicsPanel = new GraphicsPanel();
        this.menuPanel = new MenuPanel();

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setTitle("2D Game");

        add(menuPanel, BorderLayout.NORTH);
        add(commandPanel, BorderLayout.SOUTH);

        pack();

        setLocationRelativeTo(null);
        setVisible(true);
    }

    public void switchToMenu() {
        // TODO
    }

    public void switchToGame() {
        // TODO
    }
}