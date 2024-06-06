package org.example.model.entities;

/**
 * ViewableItem is an implementation of the Entity interface.
 * It represents an item that can be viewed in the game, with an id, name, and description.
 * These items are part of the game's environment and provide information to the player.
 * They cannot be interacted with or used in the same way as CollectableItems.
 */
public class ViewableItem implements Entity {

    private final int id;
    private final String name;
    private final String description;

    /**
     * Constructs a new ViewableItem with the given id, name, and description.
     *
     * @param id          the id of the item
     * @param name        the name of the item
     * @param description the description of the item
     */
    public ViewableItem(int id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
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

}