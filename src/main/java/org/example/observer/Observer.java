package org.example.observer;

import org.example.exceptions.AWSException;

public interface Observer {
    void update() throws AWSException;
}