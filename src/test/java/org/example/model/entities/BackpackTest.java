package org.example.model.entities;

import org.example.exceptions.BackpackWeightExceededException;
import org.example.exceptions.ItemNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class BackpackTest {

    private Backpack backpack;
    private CollectableItem item1;
    private CollectableItem item2;
    private CollectableItem item3;

    @BeforeEach
    public void setUp() {
        backpack = new Backpack(10); // Supponiamo che il peso massimo sia 10
        item1 = new CollectableItem(100,"nome1", "descrizione1",1,0 ); // id = 1, peso = 3
        item2 = new CollectableItem(101,"nome2", "descrizione1",8,0 ); // id = 2, peso = 4
        item3 = new CollectableItem(102,"nome3", "descrizione1",5,0 ); // id = 3, peso = 5
    }

    @Test
    public void testAddItemSuccess() throws BackpackWeightExceededException {
        backpack.addItem(item1);
        assertTrue(backpack.getItems().contains(item1));
    }

    @Test
    public void testAddItemThrowsExceptionWhenOverweight() {
        assertThrows(BackpackWeightExceededException.class, () -> {
            backpack.addItem(item2); // Aggiungiamo un oggetto di peso 5
            backpack.addItem(item3); // Questo deve generare un'eccezione
        });
    }

    @Test
    public void testRemoveItemSuccess() throws ItemNotFoundException, BackpackWeightExceededException {
        backpack.addItem(item1);
        backpack.addItem(item2);
        CollectableItem removedItem = backpack.removeItem(100);
        assertEquals(item1, removedItem);
        assertFalse(backpack.getItems().contains(item1));
    }

    @Test
    public void testRemoveItemThrowsExceptionWhenItemNotFound() {
        assertThrows(ItemNotFoundException.class, () -> {
            backpack.removeItem(104); // Nessun oggetto con id 1
        });
    }

    @Test
    public void testGetTotalWeight() throws BackpackWeightExceededException {
        backpack.addItem(item1);
        backpack.addItem(item2);
        assertEquals(9, backpack.getTotalWeight());
    }

    @Test
    public void testIsItemEquipped() throws BackpackWeightExceededException {
        backpack.addItem(item1);
        assertTrue(backpack.isItemEquipped(100));
        assertFalse(backpack.isItemEquipped(101));
    }

    @Test
    public void testGetItems() throws BackpackWeightExceededException {
        backpack.addItem(item1);
        backpack.addItem(item2);
        ArrayList<CollectableItem> items = backpack.getItems();
        assertEquals(2, items.size());
        assertTrue(items.contains(item1));
        assertTrue(items.contains(item2));
    }

    @Test
    public void testGetItemById() throws BackpackWeightExceededException, ItemNotFoundException {
        backpack.addItem(item1);
        CollectableItem foundItem = backpack.getItemById(100);
        assertEquals(item1, foundItem);
    }

    @Test
    public void testGetItemByIdThrowsExceptionWhenItemNotFound() {
        assertThrows(ItemNotFoundException.class, () -> {
            backpack.getItemById(100); // Nessun oggetto con id 1
        });
    }

}
