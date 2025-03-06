package com.apex.config;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.Optional;

public class Constants {

    // System root directory
    public static final Path SYSTEM_ROOT = Paths.get(System.getProperty("user.dir"));

    // Resources folders
    public static final Path RESOURCES_FOLDER = SYSTEM_ROOT.resolve("src/main/resources");
    public static final Path RESULTS_FOLDER = SYSTEM_ROOT.resolve("results");

    // Data folders and files
    public static final Path JSONS_FOLDER = RESOURCES_FOLDER.resolve("testdata/jsons/");
    public static final Path WORKBOOKS_FOLDER = RESOURCES_FOLDER.resolve("testdata/workbooks/");
    public static final Path QUERIES_FOLDER = RESOURCES_FOLDER.resolve("testdata/queries/");

    public static final String WORKBOOK_FILE = "LiverpoolTestData.xlsx";
    public static final String  DATABASE_NAME = "SmartTV_DB";
    // Configuration file path
    public static final Path CONFIG_PATH = RESOURCES_FOLDER.resolve("config.properties");

    //TestRail
    public static final String TESTRAIL_USERNAME = PropertyManager.getInstance().getProperty("TestRailUsername");
    public static final String TESTRAIL_PASSWORD = PropertyManager.getInstance().getProperty("TestRailPass");
    public static final String TESTRAIL_ENGINE_URL = PropertyManager.getInstance().getProperty("TestRailUrl");
    public static final int TEST_RUN_ID = 18;
    public static final int TEST_CASE_PASSED_STATUS = 1;
    public static final int TEST_CASE_FAILED_STATUS = 5;

    // Web Configuration Info (lazy-loaded)
    private static String baseUrl;

    public static String getBaseUrl() {
        return Optional.ofNullable(baseUrl)
                .orElseGet(() -> {
                    baseUrl = PropertyManager.getInstance().getProperty("Url");
                    return baseUrl;
                });
    }

    public static final String SMART_TV_PRECONDITION_DATA = "SmartTVSteps";

    public static final Duration WAIT_TIMEOUT = Duration.ofSeconds(15);
}