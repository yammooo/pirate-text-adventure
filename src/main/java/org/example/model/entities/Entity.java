package org.example.model.entities;

/**
 * Entity is an interface that represents an entity in the game.
 * An entity is any object that can exist in the game world, including items and NPCs.
 * Each entity has an id, a name, and a description.
 */
public interface Entity {

    /**
     * Returns the id of this entity.
     *
     * @return the id of this entity
     */
    int getID();

    /**
     * Returns the name of this entity.
     *
     * @return the name of this entity
     */
    String getName();

    /**
     * Returns the description of this entity.
     *
     * @return the description of this entity
     */
    String getDescription();

}