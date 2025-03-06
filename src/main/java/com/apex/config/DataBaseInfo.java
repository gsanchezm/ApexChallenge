package com.apex.config;

import java.util.Optional;

import java.util.function.Supplier;

public enum DataBaseInfo {
    DBHOST(() -> getProperty("DBHost")),
    DBPORT(() -> getProperty("DBPort")),
    DBUSERNAME(() -> getProperty("DBUserName")),
    DBPASSWORD(() -> getProperty("DBPassword")),
    DBNAME(() -> getProperty("DBStudent"));

    private final Supplier<String> dbSupplier;

    DataBaseInfo(Supplier<String> dbSupplier) {
        this.dbSupplier = dbSupplier;
    }

    public String getValue() {
        return dbSupplier.get();
    }

    private static String getProperty(String key) {
        return Optional.ofNullable(PropertyManager.getInstance().getProperty(key))
                .orElseThrow(() -> new IllegalStateException("Missing database property: " + key));
    }
}