package org.example.view.panels;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;
import java.util.Queue;

import org.example.view.handlers.CommandPanelHandler;

/**
 * CommandPanel is a crucial component of the game's user interface, responsible for handling user commands
 * and displaying system messages. This panel is part of the view layer in the MVC architecture, facilitating
 * user interaction with the game through text input and output.
 *
 * <p>The upper part of the game's UI displays the graphical representation of the game state, while the lower
 * part, managed by CommandPanel, serves as the command prompt where users can input text commands to interact
 * with the game. It also displays messages from the game, providing feedback and information to the user.
 *
 * <p>The CommandPanel handles text input via a JTextField and displays messages in a JTextArea. User input is
 * processed and forwarded to the CommandPanelHandler, which manages the state and interactions based on the
 * commands received.
 *
 * <p>The CommandPanel also features a timer mechanism to handle the display of messages with a typewriter effect,
 * enhancing the user experience by animating the text output.
 */
public class CommandPanel extends JPanel {
    private final JTextArea displayArea;
    private final JTextField inputField;
    private Timer timer;
    private String currentMessage;
    private int currentCharIndex;
    private final Queue<String> messageQueue = new LinkedList<>();
    private final CommandPanelHandler commandPanelHandler;

    /**
     * Constructs a CommandPanel with initialized components for text input and output.
     * Sets up the layout and visual properties of the panel, and initializes the CommandPanelHandler.
     */
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
        scrollPane.setPreferredSize(new Dimension(0, 300)); // Fixed height for displayArea
        add(scrollPane, BorderLayout.CENTER);

        inputField = new JTextField();
        inputField.setFont(new Font("Monospaced", Font.PLAIN, 16));
        inputField.setBackground(Color.BLACK);
        inputField.setForeground(Color.WHITE);
        inputField.setCaretColor(Color.WHITE);
        inputField.setBorder(BorderFactory.createLineBorder(Color.WHITE));
        inputField.setPreferredSize(new Dimension(0, 30)); // Fixed height for inputField
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

    /**
     * Displays a system message in the display area with indentation.
     *
     * @param message The system message to display.
     */
    public void showSystemMessage(String message) {
        // Indent system messages
        String indentedMessage = "Game >\t" + message.replaceAll("\n", "\n\t");
        showMessage(indentedMessage + "\n");
    }

    /**
     This method is responsible for creating a self-writing text animation effect.
     It accepts a message as input and adds it to a queue of messages to be displayed.

     On each tick of the timer, it appends the next character from the current message to the display area.
     If all characters of the current message have been displayed, the method retrieves the next message from the queue.
     If there are no more messages in the queue, it stops the timer.

     @param message The message to be displayed with the self-writing text animation. */
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

    /**
     * Returns the CommandPanelHandler associated with this panel.
     *
     * @return The CommandPanelHandler instance.
     */
    public CommandPanelHandler getCommandPanelHandler() {
        return this.commandPanelHandler;
    }
}