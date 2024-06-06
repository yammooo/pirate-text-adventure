package org.example.model.entities;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ObstacleTest {

    private Obstacle obstacle;

    @BeforeEach
    void setUp() {
        obstacle = new Obstacle(1, "Obstacle1", "Description1", 2);
    }

    @Test
    void getIDReturnsCorrectID() {
        assertEquals(1, obstacle.getID());
    }

    @Test
    void getNameReturnsCorrectName() {
        assertEquals("Obstacle1", obstacle.getName());
    }

    @Test
    void getDescriptionReturnsCorrectDescription() {
        assertEquals("Description1", obstacle.getDescription());
    }

    @Test
    void getItemToDefeatIDReturnsCorrectID() {
        assertEquals(2, obstacle.getItemToDefeatID());
    }
}