package org.example.json_translator;

import com.google.gson.*;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import org.example.model.entities.Obstacle;
import org.example.pair.Pair;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class PairObstacleMapAdapter extends TypeAdapter<HashMap<Pair<Integer, Integer>, Obstacle>> {
    private final Gson gson;

    public PairObstacleMapAdapter(Gson gson) {
        this.gson = gson;
    }

    @Override
    public void write(JsonWriter out, HashMap<Pair<Integer, Integer>, Obstacle> value) throws IOException {
        out.beginObject();
        for (Map.Entry<Pair<Integer, Integer>, Obstacle> entry : value.entrySet()) {
            out.name(entry.getKey().getKey() + "-" + entry.getKey().getValue());
            gson.toJson(entry.getValue(), Obstacle.class, out);
        }
        out.endObject();
    }

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