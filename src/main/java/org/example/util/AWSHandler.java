package org.example.util;

import org.example.observer.Observable;
import org.example.observer.Observer;

public class AWSHandler implements Observer {

    private static AWSHandler instance = null;

    private AWSHandler() {}

    public static AWSHandler getInstance() {
        if (instance == null) {
            instance = new AWSHandler();
        }
        return instance;
    }

    @Override
    public void update() {
        // TODO
    }

    public void saveToS3(String data) {
        // TODO
    }

    public String loadFromS3() {
        // TODO
        return null;
    }

    public void jsonToObject() {
        // TODO
    }
}
