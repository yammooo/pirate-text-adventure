package org.example.newgamecreator.utils;

import com.google.gson.*;
import org.example.pair.Pair;
import java.lang.reflect.Type;

public class PairAdapter implements JsonSerializer<Pair<Integer, Integer>>, JsonDeserializer<Pair<Integer, Integer>> {

    @Override
    public JsonElement serialize(Pair<Integer, Integer> src, Type typeOfSrc, JsonSerializationContext context) {
        return new JsonPrimitive(src.getKey() + "-" + src.getValue());
    }

    @Override
    public Pair<Integer, Integer> deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        String[] pair = json.getAsString().split("-");
        int key = Integer.parseInt(pair[0]);
        int value = Integer.parseInt(pair[1]);
        return new Pair<>(key, value);
    }
}