package org.example.newgamecreator.map_creation.volcania_island;

import org.example.model.entities.CollectableItem;
import org.example.model.entities.Location;
import org.example.model.entities.NPC;
import org.example.model.entities.ViewableItem;
import org.example.newgamecreator.entities_creation.EntitiesCreator;

import java.util.ArrayList;

public class TreasureIsland {
    public static Location createTreasureIsland(int locationId) {
        ArrayList<NPC> npcs = new ArrayList<>();
        ArrayList<CollectableItem> collectableItems = new ArrayList<>();
        ArrayList<ViewableItem> viewableItems = new ArrayList<>();
        ArrayList<Integer> adjacentLocations = new ArrayList<>();


        collectableItems.add(EntitiesCreator.createTreasure());


        //adjacentLocations.add()

        return new Location(locationId,"Treasure Island","You made it!!!", collectableItems, viewableItems, npcs, adjacentLocations);
    }
}
