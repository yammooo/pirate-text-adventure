package org.example.model.entities;

import org.example.exceptions.ItemNotFoundException;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;

public class Location {
    private String name;
    private String description;
    private ArrayList<CollectableItem> items;
    private ArrayList<NPC> npcs;
    private Map<String, Location> adjacentLocations;
    private int locationId;

    public Location(String name, String description) {
        this.name = name;
        this.description = description;
        this.items = new ArrayList<>();
        this.npcs = new ArrayList<>();
    }

    public int getID(){
        return locationId;
    }

    public void setID( int id){
        this.locationId = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ArrayList<CollectableItem> getItems() {
        return items;
    }

    public void removeItem(int id) throws ItemNotFoundException {
        boolean itemRemoved = items.removeIf(item -> item.getID() == id);
        if (!itemRemoved) {
            throw new ItemNotFoundException("Item not found in the backpack.");
        }
    }

    public void addItem(CollectableItem item) {
        items.add(item);
    }


    public List<NPC> getNpcs() {
        return npcs;
    }

    public void addNpc(NPC npc) {
        npcs.add(npc);
    }

    public void removeNpc(NPC npc) {
        npcs.remove(npc);
    }

    public Map<String, Location> getAdjacentLocations() {
        return adjacentLocations;
    }

    public void setAdjacentLocations(Map<String, Location> adjacentLocations) {
        this.adjacentLocations = adjacentLocations;
    }

    public void addAdjacentLocation(String direction, Location location) {
        adjacentLocations.put(direction, location);
    }


}

