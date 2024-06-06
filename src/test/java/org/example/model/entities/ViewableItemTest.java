package org.example.model.entities;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ViewableItemTest {

    private ViewableItem viewableItem;

    @BeforeEach
    void setUp() {
        viewableItem = new ViewableItem(1, "Item1", "Description1");
    }

    @Test
    void getIDReturnsCorrectID() {
        assertEquals(1, viewableItem.getID());
    }

    @Test
    void getNameReturnsCorrectName() {
        assertEquals("Item1", viewableItem.getName());
    }

    @Test
    void getDescriptionReturnsCorrectDescription() {
        assertEquals("Description1", viewableItem.getDescription());
    }
}