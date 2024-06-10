package org.example.exceptions;

/**
 * AWSException is a custom exception that is thrown when an error occurs
 * related to AWS operations.
 */
public class AWSException extends Exception {

    /**
     * Constructs a new AWSException with the specified detail message.
     *
     * @param message the detail message
     */
    public AWSException(String message) {
        super(message);
    }
}
