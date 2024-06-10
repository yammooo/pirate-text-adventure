package org.example.facade;

import org.example.model.AppHandler;
import org.example.util.AWSHandler;
import org.example.view.MainFrame;

/**
 * AppFacade is a facade class responsible for initializing and coordinating the main components of the game.
 * This class is responsible for creating the main frame, instantiating application handlers, and establishing
 * connections between them and the view components.
 */
public class AppFacade {

    private MainFrame mainFrame;
    private AWSHandler awsHandler;
    private AppHandler appHandler;

    /**
     * Initializes the application by creating the main frame, instantiating application handlers, and establishing
     * their relationships with view components.
     */
    public void start() {
        // Creating the main frame
        mainFrame = new MainFrame();

        // Initializing application handlers
        awsHandler = AWSHandler.getInstance();
        appHandler = AppHandler.getInstance();

        // Adding observers to application handlers
        appHandler.addObserver(awsHandler);
        appHandler.addObserver(mainFrame.getCommandPanel().getCommandPanelHandler());
        appHandler.addObserver(mainFrame.getGraphicsPanel());
    }
}
