package com.books.thebookinitiative.openlibrary;

import com.google.gson.*;

import java.lang.reflect.Type;

//This is as a result of the API returning description as two different types depending on ID
public class DescriptionDeserializer implements JsonDeserializer<String> {
    @Override
    public String deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        if (json.isJsonPrimitive() && json.getAsJsonPrimitive().isString()) {
            // It's a normal string and can thus be returned as is
            return json.getAsJsonPrimitive().getAsString();
        } else if (json.isJsonObject()) {
            // The return is an object, and we likely has value property
            JsonObject jsonObject = json.getAsJsonObject();
            //Check if it has value
            if (jsonObject.has("value")) {
                //Return the value as a string type
                return jsonObject.getAsJsonPrimitive("value").getAsString();
            }
        }

        // Return null or handle the case where it's neither a string nor an object
        return null;
    }
}
