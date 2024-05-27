package org.example.model.entities;

public class Obstacle implements iEntity {
    private int id;
    private String name;
    private String description;
    private int itemToDefeatID;

    public Obstacle(int id, String name, String description, int itemToDefeatID) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.itemToDefeatID = itemToDefeatID;
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

    public int getItemToDefeatID() {
        return itemToDefeatID;
    }

}
