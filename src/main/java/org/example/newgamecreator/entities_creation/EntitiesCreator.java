package org.example.newgamecreator.entities_creation;

import org.example.model.entities.CollectableItem;
import org.example.model.entities.NPC;

public class EntitiesCreator {

    private static final int NON_REQUIRED = 0;

    // Non-special ids are generated from this key
    private static final int STARTING_KEY = 100;
    private static int idCounter = STARTING_KEY;

    // Keys
    public static final int PINK_KEY_ID = 1;
    public static final int REQUIRED_ID_FOR_PINK_KEY = 10;

    public static CollectableItem createPinkKey() {
        return new CollectableItem(PINK_KEY_ID, "Pink Key", "A beautiful shiny pink key, it looks important.", 0, REQUIRED_ID_FOR_PINK_KEY);
    }

    public static final int BLACK_KEY_ID = 2;
    public static final int GOLDEN_KEY_ID = 3;

    public static CollectableItem createBlackKey() {
        return new CollectableItem(BLACK_KEY_ID, "Black Key", "A beautiful shiny black key, it looks important.", 0, FIREPLACE_ID);
    }

    public static CollectableItem createGoldenKey() {
        return new CollectableItem(GOLDEN_KEY_ID, "Golden Key", "A beautiful shiny golden key, it looks important.", 0, NON_REQUIRED);
    }

    public static CollectableItem createTreasure() {
        return new CollectableItem(888888, "Treasure", "You just found... a new friend", 0, NON_REQUIRED);
    }



    // Weapons
    public static final int SPEAR_ID = 20;
    public static final int SWORD_ID = 21;
    public static final int HATCHET_ID = 22;
    public static final int WOOD_ID = 24;
    public static final int FIREPLACE_ID = 23;

    public static CollectableItem createSpear() {
        return new CollectableItem(SPEAR_ID, "Spear", "A shiny iron spear, sharp and ready for use.", 2, NON_REQUIRED);
    }


    public static CollectableItem createSword() {
        return new CollectableItem(SWORD_ID, "Sword", "A sharp viking longsword... how did it end up here?", 2, NON_REQUIRED);
    }

    // Viewable items

    // Collectable items

    public static CollectableItem createPinkLeaf() {
        return new CollectableItem(idCounter++, "Pink Leaf", "A big pink leaf, a perfect souvenir.", 1, NON_REQUIRED);
    }

    public static CollectableItem createObsidian() {
        return new CollectableItem(idCounter++, "Obsidian", "A stone blacker than a moonless night.", 1, NON_REQUIRED);
    }

    public static CollectableItem createHatchet() {
        return new CollectableItem(HATCHET_ID, "Hatchet", "A rusty but big hatchet... but sharp enough to cut wood.", 1, NON_REQUIRED);
    }

    public static CollectableItem createLadder() {
        return new CollectableItem(REQUIRED_ID_FOR_PINK_KEY, "Ladder", "A huge wooden stepladder, it's sturdy and reliable.", 9, NON_REQUIRED);
    }

    public static CollectableItem createShell() {
        return new CollectableItem(idCounter++, "Shell", "A glistening white shell, perfect for a souvenir.", 1, NON_REQUIRED);
    }

    public static CollectableItem createShovel() {
        return new CollectableItem(idCounter++, "Shovel", "An old wooden shovel, it's seen better days.", 2, NON_REQUIRED);
    }

    public static CollectableItem createPassionFruit() {
        return new CollectableItem(idCounter++, "Passion Fruit", "A juicy ripe passion fruit, it's mouth-wateringly delicious.", 1, NON_REQUIRED);
    }

    public static CollectableItem createBottle() {
        return new CollectableItem(idCounter++, "Bottle", "An empty bottle, it could be useful.", 2, NON_REQUIRED);
    }

    public static CollectableItem createCoconut() {
        return new CollectableItem(idCounter++, "Coconut", "A coconut nut (is a giant nut), it's hard but nutritious.", 1, NON_REQUIRED);
    }

    public static CollectableItem createWood() {
        return new CollectableItem(WOOD_ID, "Coconut Wood", "Versitile and perfect to make a torch.", 1, HATCHET_ID);
    }

    public static CollectableItem createFireplace() {
        return new CollectableItem(FIREPLACE_ID, "Fireplace", "If you wish to search this place you must turn the light on.", 1, WOOD_ID);
    }

    // NPCs

    public static NPC createOldSage() {
        return new NPC(idCounter++, "Barry", "An old sage that seems to know everything.", "You better search for some stairs if you head into the forest, young fella...");
    }

    public static NPC createPinkMonkey() {
        return new NPC(idCounter++, "Bananas", "A pink dancing monkey, full of energy and mischief.", "What's that shiny thing in the pink tree?");
    }

    public static NPC createSeagull() {
        return new NPC(idCounter++, "Seagull", "An old seagull that lives nearby.", "You've come for the key hidden in the shed haven't you... you'll need some light to see inside there..");
    }


    // Obstacles


}