package org.example.newgamecreator.map_creation.rosita_island;

import org.example.model.entities.CollectableItem;
import org.example.model.entities.Location;
import org.example.model.entities.NPC;
import org.example.model.entities.ViewableItem;
import org.example.newgamecreator.entities_creation.EntitiesCreator;

import java.util.ArrayList;

public class PinkForest {

    public static Location createPinkForest(int locationId) {
        ArrayList<NPC> npcs = new ArrayList<>();
        ArrayList<CollectableItem> collectableItems = new ArrayList<>();
        ArrayList<ViewableItem> viewableItems = new ArrayList<>();
        ArrayList<Integer> adjacentLocations = new ArrayList<>();


        collectableItems.add(EntitiesCreator.createPinkKey());
        collectableItems.add(EntitiesCreator.createPinkLeaf());

        //adjacentLocations.add()

        return new Location(locationId,"Pink Forest","A shiny clearing in a forest full of magic pink trees", collectableItems, viewableItems, npcs, adjacentLocations);
    }
}
