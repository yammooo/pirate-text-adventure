package org.example.view.panels;

import org.example.model.AppState;
import org.example.model.entities.enums.WindowState;
import org.example.observer.Observer;

import javax.swing.*;
import java.awt.*;

public class GraphicsPanel extends JPanel implements Observer {
    private Image backgroundImage;
    private final Image heartImage = new ImageIcon("src/main/resources/assets/images/heart.png").getImage();
    private final Image backpackImage = new ImageIcon("src/main/resources/assets/images/backpack.png").getImage();
    private final Image pirateImage = new ImageIcon("src/main/resources/assets/images/pirate.gif").getImage();


    public GraphicsPanel() {
        // Panel setup

        setBackground(Color.BLACK);
        setDoubleBuffered(true);
        setFocusable(true);

        setBackgroundImage("src/main/resources/assets/images/menu-background-image.png");

//        // to repaint graphics when panel is resized
//        this.addComponentListener(new ComponentAdapter() {
//            @Override
//            public void componentResized(ComponentEvent e) {
//                repaint();
//            }
//        });
    }

    @Override
    public void update() {
        repaint();
    }

    @Override
    public Dimension getPreferredSize() {
        Dimension size = super.getPreferredSize();
        int newSize = Math.min(size.width, size.height);
        return new Dimension(newSize, newSize);
    }

    public void setBackgroundImage(String imagePath) {
        backgroundImage = new ImageIcon(imagePath).getImage();
    }

    public void displayMenu(Graphics2D g2) {
        setBackgroundImage("src/main/resources/assets/images/menu-background-image.png");

        if (backgroundImage != null) {
            g2.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        }
    }

    public void displayGameOver(Graphics2D g2) {
        setBackgroundImage("src/main/resources/assets/images/game-over.gif");
        g2.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
    }

    public void displayGameWin(Graphics2D g2) {
        setBackgroundImage("src/main/resources/assets/images/game-win.gif");
        g2.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
    }

    public void displayGame(Graphics2D g2) {

        // select image of the pirate's current location
        switch (AppState.getInstance().getGameState().getMap().getPirateLocationID()) {
            case 11: // pink tree
                setBackgroundImage("src/main/resources/assets/images/third-island.png");
                g2.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
                // pirate position
                g2.drawImage(pirateImage, 220*getWidth()/576, 205*getHeight()/576, 175, 175, this);
                break;
            case 20: // pink beach
                setBackgroundImage("src/main/resources/assets/images/third-island.png");
                g2.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
                // pirate position
                g2.drawImage(pirateImage, 325*getWidth()/576, 330*getHeight()/576, 175, 175, this);
                break;
            case 23: // abandoned house
                setBackgroundImage("src/main/resources/assets/images/third-island.png");
                g2.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
                // pirate position
                g2.drawImage(pirateImage, 125*getWidth()/576, 280*getHeight()/576, 175, 175, this);
                break;
            case 26: // dark house
                setBackgroundImage("src/main/resources/assets/images/second-island.png");
                g2.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
                // pirate position
                g2.drawImage(pirateImage, 350*getWidth()/576, 40*getHeight()/576, 175, 175, this);
                break;
            case 25: // coconut forest
                setBackgroundImage("src/main/resources/assets/images/second-island.png");
                g2.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
                // pirate position
                g2.drawImage(pirateImage, 170*getWidth()/576, 80*getHeight()/576, 175, 175, this);
                break;
            case 24: // cobblestone beach
                setBackgroundImage("src/main/resources/assets/images/second-island.png");
                g2.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
                // pirate position
                g2.drawImage(pirateImage, 325*getWidth()/576, 350*getHeight()/576, 175, 175, this);
                break;
            case 22: // volcano
                setBackgroundImage("src/main/resources/assets/images/first-island.png");
                g2.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
                // pirate position
                g2.drawImage(pirateImage, 210*getWidth()/576, 60*getHeight()/576, 175, 175, this);
                break;
            case 12: // golden beach
                setBackgroundImage("src/main/resources/assets/images/first-island.png");
                g2.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
                // pirate position
                g2.drawImage(pirateImage, 350*getWidth()/576, 230*getHeight()/576, 175, 175, this);
                break;
            case 21: // white beach
                setBackgroundImage("src/main/resources/assets/images/first-island.png");
                g2.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
                // pirate position
                g2.drawImage(pirateImage, 80*getWidth()/576, 250*getHeight()/576, 175, 175, this);
                break;
            default:
                setBackgroundImage("src/main/resources/assets/images/menu-background-image.png");
                g2.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
                break;
        }

        drawHearts(g2);
        drawBackpackStatus(g2);
    }

    /*
     * draw hearts to show the remaining lives
     */
    private void drawHearts(Graphics2D g2) {
        int remainingLives = AppState.getInstance().getGameState().getPirate().getCurrentLives();
        int x = 10;
        int y = 10;
        for (int i = 0; i < remainingLives; i++) {
            g2.drawImage(heartImage, x, y, 40, 40, this);
            x += heartImage.getWidth(this) + 5; // Add some spacing between hearts
        }
    }

    /*
     * draw backpack weight status to show the remaining space in it
     */
    private void drawBackpackStatus(Graphics2D g2) {
        int maxWeight = AppState.getInstance().getGameState().getPirate().getBackpack().getMaxWeight();
        int currentWeight = AppState.getInstance().getGameState().getPirate().getBackpack().getTotalWeight();

        g2.drawImage(backpackImage, getWidth() - 155, 10, 45, 45, this);

        g2.setFont(new Font("Monospaced", Font.BOLD, 30));
        g2.setColor(Color.BLACK);
        g2.drawString(currentWeight + "/" + maxWeight, getWidth() - 100, 40);
    }

    @Override
    protected void paintComponent(Graphics g) {

        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;

        if (AppState.getInstance().getCurrentWindow() == WindowState.GAME) {
            // Draw the game
            displayGame(g2);
        } else if (AppState.getInstance().getCurrentWindow() == WindowState.MENU) {
            // Draw the menu
            displayMenu(g2);
        } else if (AppState.getInstance().getCurrentWindow() == WindowState.GAME_OVER) {
            // Draw the Game Over
            displayGameOver(g2);
        } else if (AppState.getInstance().getCurrentWindow() == WindowState.GAME_WIN) {
            // Draw the Game Win
            displayGameWin(g2);
        }

        g2.dispose();
    }
}