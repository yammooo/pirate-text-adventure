package org.example.model.entities;


public class CollectableItem implements iEntity {
    private int id;
    private String name;
    private String description;
    private int weight;
    private int requiredEntityID;

    public CollectableItem(int id, String name, String description, int weight, int requiredEntityID) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.weight = weight;
        this.requiredEntityID = requiredEntityID;
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

    public int getWeight() {
        return weight;
    }

    public int getRequiredEntityID() {
        return requiredEntityID;
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

    public void setweight(int weight) {
        this.weight = weight;
    }

    public void setrequiredEntityID(int  requiredEntityID) {
        this.requiredEntityID = requiredEntityID;
    }


}

