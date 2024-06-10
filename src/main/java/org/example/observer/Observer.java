package org.example.observer;

import org.example.exceptions.AWSException;

/**
 * The Observer interface defines a method to be called when an observable object is updated.
 */
public interface Observer {
    /**
     * This method is called when the observed object is changed.
     *
     * @throws AWSException if an error occurs while updating
     */
    void update() throws AWSException;
}