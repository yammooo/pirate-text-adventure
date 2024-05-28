package org.example.model.entities;

public class CollectableItem implements Entity {
    private final int id;
    private final String name;
    private final String description;
    private final int weight;
    private final int requiredEntityID;

    public CollectableItem(int id, String name, String description, int weight, int requiredEntityID) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.weight = weight;
        this.requiredEntityID = requiredEntityID;
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

    public int getWeight() {
        return weight;
    }

    public int getRequiredEntityID() {
        return requiredEntityID;
    }

}

