package com.apex.utils;

import com.apex.config.FrameworkException;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Optional;

public class JSONUtils {

    public static JsonElement parseJSON(String jsonLocation) {
        if (!Files.exists(Paths.get(jsonLocation))) {
            throw new FrameworkException("File not found: " + jsonLocation);
        }

        try (FileReader reader = new FileReader(jsonLocation)) {
            return JsonParser.parseReader(reader);
        } catch (IOException | JsonSyntaxException e) {
            throw new FrameworkException("Error parsing JSON file: " + jsonLocation, e);
        }
    }

    public static JsonArray getJsonArray(String jsonLocation) {
        return Optional.ofNullable(parseJSON(jsonLocation))
                .filter(JsonElement::isJsonArray)
                .map(JsonElement::getAsJsonArray)
                .orElseThrow(() -> new FrameworkException("JSON content is not an array in file: " + jsonLocation));
    }

    public static JsonArray getJsonArray(String filename, String jsonObj) {
        return Optional.ofNullable(parseJSON(filename))
                .filter(JsonElement::isJsonObject)
                .map(JsonElement::getAsJsonObject)
                .map(jsonObject -> jsonObject.get(jsonObj))
                .filter(JsonElement::isJsonArray)
                .map(JsonElement::getAsJsonArray)
                .orElseThrow(() -> new FrameworkException("JSON object '" + jsonObj + "' is not a valid JSON array in file: " + filename));
    }
}

