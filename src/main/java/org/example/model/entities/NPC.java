package org.example.model.entities;

public class NPC implements iEntity {
    private int id;
    private String name;
    private String description;
    private String dialogue;

    public NPC(int id, String name, String description, String dialogue) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.dialogue = dialogue;
    }

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

    public void setDialogue(String dialogue) {
        this.dialogue = dialogue;
    }

    public void setID(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
