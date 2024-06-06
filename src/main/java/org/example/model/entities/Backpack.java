package org.example.model.entities;

import org.example.exceptions.BackpackWeightExceededException;
import org.example.exceptions.ItemNotFoundException;
import java.util.ArrayList;

/**
 * The Backpack class represents the backpack of the pirate.
 * It can hold a certain weight of CollectableItems.
 * The pirate can add items to the backpack as long as the total weight of the items does not exceed the maximum weight.
 * The pirate can also remove items from the backpack.
 */
public class Backpack {
    private final int maxWeight;
    private final ArrayList<CollectableItem> items;

    /**
     * Constructs a new Backpack with the given maximum weight.
     *
     * @param maxWeight the maximum weight the backpack can hold
     */
    public Backpack(int maxWeight) {
        this.maxWeight = maxWeight;
        this.items = new ArrayList<>();
    }

    /**
     * Adds the given item to the backpack.
     *
     * @param item the item to add
     * @throws BackpackWeightExceededException if the total weight of the items in the backpack after adding the item would exceed the maximum weight
     */
    public void addItem(CollectableItem item) throws BackpackWeightExceededException {
        if (getTotalWeight() + item.getWeight() <= maxWeight) {
            items.add(item);
        } else {
            throw new BackpackWeightExceededException("Item too heavy to add to the backpack.");
        }
    }

    /**
     * Removes the item with the given id from the backpack.
     *
     * @param id the id of the item to remove
     * @return the removed item
     * @throws ItemNotFoundException if no item with the given id is found in the backpack
     */
    public CollectableItem removeItem(int id) throws ItemNotFoundException {
        CollectableItem itemToRemove = getItemById(id);
        boolean itemRemoved = items.removeIf(item -> item.getID() == id);
        if (!itemRemoved) {
            throw new ItemNotFoundException("Item not found in the backpack.");
        }
        return itemToRemove;
    }

    // Getters

    /**
     * Returns the maximum weight the backpack can hold.
     *
     * @return the maximum weight the backpack can hold
     */
    public int getMaxWeight() {
        return maxWeight;
    }

    /**
     * Returns the total weight of the items in the backpack.
     *
     * @return the total weight of the items in the backpack
     */
    public int getTotalWeight() {
        int totalWeight = 0;
        for (CollectableItem item : items) {
            totalWeight += item.getWeight();
        }
        return totalWeight;
    }

    /**
     * Returns whether the item with the given id is in the backpack.
     *
     * @param id the id of the item
     * @return true if the item is in the backpack, false otherwise
     */
    public boolean isItemEquipped(int id) {
        for (CollectableItem item : items) {
            if (item.getID() == id) {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns a new ArrayList containing all items in the backpack.
     *
     * @return a new ArrayList containing all items in the backpack
     */
    public ArrayList<CollectableItem> getItems() {
        return new ArrayList<>(items);
    }

    /**
     * Returns the item with the given id from the backpack.
     *
     * @param id the id of the item
     * @return the item with the given id
     * @throws ItemNotFoundException if no item with the given id is found in the backpack
     */
    public CollectableItem getItemById(int id) throws ItemNotFoundException {
        for (CollectableItem item : items) {
            if (item.getID() == id) {
                return item;
            }
        }
        throw new ItemNotFoundException("Item not found in the backpack.");
    }
}