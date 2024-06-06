package org.example.model.entities;

/**
 * CollectableItem is an implementation of the Entity interface.
 * It represents an item that can be collected in the game, with an id, name, description, weight, and a requiredEntityID.
 * These items can be picked up by the player and added to the player's backpack, provided the total weight of the items in the backpack does not exceed the backpack's maximum weight.
 * Some items may require the player to have a certain other item (indicated by requiredEntityID) in order to be collected.
 */
public class CollectableItem implements Entity {
    private final int id;
    private final String name;
    private final String description;
    private final int weight;
    private final int requiredEntityID;

    /**
     * Constructs a new CollectableItem with the given id, name, description, weight, and requiredEntityID.
     *
     * @param id               the id of the item
     * @param name             the name of the item
     * @param description      the description of the item
     * @param weight           the weight of the item
     * @param requiredEntityID the id of the entity required to collect this item
     */
    public CollectableItem(int id, String name, String description, int weight, int requiredEntityID) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.weight = weight;
        this.requiredEntityID = requiredEntityID;
    }

    // Getters

    /**
     * Returns the id of this item.
     *
     * @return the id of this item
     */
    @Override
    public int getID() {
        return id;
    }

    /**
     * Returns the name of this item.
     *
     * @return the name of this item
     */
    @Override
    public String getName() {
        return name;
    }

    /**
     * Returns the description of this item.
     *
     * @return the description of this item
     */
    @Override
    public String getDescription() {
        return description;
    }

    /**
     * Returns the weight of this item.
     *
     * @return the weight of this item
     */
    public int getWeight() {
        return weight;
    }

    /**
     * Returns the id of the entity required to collect this item.
     *
     * @return the id of the required entity
     */
    public int getRequiredEntityID() {
        return requiredEntityID;
    }
}