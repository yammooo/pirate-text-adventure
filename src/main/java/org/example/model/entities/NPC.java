package org.example.model.entities;

public class NPC implements Entity {
    private final int id;
    private final String name;
    private final String description;
    private final String dialogue;

    public NPC(int id, String name, String description, String dialogue) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.dialogue = dialogue;
    }


    // Getters

    @Override
    public int getID() {
        return id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getDescription() {
        return description;
    }

    public String getDialogue() {
        return dialogue;
    }

}