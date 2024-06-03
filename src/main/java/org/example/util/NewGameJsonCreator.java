package org.example.util;
import java.util.HashMap;
import org.example.model.GameState;
import org.example.model.entities.*;
import org.example.pair.Pair;
import java.util.ArrayList;
import java.io.FileWriter;
import java.io.IOException;

public class NewGameJsonCreator {

    public static void main(String[] args) {
        // Step 1: Create an instance of GameState and populate it with values


        //SPIAGGIA INIZIALE : BEACH 11

        ArrayList<NPC> beach11npcs = new ArrayList<>();

        ArrayList<CollectableItem> beach11collectableItems = new ArrayList<>();

        CollectableItem shell11 = new CollectableItem(111, "shell", "a glistening white shell", 1,0 );
        CollectableItem shovel11 = new CollectableItem(112, "shovel", "an old wooden shovel", 2,0 );
        CollectableItem spear11 = new CollectableItem(113, "spear", "a shiny iron spear", 2,0 );

        beach11collectableItems.add(shell11);
        beach11collectableItems.add(shovel11);
        beach11collectableItems.add(spear11);

        ArrayList<ViewableItem> beach11ViewableItems = new ArrayList<>();

        Location beach11 = new Location(11,"Deserted Beach","a golden beach with warm sand",beach11collectableItems, beach11ViewableItems, beach11npcs);





        //SPIAGGIA ISOLA PICCOLA : BEACH 21

        ArrayList<NPC> beach21npcs = new ArrayList<>();

        NPC oldsage = new NPC(211, "barry", "an old sage that knows it all", "You better make space for those stairs if you head into the forest young fella...");

        beach21npcs.add(oldsage);

        ArrayList<CollectableItem> beach21collectableItems = new ArrayList<>();

        CollectableItem ladder21 = new CollectableItem(211, "ladder", "huge wooden stepladder", 10,0 );
        CollectableItem bottle21 = new CollectableItem(212, "bottle", "an empty bottle", 2,0 );
        CollectableItem coconut21 = new CollectableItem(213, "coconut", "a coconut nut (is a giant nut)", 1,0 );

        beach21collectableItems.add(ladder21);
        beach21collectableItems.add(bottle21);
        beach21collectableItems.add(coconut21);

        ArrayList<ViewableItem> beach21ViewableItems = new ArrayList<>();

        Location beach21 = new Location(21,"Deserted Beach","a golden beach with warm sand",beach21collectableItems, beach21ViewableItems, beach21npcs);



        //FORESTA ROSA

        ArrayList<NPC> pinkforestnpcs = new ArrayList<>();

        NPC pinkmonkey = new NPC (221, "bananas", "a pink dancing monkey","what's that shiny thing in the pink tree? ");

        pinkforestnpcs.add(pinkmonkey);

        ArrayList<CollectableItem> pinkforestCollectableitems= new ArrayList<>();

        CollectableItem pinkleaf = new CollectableItem(221, "leaf", "a big pink leafr", 1,0 );
        CollectableItem pinkkey = new CollectableItem(222, "pink Key", "a beautiful shiny pink key", 2,211 );
        CollectableItem passionfruit = new CollectableItem(223, "passion fruit", "a juicy ripe passion fruit ", 1,0 );

        pinkforestCollectableitems.add(pinkleaf);
        pinkforestCollectableitems.add(pinkkey);
        pinkforestCollectableitems.add(passionfruit);

        ArrayList<ViewableItem> pinkforestViewableItems = new ArrayList<>();

        Location pinkforest = new Location( 22, "pink forest", "a magic pink forest populated by monkeys", pinkforestCollectableitems,pinkforestViewableItems, pinkforestnpcs);

        // CREAZIONE OSTACOLI

        Obstacle squalo = new Obstacle(1, "squalo", "a bloodthirsty hammershark", 13);

        HashMap<Pair<Integer, Integer>, Obstacle> obstacles = new HashMap<>();

        obstacles.put(new Pair<>(11, 21), squalo );


        //UNIONE NELLA MAPPA

        ArrayList<Location> locationlist = new ArrayList<Location>();
        locationlist.add(beach11);
        locationlist.add(beach21);

        Map pirateMap = new Map(locationlist, obstacles, 11);

        //CREO BACKPACK

        Pirate pirate = new Pirate(3,new Backpack(10),3);

        GameState gameState = new GameState(pirate,pirateMap, "");

        /////////////////////////////////////////////////////////////////////////////////////////////////


        // Step 2: Use GameStateTranslator to convert the GameState instance to JSON
        String json = GameStateTranslator.gameStateToJson(gameState);

        // Step 3: Write the JSON string to a file in "src/main/resources"
        try (FileWriter file = new FileWriter("src/main/resources/json/defaultGameState.json")) {
            file.write(json);
            System.out.println("Successfully Copied JSON Object to File...");
            System.out.println("\nJSON Object: " + json);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
