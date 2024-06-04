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
        return new CollectableItem(BLACK_KEY_ID, "Black Key", "A beautiful shiny black key, it looks important.", 0, NON_REQUIRED);
    }

    public static CollectableItem createGoldenKey() {
        return new CollectableItem(GOLDEN_KEY_ID, "Golden Key", "A beautiful shiny golden key, it looks important.", 0, NON_REQUIRED);
    }

    public static CollectableItem createTreasure() {
        return new CollectableItem(888888, "Treasure", "You just found... a new friend", 0, NON_REQUIRED);
    }



    // Weapons
    public static final int SPEAR_ID = 20;

    public static CollectableItem createSpear() {
        return new CollectableItem(SPEAR_ID, "Spear", "A shiny iron spear, sharp and ready for use.", 2, NON_REQUIRED);
    }

    // Viewable items

    // Collectable items

    public static CollectableItem createPinkLeaf() {
        return new CollectableItem(idCounter++, "Pink Leaf", "A big pink leaf, it's unusually vibrant.", 1, NON_REQUIRED);
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

    // NPCs

    public static NPC createOldSage() {
        return new NPC(idCounter++, "Barry", "An old sage that seems to know everything.", "You better make space for those stairs if you head into the forest, young fella...");
    }

    public static NPC createPinkMonkey() {
        return new NPC(idCounter++, "Bananas", "A pink dancing monkey, full of energy and mischief.", "What's that shiny thing in the pink tree?");
    }

    // Obstacles


}