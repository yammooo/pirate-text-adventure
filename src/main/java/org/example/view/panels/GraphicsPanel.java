package org.example.view.panels;

import org.example.observer.Observer;

import javax.swing.*;
import java.awt.*;

public class GraphicsPanel extends JPanel implements Observer {
    private Image image;

    // Screen settings
    final int originalTileSize = 16; // 16x16 pixels
    final int scale = 3;

    public final int tileSize = originalTileSize * scale; // 48x48 pixels
    final int maxScreenCols = 12;
    final int maxScreenRows = 12;
    final int screenWidth = tileSize * maxScreenCols; // 576 pixels
    final int screenHeight = tileSize * maxScreenRows; // 576 pixels

    public GraphicsPanel() {
        // Panel setup

        setPreferredSize(new Dimension(screenWidth, screenHeight));
        setBackground(Color.BLACK);
        setDoubleBuffered(true);
        setFocusable(true);

        setImage("C:\\Repos\\pirate-text-adventure\\src\\main\\resources\\assets\\images\\menu-background-image.png");

    }

    @Override
    public void update() {
        repaint();
    }

    public void draw() {
        // Drawing logic
    }

    public void setImage(String imagePath) {
        image = new ImageIcon(imagePath).getImage();
    }

    public void displayMenu(Graphics2D g2) {
        if (image != null) {
            g2.drawImage(image, 0, 0, getWidth(), getHeight(), this);
        }
    }

    public void displayGame(Graphics2D g2) {
        // TODO: Implement game display logic
    }

    @Override
    protected void paintComponent(Graphics g) {

        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;

        // TODO: Implement paintComponent logic
        if (false) {
            // Draw the game
            displayGame(g2);
        } else {
            // Draw the menu
            displayMenu(g2);
        }

        g2.dispose();
    }
}