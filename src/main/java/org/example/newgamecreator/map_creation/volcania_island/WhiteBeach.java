package org.example.newgamecreator.map_creation.volcania_island;

import org.example.model.entities.CollectableItem;
import org.example.model.entities.Location;
import org.example.model.entities.NPC;
import org.example.model.entities.ViewableItem;
import org.example.newgamecreator.entities_creation.EntitiesCreator;

import java.util.ArrayList;

public class WhiteBeach {
    public static Location createWhiteBeach(int locationId) {
        ArrayList<NPC> npcs = new ArrayList<>();
        ArrayList<CollectableItem> collectableItems = new ArrayList<>();
        ArrayList<ViewableItem> viewableItems = new ArrayList<>();
        ArrayList<Integer> adjacentLocations = new ArrayList<>();

        viewableItems.add(EntitiesCreator.createSmokedEels());

        collectableItems.add(EntitiesCreator.createShell());
        collectableItems.add(EntitiesCreator.createSword());

        return new Location(locationId,"White Beach","A dreamy paradise... with a scary side", collectableItems, viewableItems, npcs, adjacentLocations);
    }
}
