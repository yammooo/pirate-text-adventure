package org.example.view.panels;

import org.example.model.AppState;
import org.example.model.entities.enums.WindowState;
import org.example.observer.Observer;

import javax.swing.*;
import java.awt.*;

/**
 * It is responsible for displaying the graphical elements of the game, including
 * backgrounds, game states, and pirate images.
 */
public class GraphicsPanel extends JPanel implements Observer {
    private Image backgroundImage;
    private final Image heartImage = new ImageIcon("src/main/resources/assets/images/heart.png").getImage();
    private final Image backpackImage = new ImageIcon("src/main/resources/assets/images/backpack.png").getImage();
    private final Image pirateImage = new ImageIcon("src/main/resources/assets/images/pirate.gif").getImage();

    /**
     * Constructs a new graphics panel and initializes its properties.
     */
    public GraphicsPanel() {
        setBackground(Color.BLACK);
        setDoubleBuffered(true);
        setFocusable(true);

        setBackgroundImage("src/main/resources/assets/images/menu-background-image.png");
    }

    /**
     * Called when the observed object is changed. This method is used to
     * trigger a repaint of the panel.
     */
    @Override
    public void update() {
        repaint();
    }

    /**
     * Returns the preferred size of the panel. The size is adjusted to
     * ensure the panel is always square.
     * @return the preferred size as a {@code Dimension}
     */
    @Override
    public Dimension getPreferredSize() {
        Dimension size = super.getPreferredSize();
        int newSize = Math.min(size.width, size.height);
        return new Dimension(newSize, newSize);
    }

    /**
     * Sets the background image for the panel.
     * @param imagePath the path to the image file
     */
    public void setBackgroundImage(String imagePath) {
        backgroundImage = new ImageIcon(imagePath).getImage();
    }

    /**
     * Displays the menu background image.
     * @param g2 the {@code Graphics2D} context in which to paint
     */
    public void displayMenu(Graphics2D g2) {
        setBackgroundImage("src/main/resources/assets/images/menu-background-image.png");

        if (backgroundImage != null) {
            g2.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        }
    }

    /**
     * Displays the game over background image.
     * @param g2 the {@code Graphics2D} context in which to paint
     */
    public void displayGameOver(Graphics2D g2) {
        setBackgroundImage("src/main/resources/assets/images/game-over.gif");
        g2.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
    }

    /**
     * Displays the game win background image.
     * @param g2 the {@code Graphics2D} context in which to paint
     */
    public void displayGameWin(Graphics2D g2) {
        setBackgroundImage("src/main/resources/assets/images/game-win.gif");
        g2.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
    }

    /**
     * Displays the game view, including the pirate's current location and status.
     * @param g2 the {@code Graphics2D} context in which to paint
     */
    public void displayGame(Graphics2D g2) {
        // Select image of the pirate's current location
        switch (AppState.getInstance().getGameState().getMap().getPirateLocationID()) {
            case 11:
                setBackgroundImage("src/main/resources/assets/images/third-island.png");
                g2.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
                g2.drawImage(pirateImage, 220 * getWidth() / 576, 205 * getHeight() / 576, 175, 175, this);
                break;
            case 20:
                setBackgroundImage("src/main/resources/assets/images/third-island.png");
                g2.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
                g2.drawImage(pirateImage, 325 * getWidth() / 576, 330 * getHeight() / 576, 175, 175, this);
                break;
            case 23:
                setBackgroundImage("src/main/resources/assets/images/third-island.png");
                g2.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
                g2.drawImage(pirateImage, 125 * getWidth() / 576, 280 * getHeight() / 576, 175, 175, this);
                break;
            case 26:
                setBackgroundImage("src/main/resources/assets/images/second-island.png");
                g2.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
                g2.drawImage(pirateImage, 350 * getWidth() / 576, 40 * getHeight() / 576, 175, 175, this);
                break;
            case 25:
                setBackgroundImage("src/main/resources/assets/images/second-island.png");
                g2.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
                g2.drawImage(pirateImage, 170 * getWidth() / 576, 80 * getHeight() / 576, 175, 175, this);
                break;
            case 24:
                setBackgroundImage("src/main/resources/assets/images/second-island.png");
                g2.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
                g2.drawImage(pirateImage, 325 * getWidth() / 576, 350 * getHeight() / 576, 175, 175, this);
                break;
            case 22:
                setBackgroundImage("src/main/resources/assets/images/first-island.png");
                g2.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
                g2.drawImage(pirateImage, 210 * getWidth() / 576, 60 * getHeight() / 576, 175, 175, this);
                break;
            case 12:
                setBackgroundImage("src/main/resources/assets/images/first-island.png");
                g2.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
                g2.drawImage(pirateImage, 350 * getWidth() / 576, 230 * getHeight() / 576, 175, 175, this);
                break;
            case 21:
                setBackgroundImage("src/main/resources/assets/images/first-island.png");
                g2.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
                g2.drawImage(pirateImage, 80 * getWidth() / 576, 250 * getHeight() / 576, 175, 175, this);
                break;
            default:
                setBackgroundImage("src/main/resources/assets/images/menu-background-image.png");
                g2.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
                break;
        }

        drawHearts(g2);
        drawBackpackStatus(g2);
    }

    /**
     * Draws hearts to represent the remaining lives of the pirate.
     * @param g2 the {@code Graphics2D} context in which to paint
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

    /**
     * Draws the backpack weight status to show the remaining space in the backpack.
     * @param g2 the {@code Graphics2D} context in which to paint
     */
    private void drawBackpackStatus(Graphics2D g2) {
        int maxWeight = AppState.getInstance().getGameState().getPirate().getBackpack().getMaxWeight();
        int currentWeight = AppState.getInstance().getGameState().getPirate().getBackpack().getTotalWeight();

        g2.drawImage(backpackImage, getWidth() - 155, 10, 45, 45, this);

        g2.setFont(new Font("Monospaced", Font.BOLD, 30));
        g2.setColor(Color.BLACK);
        g2.drawString(currentWeight + "/" + maxWeight, getWidth() - 100, 40);
    }

    /**
     * Paints the component based on the current game state.
     * It displays the appropriate screen (game, menu, game over, or game win).
     * @param g the {@code Graphics} context in which to paint
     */
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

