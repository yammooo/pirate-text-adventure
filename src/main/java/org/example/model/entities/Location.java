package org.example.model.entities;

import org.example.exceptions.ItemNotFoundException;
import java.util.List;
import java.util.ArrayList;

/**
 * The Location class represents a location in the game world.
 * Each location has an id, a name, a description, a list of items, viewable items, NPCs, and adjacent locations.
 * The player can interact with the entities in a location and move to an adjacent location.
 */
public class Location {
    private final int id;
    private final String name;
    private final String description;
    private final ArrayList<CollectableItem> items;
    private final ArrayList<ViewableItem> viewables;
    private final ArrayList<NPC> npcs;
    private final ArrayList<Integer> adjacentLocations;

    /**
     * Constructs a new Location with the given id, name, description, items, viewable items, NPCs, and adjacent locations.
     *
     * @param id          the id of the Location
     * @param name        the name of the Location
     * @param description the description of the Location
     * @param items       the items in the Location
     * @param viewableitems the viewable items in the Location
     * @param npcs        the NPCs in the Location
     * @param adjlocation the adjacent locations to the Location
     */
    public Location(int id, String name, String description, ArrayList<CollectableItem> items, ArrayList<ViewableItem> viewableitems, ArrayList<NPC> npcs, ArrayList<Integer> adjlocation) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.items = items != null ? new ArrayList<>(items) : new ArrayList<>();
        this.viewables = viewableitems != null ? new ArrayList<>(viewableitems) : new ArrayList<>();
        this.npcs = npcs != null ? new ArrayList<>(npcs) : new ArrayList<>();
        this.adjacentLocations=adjlocation;
    }

    /**
     * Removes an item from the location.
     *
     * @param id the id of the item to be removed
     * @throws ItemNotFoundException if the item is not found in the location
     */
    public void removeItem(int id) throws ItemNotFoundException {
        boolean itemRemoved = items.removeIf(item -> item.getID() == id);
        if (!itemRemoved) {
            throw new ItemNotFoundException(this.name+" does not have this item ");
        }
    }

    // Getters

    /**
     * Returns the id of this Location.
     *
     * @return the id of this Location
     */
    public int getID(){
        return id;
    }

    /**
     * Returns the name of this Location.
     *
     * @return the name of this Location
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the description of this Location.
     *
     * @return the description of this Location
     */
    public String getDescription() {
        return description;
    }

    /**
     * Returns the NPCs in this Location.
     *
     * @return the NPCs in this Location
     */
    public List<NPC> getNpcs() {
        return npcs;
    }

    /**
     * Returns the ids of the Locations adjacent to this Location.
     *
     * @return the ids of the Locations adjacent to this Location
     */
    public ArrayList<Integer> getAdjacentLocations() {
        return adjacentLocations;
    }

    // Setters

    /**
     * Returns the viewable items in this Location.
     *
     * @return the viewable items in this Location
     */
    public ArrayList<ViewableItem> getViewableItems() {
        return viewables;
    }

    /**
     * Returns the collectable items in this Location.
     *
     * @return the collectable items in this Location
     */
    public ArrayList<CollectableItem> getCollectableItems() {
        return items;
    }

    /**
     * Returns the NPCs in this Location.
     *
     * @return the NPCs in this Location
     */
    public ArrayList<NPC> getNPC() {
        return npcs;
    }

    /**
     * Adds a collectable item to this Location.
     *
     * @param item the item to be added
     */
    public void addCollectableItem(CollectableItem item) {
        items.add(item);
    }

    /**
     * Checks if a given Location is adjacent to this Location.
     *
     * @param adjLocation the Location to check
     * @return true if the Location is adjacent, false otherwise
     */
    public boolean isAdjacentLocation(Location adjLocation) {
        return this.adjacentLocations.contains(adjLocation.getID());
    }

    /**
     * Returns a specific collectable item by its id.
     *
     * @param id the id of the item
     * @return the item with the given id
     * @throws ItemNotFoundException if the item is not found in the location
     */
    public CollectableItem getCollectableItemByID(int id) throws ItemNotFoundException {
        for(CollectableItem item : this.items) {
            if(item.getID() == id)
                return item;
        }
        throw new ItemNotFoundException(this.name+" does not have this item ");
    }

    /**
     * Returns a list containing all entities in the location.
     *
     * @return a list containing all entities in the location
     */
    public ArrayList<Entity> getAllEntities() {
        ArrayList<Entity> entities = new ArrayList<>();
        entities.addAll(this.items);
        entities.addAll(this.viewables);
        entities.addAll(this.npcs);

        return entities;
    }

    /**
     * Returns an entity by its id.
     *
     * @param id the id of the entity
     * @return the entity with the given id
     * @throws ItemNotFoundException if the entity is not found in the location
     */
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

    /**
     * Adds an id of a Location to the list of adjacent locations.
     *
     * @param locationId the id of the Location to be added
     */
    public void addAdjacentLocation(int locationId) {
        adjacentLocations.add(locationId);
    }

}