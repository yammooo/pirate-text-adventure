package org.example.model.entities;

public class ViewableItem implements Entity {

    private final int id;
    private final String name;
    private final String description;

    public ViewableItem(int id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
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

}
