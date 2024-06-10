package org.example.exceptions;

/**
 * BackpackWeightExceededException is a custom exception that is thrown when the weight limit
 * of the backpack is exceeded.
 */
public class BackpackWeightExceededException extends Exception {

    /**
     * Constructs a new BackpackWeightExceededException with the specified message.
     *
     * @param message the exception's message
     */
    public BackpackWeightExceededException(String message) {
        super(message);
    }
}
