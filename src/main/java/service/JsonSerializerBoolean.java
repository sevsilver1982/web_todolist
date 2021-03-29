package service;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;

public final class JsonSerializerBoolean implements JsonSerializer<Boolean>, JsonDeserializer<Boolean> {

    @Override
    public JsonElement serialize(final Boolean src, final Type typeOfSrc, final JsonSerializationContext context) {
        return Boolean.TRUE.equals(src) ? new JsonPrimitive(1) : new JsonPrimitive(0);
    }

    @Override
    public Boolean deserialize(final JsonElement json, final Type typeOfT, final JsonDeserializationContext context) throws JsonParseException {
        return json.getAsInt() == 1;
    }

}
