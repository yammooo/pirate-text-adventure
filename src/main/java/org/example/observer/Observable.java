package org.example.observer;

import org.example.util.Event;

/**
 * The Observable interface defines methods for managing observers that are
 * interested in changes to the observable object.
 */
public interface Observable {
    /**
     * Adds an observer to the list of observers.
     *
     * @param observer the observer to be added
     */
    void addObserver(Observer observer);

    /**
     * Removes an observer from the list of observers.
     *
     * @param observer the observer to be removed
     */
    void removeObserver(Observer observer);

    /**
     * Notifies all observers about an event.
     *
     * @param event the event to be notified to observers
     */
    void notifyObservers(Event event);
}