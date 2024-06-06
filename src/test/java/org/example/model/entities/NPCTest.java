package org.example.model.entities;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NPCTest {

    private NPC npc;

    @BeforeEach
    void setUp() {
        npc = new NPC(1, "NPC1", "Description1", "Dialogue1");
    }

    @Test
    void getIDReturnsCorrectID() {
        assertEquals(1, npc.getID());
    }

    @Test
    void getNameReturnsCorrectName() {
        assertEquals("NPC1", npc.getName());
    }

    @Test
    void getDescriptionReturnsCorrectDescription() {
        assertEquals("Description1", npc.getDescription());
    }

    @Test
    void getDialogueReturnsCorrectDialogue() {
        assertEquals("Dialogue1", npc.getDialogue());
    }
}