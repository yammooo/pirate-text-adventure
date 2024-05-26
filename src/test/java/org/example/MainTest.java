package org.example;

import org.example.facade.AppFacade;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class MainTest {

    @Test
    public void testMain() {
        AppFacade facade = new AppFacade();
        assertNotNull(facade, "Facade object should not be null");

        // Call the start method and assert its behavior
        facade.start();
    }
}