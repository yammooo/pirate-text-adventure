package org.example.newgamecreator.map_creation.boriana_island;

import org.example.model.entities.CollectableItem;
import org.example.model.entities.Location;
import org.example.model.entities.NPC;
import org.example.model.entities.ViewableItem;
import org.example.newgamecreator.entities_creation.EntitiesCreator;

import java.util.ArrayList;

public class CobblestoneBeach {

    public static Location createCobblestoneBeach(int locationId) {
        ArrayList<NPC> npcs = new ArrayList<>();
        ArrayList<CollectableItem> collectableItems = new ArrayList<>();
        ArrayList<ViewableItem> viewableItems = new ArrayList<>();
        ArrayList<Integer> adjacentLocations = new ArrayList<>();

        collectableItems.add(EntitiesCreator.createHatchet());
        collectableItems.add(EntitiesCreator.createObsidian());

        npcs.add(EntitiesCreator.createSeagull());

        return new Location(locationId,"Cobblestone Beach","A rare cobblestone beach full of round pebbles", collectableItems, viewableItems, npcs, adjacentLocations);
    }
}
