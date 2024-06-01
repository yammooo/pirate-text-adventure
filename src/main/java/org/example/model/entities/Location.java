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
    private final ArrayList<ViewableItem> viewables;
    private final ArrayList<NPC> npcs;
    private Map<String, Location> adjacentLocations;

    public Location(int id, String name, String description, ArrayList<CollectableItem> items, ArrayList<ViewableItem> viewableitems, ArrayList<NPC> npcs) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.items = items != null ? new ArrayList<>(items) : new ArrayList<>();
        this.viewables = viewableitems != null ? new ArrayList<>(viewableitems) : new ArrayList<>();
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


    // Setters

    public void setAdjacentLocations(Map<String, Location> adjacentLocations) {
        this.adjacentLocations = adjacentLocations;
    }

    public ArrayList<ViewableItem> getViewableItems() {
        return viewables;
    }

    public ArrayList<CollectableItem> getCollectableItems() {
        return items;
    }

    public ArrayList<NPC> getNPC() {
        return npcs;
    }

    public void addCollectableItem(CollectableItem item) {
        items.add(item);
    }

    // find if a location is adjacent or not
    public boolean isAdjacentLocation(Location adjLocation) {
        for(Location location : this.adjacentLocations.values()) {
            if(location.getID() == adjLocation.getID())
                return true;
        }
        return false;
    }

    // get a specific collectableItem by its ID
    public CollectableItem getCollectableItemByID(int id) throws ItemNotFoundException {
        for(CollectableItem item : this.items) {
            if(item.getID() == id)
                return item;
        }
        throw new ItemNotFoundException(this.name+" does not have this item ");
    }

    // return a list containing all entities in the location
    public ArrayList<Entity> getAllEntities() {
        ArrayList<Entity> entities = new ArrayList<>();
        entities.addAll(this.items);
        entities.addAll(this.viewables);
        entities.addAll(this.npcs);

        return entities;
    }

    public Entity getEntityById(int id) throws ItemNotFoundException {
        for (CollectableItem item : items) {
            if (item.getID() == id) {
                return item;
            }
        }
        for (ViewableItem item : viewables) {
            if (item.getID() == id) {
                return item;
            }
        }
        for (NPC npc : npcs) {
            if (npc.getID() == id) {
                return npc;
            }
        }
        throw new ItemNotFoundException("No entity with the given ID is found"); // Return null if no entity with the given ID is found
    }

}