package org.example.observer;

import org.example.util.Event;

public interface Observable {
    void addObserver(Observer observer);
    void removeObserver(Observer observer);
    void notifyObservers();
    void notifyObservers(Event event);
}