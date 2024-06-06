package org.example.model.entities;

/**
 * The Obstacle class implements the Entity interface.
 * It represents an obstacle in the game, with an id, name, description, and an itemToDefeatID.
 * The itemToDefeatID represents the item required to overcome this obstacle.
 */
public class Obstacle implements Entity {
    private final int id;
    private final String name;
    private final String description;
    private final int itemToDefeatID;

    /**
     * Constructs a new Obstacle with the given id, name, description, and itemToDefeatID.
     *
     * @param id             the id of the Obstacle
     * @param name           the name of the Obstacle
     * @param description    the description of the Obstacle
     * @param itemToDefeatID the id of the item required to defeat this Obstacle
     */
    public Obstacle(int id, String name, String description, int itemToDefeatID) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.itemToDefeatID = itemToDefeatID;
    }

    // Getters

    /**
     * Returns the id of this Obstacle.
     *
     * @return the id of this Obstacle
     */
    @Override
    public int getID() {
        return id;
    }

    /**
     * Returns the name of this Obstacle.
     *
     * @return the name of this Obstacle
     */
    @Override
    public String getName() {
        return name;
    }

    /**
     * Returns the description of this Obstacle.
     *
     * @return the description of this Obstacle
     */
    @Override
    public String getDescription() {
        return description;
    }

    /**
     * Returns the id of the item required to defeat this Obstacle.
     *
     * @return the id of the item required to defeat this Obstacle
     */
    public int getItemToDefeatID() {
        return itemToDefeatID;
    }

}