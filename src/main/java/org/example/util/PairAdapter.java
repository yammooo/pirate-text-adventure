package org.example.util;

import com.google.gson.*;
import org.example.pair.Pair;

import java.lang.reflect.Type;

public class PairAdapter implements JsonSerializer<Pair>, JsonDeserializer<Pair> {

    @Override
    public JsonElement serialize(Pair src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.add("key", context.serialize(src.getKey()));
        jsonObject.add("value", context.serialize(src.getValue()));
        return jsonObject;
    }

    @Override
    public Pair deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();
        return new Pair(
            context.deserialize(jsonObject.get("key"), Object.class),
            context.deserialize(jsonObject.get("value"), Object.class)
        );
    }
}