package com.apex.utils.listeners;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class TestListener implements ITestListener {
    private static final Logger logger = LogManager.getLogger(TestListener.class);

    @Override
    public void onStart(ITestContext context) {
        logger.info("Starting test execution: {}", context.getName());
    }

    @Override
    public void onFinish(ITestContext context) {
        logger.info("Finishing test execution: {}", context.getName());
        logger.info("======= TEST EXECUTION COMPLETED =======");
    }

    @Override
    public void onTestStart(ITestResult result) {
        logger.info("-------------------------------------------------");
        logger.info("Test Started: {}", getTestMethodName(result));
        logger.info("-------------------------------------------------");
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        logger.info("✅ {} - Test Passed", getTestMethodName(result));
    }

    @Override
    public void onTestFailure(ITestResult result) {
        logger.error("❌ {} - Test Failed", getTestMethodName(result));
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        logger.warn("⚠ {} - Test Skipped", getTestMethodName(result));
    }

    /**
     * Helper method to extract the test method name.
     */
    private String getTestMethodName(ITestResult result) {
        return result.getMethod().getConstructorOrMethod().getName();
    }
}
