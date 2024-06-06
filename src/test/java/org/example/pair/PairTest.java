package org.example.pair;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PairTest {

    @Test
    void getKeyReturnsCorrectKey() {
        Pair<String, Integer> pair = new Pair<>("Key", 1);
        assertEquals("Key", pair.getKey());
    }

    @Test
    void getValueReturnsCorrectValue() {
        Pair<String, Integer> pair = new Pair<>("Key", 1);
        assertEquals(1, pair.getValue());
    }

    @Test
    void equalsReturnsTrueForIdenticalPairs() {
        Pair<String, Integer> pair1 = new Pair<>("Key", 1);
        Pair<String, Integer> pair2 = new Pair<>("Key", 1);
        assertEquals(pair1, pair2);
    }

    @Test
    void equalsReturnsFalseForDifferentPairs() {
        Pair<String, Integer> pair1 = new Pair<>("Key1", 1);
        Pair<String, Integer> pair2 = new Pair<>("Key2", 2);
        assertNotEquals(pair1, pair2);
    }

    @Test
    void toStringReturnsCorrectFormat() {
        Pair<String, Integer> pair = new Pair<>("Key", 1);
        assertEquals("Key-1", pair.toString());
    }
}