package org.example.model.entities;

import org.example.pair.Pair;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class MapTest {

    private Map map;
    private Location location1;
    private Location location2;
    private Obstacle obstacle;
    @BeforeEach
    void setUp() {
        location1 = new Location(1, "Location1", "Description1", new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
        location2 = new Location(2, "Location2", "Description2", new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
        ArrayList<Location> locations = new ArrayList<>();
        locations.add(location1);
        locations.add(location2);
        obstacle = new Obstacle(1, "Obstacle1", "Description1", 1);
        HashMap<Pair<Integer, Integer>, Obstacle> obstacles = new HashMap<>();
        obstacles.put(new Pair<>(1, 2), obstacle);
        map = new Map(locations, obstacles, 1);
    }


    @Test
    void getLocationByIdReturnsCorrectLocation() {
        assertEquals(location1, map.getLocationById(1));
    }

    @Test
    void getLocationByIdReturnsNullForNonexistentId() {
        assertNull(map.getLocationById(3));
    }

    @Test
    void getObstacleByLocationsIDReturnsCorrectObstacle() {
        assertEquals(obstacle, map.getObstacleByLocationsID(1, 2));
    }

    @Test
    void getObstacleByLocationsIDReturnsNullForNonexistentIds() {
        assertNull(map.getObstacleByLocationsID(1, 3));
    }

    @Test
    void getPirateLocationIDReturnsCorrectId() {
        assertEquals(1, map.getPirateLocationID());
    }

    @Test
    void setPirateLocationIDChangesPirateLocation() {
        map.setPirateLocationID(2);
        assertEquals(2, map.getPirateLocationID());
    }

    @Test
    void removeObstacleByLocationsIDRemovesCorrectObstacle() {
        assertEquals(obstacle, map.removeObstacleByLocationsID(1, 2));
        assertNull(map.getObstacleByLocationsID(1, 2));
    }

    @Test
    void removeObstacleByLocationsIDReturnsNullForNonexistentIds() {
        assertNull(map.removeObstacleByLocationsID(1, 3));
    }
}