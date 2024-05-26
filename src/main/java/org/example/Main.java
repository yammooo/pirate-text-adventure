package org.example;

import org.example.facade.AppFacade;

public class Main {
    public static void main(String[] args) {
        // Instantiate the facade
        AppFacade facade = new AppFacade();

        // Start the application
        facade.start();
    }
}