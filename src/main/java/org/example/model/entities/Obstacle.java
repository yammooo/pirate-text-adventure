package org.example.model.entities;

public class Obstacle implements Entity {
    private final int id;
    private final String name;
    private final String description;
    private final int itemToDefeatID;

    public Obstacle(int id, String name, String description, int itemToDefeatID) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.itemToDefeatID = itemToDefeatID;
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

    public int getItemToDefeatID() {
        return itemToDefeatID;
    }

}
