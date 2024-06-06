package org.example.facade;

import org.example.model.AppHandler;
import org.example.util.AWSHandler;
import org.example.view.MainFrame;

public class AppFacade {

    private MainFrame mainFrame;
    private AWSHandler awsHandler;
    private AppHandler appHandler;

    public void start() {
        mainFrame = new MainFrame();
        awsHandler = AWSHandler.getInstance();
        appHandler = AppHandler.getInstance();

        appHandler.addObserver(awsHandler);
        appHandler.addObserver(mainFrame.getCommandPanel().getCommandPanelHandler());
        appHandler.addObserver(mainFrame.getGraphicsPanel());
    }
}