package org.example.model.entities;

import org.example.exceptions.ItemNotFoundException;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;

public class Location {
    private final int id;
    private final String name;
    private final String description;
    private final ArrayList<CollectableItem> items;
    private final ArrayList<NPC> npcs;
    private final ArrayList<ViewableItem> viewables;
    private Map<String, Location> adjacentLocations;

    public Location(int id, String name, String description, ArrayList<CollectableItem> items, ArrayList<NPC> npcs, ArrayList<ViewableItem> viewable) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.viewables=viewable;
        this.items = items != null ? new ArrayList<>(items) : new ArrayList<>();
        this.npcs = npcs != null ? new ArrayList<>(npcs) : new ArrayList<>();
    }


    public void removeItem(int id) throws ItemNotFoundException {
        boolean itemRemoved = items.removeIf(item -> item.getID() == id);
        if (!itemRemoved) {
            throw new ItemNotFoundException(this.name+" does not have this item ");
        }
    }

    public void addItem(CollectableItem item) {
        items.add(item);
    }


    //Getters

    public int getID(){
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public List<NPC> getNpcs() {
        return npcs;
    }

    public Map<String, Location> getAdjacentLocations() {
        return adjacentLocations;
    }

    public List<CollectableItem> getCollectableItems(){
        return this.items;
    }

    public List<ViewableItem> getViewableItems(){
        return this.viewables;
    }




    // Setters

    public void setAdjacentLocations(Map<String, Location> adjacentLocations) {
        this.adjacentLocations = adjacentLocations;
    }

}