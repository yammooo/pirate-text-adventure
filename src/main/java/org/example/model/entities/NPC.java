package org.example.model.entities;

/**
 * NPC is an implementation of the Entity interface.
 * It represents a non-player character in the game, with an id, name, description, and dialogue.
 * These characters can be interacted with by the player to get valuable advice for the game.
 */
public class NPC implements Entity {
    private final int id;
    private final String name;
    private final String description;
    private final String dialogue;

    /**
     * Constructs a new NPC with the given id, name, description, and dialogue.
     *
     * @param id          the id of the NPC
     * @param name        the name of the NPC
     * @param description the description of the NPC
     * @param dialogue    the dialogue of the NPC
     */
    public NPC(int id, String name, String description, String dialogue) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.dialogue = dialogue;
    }

    // Getters

    /**
     * Returns the id of this NPC.
     *
     * @return the id of this NPC
     */
    @Override
    public int getID() {
        return id;
    }

    /**
     * Returns the name of this NPC.
     *
     * @return the name of this NPC
     */
    @Override
    public String getName() {
        return name;
    }

    /**
     * Returns the description of this NPC.
     *
     * @return the description of this NPC
     */
    @Override
    public String getDescription() {
        return description;
    }

    /**
     * Returns the dialogue of this NPC.
     *
     * @return the dialogue of this NPC
     */
    public String getDialogue() {
        return dialogue;
    }
}