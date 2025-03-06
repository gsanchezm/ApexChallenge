package com.apex.utils.listeners;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

import java.util.EnumMap;
import java.util.Map;
import java.util.Optional;

public class Retry implements IRetryAnalyzer {
    private byte retryCount = 0;
    private static final byte MAX_RETRY = 3;

    private static final Map<Integer, String> TEST_STATUS_MAP = Map.of(
            ITestResult.SUCCESS, "SUCCESS",
            ITestResult.FAILURE, "FAILURE",
            ITestResult.SKIP, "SKIP"
    );

    @Override
    public boolean retry(ITestResult iTestResult) {
        return Optional.of(iTestResult)
                .filter(result -> !result.isSuccess() && retryCount < MAX_RETRY)
                .map(result -> {
                    System.out.printf("Retrying test %s with status %s for the %d time(s).%n",
                            result.getName(),
                            getResultsStatusName(result.getStatus()),
                            ++retryCount);
                    return true;
                })
                .orElse(false);
    }

    private String getResultsStatusName(final int status) {
        return TEST_STATUS_MAP.getOrDefault(status, "UNKNOWN");
    }
}