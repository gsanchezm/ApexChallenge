package com.apex.config;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Properties;
import java.util.stream.Stream;

public class PropertyManager {

    private static volatile PropertyManager instance;
    private static final String PROPERTY_FILE_PATH = System.getProperty("user.dir") + "/src/main/resources/config.properties";
    private final Properties properties = new Properties();

    private PropertyManager() {
        loadData();
    }

    public static PropertyManager getInstance() {
        return Optional.ofNullable(instance).orElseGet(() -> {
            synchronized (PropertyManager.class) {
                if (instance == null) {
                    instance = new PropertyManager();
                }
            }
            return instance;
        });
    }

    private void loadData() {
        try (FileInputStream fileInputStream = new FileInputStream(PROPERTY_FILE_PATH)) {
            properties.load(fileInputStream);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load properties file: " + PROPERTY_FILE_PATH, e);
        }
    }

    public String getProperty(String propertyKey) {
        return Optional.ofNullable(properties.getProperty(propertyKey))
                .orElseThrow(() -> new IllegalArgumentException("Property not found: " + propertyKey));
    }

    public List<String> getPropertyArray(String propertyArray) {
        return Arrays.asList(properties.getProperty(propertyArray, "").split(","));
    }
}