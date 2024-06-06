package org.example.model.entities;

import org.example.exceptions.ItemNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class LocationTest {
    private Location location;
    private CollectableItem item1;
    private CollectableItem item2;
    private ViewableItem viewableItem;
    private NPC npc;

    @BeforeEach
    void setUp() {
        item1 = new CollectableItem(1, "Item 1", "Description 1", 5, 0);
        item2 = new CollectableItem(2, "Item 2", "Description 2", 5, 0);
        viewableItem = new ViewableItem(3, "Viewable Item", "Description 3");
        npc = new NPC(4, "NPC", "Description 4", "NPC Description");

        ArrayList<CollectableItem> items = new ArrayList<>();
        items.add(item1);
        items.add(item2);

        ArrayList<ViewableItem> viewableItems = new ArrayList<>();
        viewableItems.add(viewableItem);

        ArrayList<NPC> npcs = new ArrayList<>();
        npcs.add(npc);

        ArrayList<Integer> adjacentLocations = new ArrayList<>();
        adjacentLocations.add(1);
        adjacentLocations.add(2);

        location = new Location(0, "Location", "Description", items, viewableItems, npcs, adjacentLocations);
    }

    @Test
    void removeItem_removesItemFromLocation() {
        try {
            location.removeItem(1);
        } catch (ItemNotFoundException e) {
            e.printStackTrace();
        }
        assertFalse(location.getCollectableItems().contains(item1));
    }

    @Test
    void removeItem_throwsExceptionWhenItemNotFound() {
        assertThrows(ItemNotFoundException.class, () -> location.removeItem(999));
    }

    @Test
    void getCollectableItemByID_returnsCorrectItem() throws ItemNotFoundException {
        assertEquals(item1, location.getCollectableItemByID(1));
    }

    @Test
    void getCollectableItemByID_throwsExceptionWhenItemNotFound() {
        assertThrows(ItemNotFoundException.class, () -> location.getCollectableItemByID(999));
    }

    @Test
    void isAdjacentLocation_returnsTrueWhenLocationIsAdjacent() {
        Location adjacentLocation = new Location(1, "Adjacent Location", "Description", null, null, null, null);
        assertTrue(location.isAdjacentLocation(adjacentLocation));
    }

    @Test
    void isAdjacentLocation_returnsFalseWhenLocationIsNotAdjacent() {
        Location nonAdjacentLocation = new Location(999, "Non-Adjacent Location", "Description", null, null, null, null);
        assertFalse(location.isAdjacentLocation(nonAdjacentLocation));
    }

    @Test
    void getEntityById_returnsCorrectEntity() throws ItemNotFoundException {
        assertEquals(item1, location.getEntityById(1));
        assertEquals(viewableItem, location.getEntityById(3));
        assertEquals(npc, location.getEntityById(4));
    }

    @Test
    void getEntityById_throwsExceptionWhenEntityNotFound() {
        assertThrows(ItemNotFoundException.class, () -> location.getEntityById(999));
    }

    @Test
    void addAdjacentLocation_addsLocationToAdjacentLocations() {
        location.addAdjacentLocation(999);
        assertTrue(location.getAdjacentLocations().contains(999));
    }
}