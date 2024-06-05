package org.example.newgamecreator.map_creation.boriana_island;

import org.example.model.entities.CollectableItem;
import org.example.model.entities.Location;
import org.example.model.entities.NPC;
import org.example.model.entities.ViewableItem;
import org.example.newgamecreator.entities_creation.EntitiesCreator;

import java.util.ArrayList;

public class DarkHouse {
    public static Location createDarkHouse(int locationId) {
        ArrayList<NPC> npcs = new ArrayList<>();
        ArrayList<CollectableItem> collectableItems = new ArrayList<>();
        ArrayList<ViewableItem> viewableItems = new ArrayList<>();
        ArrayList<Integer> adjacentLocations = new ArrayList<>();

        collectableItems.add(EntitiesCreator.createBlackKey());
        collectableItems.add(EntitiesCreator.createFireplace());

        return new Location(locationId,"Dark house","A house so deep in the forest where light doesn't reach.", collectableItems, viewableItems, npcs, adjacentLocations);
    }
}
