package org.example.view.panels;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;
import java.util.Queue;

import org.example.model.AppHandler;
import org.example.model.entities.enums.WindowState;
import org.example.observer.Observer;
import org.example.state.MainMenuState;
import org.example.state.ShowLocationState;
import org.example.view.handlers.CommandPanelHandler;

public class CommandPanel extends JPanel implements Observer {
    private final JTextArea displayArea;
    private final JTextField inputField;
    private Timer timer;
    private String currentMessage;
    private int currentCharIndex;
    private final Queue<String> messageQueue = new LinkedList<>();
    private CommandPanelHandler commandPanelHandler;

    public CommandPanel() {
        setLayout(new BorderLayout());

        displayArea = new JTextArea(10, 30);
        displayArea.setEditable(false);
        displayArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
        displayArea.setBackground(Color.BLACK);
        displayArea.setForeground(Color.GREEN);
        displayArea.setWrapStyleWord(true);
        displayArea.setLineWrap(true);

        JScrollPane scrollPane = new JScrollPane(displayArea);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
        add(scrollPane, BorderLayout.CENTER);

        inputField = new JTextField();
        inputField.setFont(new Font("Monospaced", Font.PLAIN, 14));
        inputField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String userInput = inputField.getText();
                inputField.setText("");
                showMessage("User\t> " + userInput + "\n");
                commandPanelHandler.handleUserInput(userInput);
            }
        });
        add(inputField, BorderLayout.SOUTH);

        AppHandler.getInstance().addObserver(this);
    }

    @Override
    public void update() {
        WindowState currentState = AppHandler.getInstance().getAppState().getCurrentWindow();

        if (currentState == WindowState.MENU) {
            commandPanelHandler.setState(new MainMenuState());
        } else if (currentState == WindowState.GAME) {
            commandPanelHandler.setState(new ShowLocationState());
        }
    }

    public void setHandler(CommandPanelHandler handler) {
        this.commandPanelHandler = handler;
    }

    public void showSystemMessage(String message) {
        showMessage("System\t> " + message + "\n");
    }

    private void showMessage(String message) {
        messageQueue.add(message);
        if (timer == null || !timer.isRunning()) {
            currentMessage = messageQueue.poll();
            currentCharIndex = 0;
            if (timer == null) {
                timer = new Timer(5, new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        if (currentCharIndex < currentMessage.length()) {
                            displayArea.append(String.valueOf(currentMessage.charAt(currentCharIndex)));
                            currentCharIndex++;
                        } else {
                            currentMessage = messageQueue.poll();
                            if (currentMessage == null) {
                                timer.stop();
                            } else {
                                currentCharIndex = 0;
                            }
                        }
                    }
                });
            }
            timer.start();
        }
    }
}
