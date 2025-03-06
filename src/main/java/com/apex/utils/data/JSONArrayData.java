package com.apex.utils.data;

import com.apex.config.FrameworkException;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.LinkedHashMap;
import java.util.Optional;
import java.util.stream.IntStream;

import static com.apex.config.Constants.JSONS_FOLDER;


public class JSONArrayData implements IGetTableArray {

    private static JsonArray getJsonArray(String filename, String jsonObj) {
        String filePath = JSONS_FOLDER.resolve(filename).toString();

        if (!Files.exists(Paths.get(filePath))) {
            throw new FrameworkException("File not found: " + filePath);
        }

        try (FileReader reader = new FileReader(filePath)) {
            return Optional.ofNullable(JsonParser.parseReader(reader))
                    .filter(JsonElement::isJsonObject)
                    .map(JsonElement::getAsJsonObject)
                    .map(obj -> obj.get(jsonObj))
                    .filter(JsonElement::isJsonArray)
                    .map(JsonElement::getAsJsonArray)
                    .orElseThrow(() -> new FrameworkException("JSON object '" + jsonObj + "' is not a valid JSON array in file: " + filePath));
        } catch (IOException e) {
            throw new FrameworkException("Error reading JSON file: " + filePath, e);
        }
    }

    @Override
    public Object[][] getTableArray(String filename, String jsonObj) {
        JsonArray jsonArray = getJsonArray(filename, jsonObj);

        return IntStream.range(0, jsonArray.size())
                .mapToObj(i -> new Object[]{convertJsonObjectToMap(jsonArray.get(i).getAsJsonObject())})
                .toArray(Object[][]::new);
    }

    private static LinkedHashMap<Object, Object> convertJsonObjectToMap(JsonObject jsonObject) {
        return jsonObject.keySet()
                .stream()
                .collect(LinkedHashMap::new, (map, key) -> map.put(key, jsonObject.get(key).getAsString()), LinkedHashMap::putAll);
    }
}

