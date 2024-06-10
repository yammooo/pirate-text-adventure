package org.example.util;

import org.example.exceptions.AWSException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class AWSHandlerTest {

    private AWSHandler awsHandler = AWSHandler.getInstance();

    @Test
    void countSavedGames() {
        try {
            int result = awsHandler.countSavedGames();
            assertTrue(result >= -1);
        } catch (AWSException e) {
            fail("Exception should not be thrown");
        }
    }

    @Test
    void loadFromS3() {
        try {
            ArrayList<String> result = awsHandler.loadFromS3();
        } catch (AWSException e) {
            fail("Exception should not be thrown");
        }
    }

    @Test
    void getGamesTitles() {
        try {
            ArrayList<String> result = awsHandler.getGamesTitles();
        } catch (AWSException e) {
            fail("Exception should not be thrown");
        }
    }

    @Test
    void saveAnExistingGame() {
        assertThrows(AWSException.class, () -> awsHandler.saveAnExistingGame("TEST", "example-4702d0f6-3ad8-4a1f-9b8f-1b1f99a61859"));
    }

    @Test
    void deleteGame() {
        assertThrows(AWSException.class, () -> awsHandler.deleteGame("example-4702d0f6-3ad8-4a1f-9b8f-1b1f99a61859"));
    }
}