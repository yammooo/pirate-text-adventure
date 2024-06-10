package org.example.exceptions;

/**
 * RunOutOfLivesException is a custom exception that is thrown when a pirate
 * runs out of lives in the game.
 */
public class RunOutOfLivesException extends Exception {

    /**
     * Constructs a new RunOutOfLivesException with the specified message.
     *
     * @param message the detail message
     */
    public RunOutOfLivesException(String message) {
        super(message);
    }
}
