package org.example.view.panels;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;
import java.util.Queue;

import org.example.view.handlers.CommandPanelHandler;

public class CommandPanel extends JPanel {
    private final JTextArea displayArea;
    private final JTextField inputField;
    private Timer timer;
    private String currentMessage;
    private int currentCharIndex;
    private final Queue<String> messageQueue = new LinkedList<>();
    private final CommandPanelHandler commandPanelHandler;

    public CommandPanel() {
        setLayout(new BorderLayout());

        displayArea = new JTextArea(15, 30);
        displayArea.setEditable(false);
        displayArea.setFont(new Font("Monospaced", Font.PLAIN, 16));
        displayArea.setBackground(Color.BLACK);
        displayArea.setForeground(Color.WHITE);
        displayArea.setWrapStyleWord(true);
        displayArea.setLineWrap(true);

        JScrollPane scrollPane = new JScrollPane(displayArea);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
        add(scrollPane, BorderLayout.CENTER);

        inputField = new JTextField();
        inputField.setFont(new Font("Monospaced", Font.PLAIN, 16));
        inputField.setBackground(Color.BLACK);
        inputField.setForeground(Color.WHITE);
        inputField.setCaretColor(Color.WHITE);
        inputField.setBorder(BorderFactory.createLineBorder(Color.WHITE));
        inputField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String userInput = inputField.getText();
                inputField.setText("");
                showMessage("You >\t" + userInput + "\n\n");
                commandPanelHandler.handleUserInput(userInput);
            }
        });
        add(inputField, BorderLayout.SOUTH);

        // Initialize controllers with references to their panels
        this.commandPanelHandler = new CommandPanelHandler(this);
    }

    public void showSystemMessage(String message) {
        // Indent system messages
        String indentedMessage = "Game >\t" + message.replaceAll("\n", "\n\t");
        showMessage(indentedMessage + "\n");
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

    public CommandPanelHandler getCommandPanelHandler() {
        return this.commandPanelHandler;
    }
}
