package org.example.newgamecreator.map_creation.rosita_island;

import org.example.model.entities.CollectableItem;
import org.example.model.entities.Location;
import org.example.model.entities.NPC;
import org.example.model.entities.ViewableItem;
import org.example.newgamecreator.entities_creation.EntitiesCreator;

import java.util.ArrayList;

public class PinkBeach {

    public static Location createPinkBeach(int locationId) {
        ArrayList<NPC> npcs = new ArrayList<>();
        ArrayList<CollectableItem> collectableItems = new ArrayList<>();
        ArrayList<ViewableItem> viewableItems = new ArrayList<>();
        ArrayList<Integer> adjacentLocations = new ArrayList<>();


        collectableItems.add(EntitiesCreator.createPassionFruit());
        collectableItems.add(EntitiesCreator.createBottle());

        npcs.add(EntitiesCreator.createOldSage());

        return new Location(locationId,"Pink Beach","A beach with pink petals and leaves all over it.", collectableItems, viewableItems, npcs, adjacentLocations);
    }

}