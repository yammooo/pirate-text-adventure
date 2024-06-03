package org.example.newgamecreator.map_creation.rosita_island;

import org.example.model.entities.CollectableItem;
import org.example.model.entities.Location;
import org.example.model.entities.NPC;
import org.example.model.entities.ViewableItem;
import org.example.newgamecreator.entities_creation.EntitiesCreator;

import java.util.ArrayList;

public class GoldenBeach {

    public static Location createGoldenBeach(int locationId) {
        ArrayList<NPC> npcs = new ArrayList<>();
        ArrayList<CollectableItem> collectableItems = new ArrayList<>();
        ArrayList<ViewableItem> viewableItems = new ArrayList<>();
        ArrayList<Integer> adjacentLocations = new ArrayList<>();

        collectableItems.add(EntitiesCreator.createShell());
        collectableItems.add(EntitiesCreator.createShovel());
        collectableItems.add(EntitiesCreator.createSpear());

        return new Location(locationId,"Golden Beach","A suspiciously empty beach.", collectableItems, viewableItems, npcs, adjacentLocations);
    }

}
