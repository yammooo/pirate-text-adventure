package org.example.newgamecreator.map_creation.rosita_island;

import org.example.model.entities.CollectableItem;
import org.example.model.entities.Location;
import org.example.model.entities.NPC;
import org.example.model.entities.ViewableItem;
import org.example.newgamecreator.entities_creation.EntitiesCreator;

import java.util.ArrayList;

public class AbandonedHouse {

    public static Location createAbandonedHouse(int locationId) {
        ArrayList<NPC> npcs = new ArrayList<>();
        ArrayList<CollectableItem> collectableItems = new ArrayList<>();
        ArrayList<ViewableItem> viewableItems = new ArrayList<>();
        ArrayList<Integer> adjacentLocations = new ArrayList<>();

        viewableItems.add(EntitiesCreator.createLabel());
        collectableItems.add(EntitiesCreator.createLadder());

        return new Location(locationId,"Abandoned House","An old looking abandoned house with shattered windows and a shed", collectableItems, viewableItems, npcs, adjacentLocations);
    }
}
