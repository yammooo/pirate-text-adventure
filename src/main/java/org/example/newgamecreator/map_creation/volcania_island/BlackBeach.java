package org.example.newgamecreator.map_creation.volcania_island;

import org.example.model.entities.CollectableItem;
import org.example.model.entities.Location;
import org.example.model.entities.NPC;
import org.example.model.entities.ViewableItem;
import org.example.newgamecreator.entities_creation.EntitiesCreator;

import java.util.ArrayList;

public class BlackBeach {

    public static Location createBlackBeach(int locationId) {
        ArrayList<NPC> npcs = new ArrayList<>();
        ArrayList<CollectableItem> collectableItems = new ArrayList<>();
        ArrayList<ViewableItem> viewableItems = new ArrayList<>();
        ArrayList<Integer> adjacentLocations = new ArrayList<>();

        collectableItems.add(EntitiesCreator.createCoconut());
        collectableItems.add(EntitiesCreator.createPassionFruit());
        collectableItems.add(EntitiesCreator.createLadder());

        npcs.add(EntitiesCreator.createOldSage());

        return new Location(locationId,"Black Beach","A beach with black sand, it's quite a sight.", collectableItems, viewableItems, npcs, adjacentLocations);
    }

}