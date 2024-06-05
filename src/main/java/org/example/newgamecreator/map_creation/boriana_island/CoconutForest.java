package org.example.newgamecreator.map_creation.boriana_island;

import org.example.model.entities.CollectableItem;
import org.example.model.entities.Location;
import org.example.model.entities.NPC;
import org.example.model.entities.ViewableItem;
import org.example.newgamecreator.entities_creation.EntitiesCreator;

import java.util.ArrayList;

public class CoconutForest {

    public static Location createCoconutForest(int locationId) {
        ArrayList<NPC> npcs = new ArrayList<>();
        ArrayList<CollectableItem> collectableItems = new ArrayList<>();
        ArrayList<ViewableItem> viewableItems = new ArrayList<>();
        ArrayList<Integer> adjacentLocations = new ArrayList<>();

        collectableItems.add(EntitiesCreator.createCoconut());
        collectableItems.add(EntitiesCreator.createWood());

        return new Location(locationId,"Coconut Forest","A deep coconut forest.", collectableItems, viewableItems, npcs, adjacentLocations);
    }
}
