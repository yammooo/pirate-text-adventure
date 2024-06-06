package org.example.model.entities;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CollectableItemTest {

    private CollectableItem collectableItem;

    @BeforeEach
    void setUp() {
        collectableItem = new CollectableItem(1, "Item1", "Description1", 10, 2);
    }

    @Test
    void getIDReturnsCorrectID() {
        assertEquals(1, collectableItem.getID());
    }

    @Test
    void getNameReturnsCorrectName() {
        assertEquals("Item1", collectableItem.getName());
    }

    @Test
    void getDescriptionReturnsCorrectDescription() {
        assertEquals("Description1", collectableItem.getDescription());
    }

    @Test
    void getWeightReturnsCorrectWeight() {
        assertEquals(10, collectableItem.getWeight());
    }

    @Test
    void getRequiredEntityIDReturnsCorrectID() {
        assertEquals(2, collectableItem.getRequiredEntityID());
    }
}