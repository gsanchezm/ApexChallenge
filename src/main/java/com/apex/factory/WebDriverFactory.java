package com.apex.factory;

import com.apex.config.FrameworkException;
import com.apex.core.IDriver;
import org.reflections.Reflections;
import org.openqa.selenium.WebDriver;

import java.util.Optional;
import java.util.Set;

public class WebDriverFactory {

    private static final WebDriverFactory INSTANCE = new WebDriverFactory();
    private final ThreadLocal<WebDriver> webDriver = new ThreadLocal<>();

    private WebDriverFactory() {
        // Private constructor to enforce Singleton pattern
    }

    public static WebDriverFactory getInstance() {
        return INSTANCE;
    }

    public WebDriver getWebDriver() {
        return webDriver.get();
    }

    public void setWebDriver(String browser) {
        try {
            // Get all available driver implementations
            Set<Class<? extends IDriver>> driverInterfaces = new Reflections(IDriver.class).getSubTypesOf(IDriver.class);

            // Find the matching driver class
            Class<? extends IDriver> matchedDriver = driverInterfaces.stream()
                    .filter(driver -> driver.getSimpleName().toLowerCase().contains(browser.toLowerCase()))
                    .findFirst()
                    .orElseThrow(() -> new FrameworkException("No WebDriver found for browser: " + browser));

            // Instantiate the driver dynamically
            IDriver driverInstance = matchedDriver.getDeclaredConstructor().newInstance();
            webDriver.set(driverInstance.createDriver());

            // Maximize browser window
            webDriver.get().manage().window().maximize();

        } catch (ReflectiveOperationException e) {
            throw new FrameworkException("Error initializing WebDriver for browser: " + browser, e);
        }
    }


    public void removeWebDriver() {
        Optional.ofNullable(webDriver.get()).ifPresent(driver -> {
            try {
                driver.quit();
            } catch (Exception e) {
                throw new FrameworkException("Unable to remove WebDriver | Exception: " + e.getMessage(), e);
            } finally {
                webDriver.remove();
            }
        });
    }

}