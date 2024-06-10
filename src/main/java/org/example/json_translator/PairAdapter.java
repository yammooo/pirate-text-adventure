package org.example.json_translator;

import com.google.gson.*;
import org.example.pair.Pair;
import java.lang.reflect.Type;

/**
 * PairAdapter is a custom Gson adapter used for serializing and deserializing Pair objects.
 * This adapter allows Gson to properly convert Pair objects to and from JSON format.
 */
public class PairAdapter implements JsonSerializer<Pair<Integer, Integer>>, JsonDeserializer<Pair<Integer, Integer>> {

    /**
     * Serializes a Pair object to JSON format.
     *
     * @param src the Pair object to be serialized
     * @param typeOfSrc the type of the source object
     * @param context the serialization context
     * @return the serialized JSON element
     */
    @Override
    public JsonElement serialize(Pair<Integer, Integer> src, Type typeOfSrc, JsonSerializationContext context) {
        return new JsonPrimitive(src.getKey() + "-" + src.getValue()); // Serializing Pair to JSON primitive
    }

    /**
     * Deserializes JSON to a Pair object.
     *
     * @param json the JSON element to be deserialized
     * @param typeOfT the type of the target object
     * @param context the deserialization context
     * @return the deserialized Pair object
     * @throws JsonParseException if an error occurs during deserialization
     */
    @Override
    public Pair<Integer, Integer> deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        String[] pair = json.getAsString().split("-"); // Parsing JSON string to extract key-value pair
        int key = Integer.parseInt(pair[0]);
        int value = Integer.parseInt(pair[1]);
        return new Pair<>(key, value); // Creating Pair object from parsed key-value pair
    }
}
