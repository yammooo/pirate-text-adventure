package org.example.json_translator;

import com.google.gson.*;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import org.example.model.entities.Obstacle;
import org.example.pair.Pair;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * PairObstacleMapAdapter is a custom TypeAdapter used for serializing and deserializing HashMaps
 * with Pair&lt;Integer, Integer&gt; keys and Obstacle values. This adapter allows Gson to properly convert
 * HashMaps to and from JSON format.
 */
public class PairObstacleMapAdapter extends TypeAdapter<HashMap<Pair<Integer, Integer>, Obstacle>> {
    private final Gson gson;

    /**
     * Constructs a new PairObstacleMapAdapter with the given Gson instance.
     *
     * @param gson the Gson instance used for serialization and deserialization
     */
    public PairObstacleMapAdapter(Gson gson) {
        this.gson = gson;
    }

    /**
     * Serializes a HashMap containing Pair keys and Obstacle values to JSON format.
     *
     * @param out   the JsonWriter to which the JSON should be written
     * @param value the HashMap to be serialized
     * @throws IOException if an I/O error occurs during serialization
     */
    @Override
    public void write(JsonWriter out, HashMap<Pair<Integer, Integer>, Obstacle> value) throws IOException {
        out.beginObject();
        for (Map.Entry<Pair<Integer, Integer>, Obstacle> entry : value.entrySet()) {
            out.name(entry.getKey().getKey() + "-" + entry.getKey().getValue());
            gson.toJson(entry.getValue(), Obstacle.class, out);
        }
        out.endObject();
    }

    /**
     * Deserializes JSON to a HashMap containing Pair keys and Obstacle values.
     *
     * @param in the JsonReader from which the JSON should be read
     * @return the deserialized HashMap
     * @throws IOException if an I/O error occurs during deserialization
     */
    @Override
    public HashMap<Pair<Integer, Integer>, Obstacle> read(JsonReader in) throws IOException {
        HashMap<Pair<Integer, Integer>, Obstacle> map = new HashMap<>();
        in.beginObject();
        while (in.hasNext()) {
            String[] pair = in.nextName().split("-");
            int key = Integer.parseInt(pair[0]);
            int value = Integer.parseInt(pair[1]);
            Obstacle obstacle = gson.fromJson(in, Obstacle.class);
            map.put(new Pair<>(key, value), obstacle);
        }
        in.endObject();
        return map;
    }
}
