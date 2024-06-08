package org.example.model.entities;

import org.example.exceptions.RunOutOfLivesException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PirateTest {

    private Pirate pirate;
    private Backpack backpack;

    @BeforeEach
    void setUp() {
        backpack = new Backpack(50); // maximum weight is set to 50
        pirate = new Pirate(backpack, 3);
    }


    @Test
    void setCurrentLivesChangesCurrentLives() throws RunOutOfLivesException {
        pirate.setCurrentLives(2);
        assertEquals(2, pirate.getCurrentLives());
    }

    @Test
    void setCurrentLivesThrowsExceptionWhenLivesAreZero() {
        assertThrows(RunOutOfLivesException.class, () -> pirate.setCurrentLives(0));
    }

    @Test
    void setCurrentLivesCapsAtMaxLives() throws RunOutOfLivesException {
        pirate.setCurrentLives(4);
        assertEquals(3, pirate.getCurrentLives());
    }
}