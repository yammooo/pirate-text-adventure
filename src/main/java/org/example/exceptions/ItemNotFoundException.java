package org.example.exceptions;

/**
 * ItemNotFoundException is a custom exception that is thrown when an item
 * is not found in the list of items it's being searched in.
 */
public class ItemNotFoundException extends Exception {

    /**
     * Constructs a new ItemNotFoundException with the specified detail message.
     *
     * @param message the exception message
     */
    public ItemNotFoundException(String message) {
        super(message);
    }
}
