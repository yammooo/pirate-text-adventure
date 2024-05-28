package org.example.view.panels;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;

import org.example.observer.Observer;
import org.example.view.handlers.CommandPanelHandler;

public class CommandPanel extends JPanel implements Observer {
    private JTextArea displayArea;
//    private JTextField inputField;
    private Timer timer;
    private String currentMessage;
    private int currentCharIndex;
    private java.util.Queue<String> messageQueue = new LinkedList<>();
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
        //add(displayArea, BorderLayout.CENTER);

        JScrollPane scrollPane = new JScrollPane(displayArea);
            scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);

        add(scrollPane, BorderLayout.CENTER);

        JTextField inputField = new JTextField();
        inputField.setFont(new Font("Monospaced", Font.PLAIN, 14));
        inputField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String userInput = inputField.getText();
                inputField.setText("");
                showMessage("User\t> " + userInput + "\n");
                commandPanelHandler.handleCommand(userInput);
            }
        });
        add(inputField, BorderLayout.SOUTH);
    }

    @Override
    public void update() {
        // TODO
    }

    public void setHandler(CommandPanelHandler handler) {
        commandPanelHandler = handler;
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