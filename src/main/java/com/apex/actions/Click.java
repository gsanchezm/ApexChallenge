package com.apex.actions;

import com.apex.factory.WebDriverFactory;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.Optional;

import static com.apex.config.Constants.WAIT_TIMEOUT;

public class Click {

    private static WebDriver driver = WebDriverFactory.getInstance().getWebDriver();

    /**
     * Default Click: First tries normal click, falls back to JavaScript click.
     */
    public static void on(WebElement element) {
        WebDriverWait wait = new WebDriverWait(WebDriverFactory.getInstance().getWebDriver(), WAIT_TIMEOUT);

        Optional.ofNullable(element).ifPresent(el -> {
            try {
                wait.until(ExpectedConditions.elementToBeClickable(el));
                el.click();
            } catch (Exception e) {
                forceClick(el);
            }
        });
    }

    /**
     * Force Click using JavaScript Executor.
     */
    public static void forceClick(WebElement element) {
        Optional.ofNullable(element).ifPresent(el ->
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", el)
        );
    }

    /**
     * Click with retry: Tries up to 3 times if click fails.
     */
    public static void withRetry(WebElement element, int attempts) {
        WebDriverWait wait = new WebDriverWait(driver, WAIT_TIMEOUT);

        for (int i = 0; i < attempts; i++) {
            try {
                wait.until(ExpectedConditions.elementToBeClickable(element));
                element.click();
                return; // Exit loop on success
            } catch (Exception e) {
                if (i == attempts - 1) {
                    forceClick(element); // Final fallback
                }
            }
        }
    }

    /**
     * Click after scrolling element into view.
     */
    public static void butScrollFirst(WebElement element) {
        Optional.ofNullable(element).ifPresent(el -> {
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", el);
            on(el); // Perform regular click after scrolling
        });
    }

    /**
     * Click using Actions class (simulates real user click).
     */
    public static void actionClick(WebElement element) {
        Actions actions = new Actions(driver);
        Optional.ofNullable(element).ifPresent(el -> actions.moveToElement(el).click().perform());
    }
}
