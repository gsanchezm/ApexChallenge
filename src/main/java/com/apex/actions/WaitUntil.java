package com.apex.actions;

import com.apex.factory.WebDriverFactory;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import java.util.Optional;

import static com.apex.config.Constants.WAIT_TIMEOUT;

public class WaitUntil {
    private  static final WebDriver driver = WebDriverFactory.getInstance().getWebDriver();

    /**
     * Waits until a single element is visible.
     */
    public static boolean elementExists(WebElement element) {
        return Optional.ofNullable(element)
                .map(WaitUntil::tryWait)
                .orElse(false);
    }

    /**
     * Waits until the element is not displayed (invisible).
     *
     * @param element the WebElement to check
     * @return true if the element becomes invisible within the timeout, otherwise false
     */
    public static boolean elementNotDisplayed(WebElement element) {
        WebDriverWait wait = new WebDriverWait(driver, WAIT_TIMEOUT);
        try {
            return wait.until(ExpectedConditions.invisibilityOf(element));
        } catch (Exception e) {
            return false; // Element did not become invisible within timeout
        }
    }

    /**
     * Waits until all elements in a list are visible.
     */
    public static boolean allElementsExist(List<WebElement> elements) {
        WebDriverWait wait = new WebDriverWait(driver, WAIT_TIMEOUT);
        return elements.stream().allMatch(el -> {
            try {
                wait.until(ExpectedConditions.visibilityOf(el));
                return true;
            } catch (Exception e) {
                return false;
            }
        });
    }

    public static boolean allElementsNotExist(List<WebElement> elements) {
        WebDriverWait wait = new WebDriverWait(driver, WAIT_TIMEOUT);
        return elements.stream().allMatch(el -> {
            try {
                wait.until(ExpectedConditions.invisibilityOf(el));
                return true;
            } catch (Exception e) {
                return false;
            }
        });
    }

    /**
     * Waits until the element's text changes from the originalText.
     *
     * @param element      the WebElement to monitor
     * @param originalText the text we expect to change
     * @return true if the text changes before timeout, otherwise false
     */
    public static boolean waitUntilTextChanges(WebElement element, String originalText) {
        WebDriverWait wait = new WebDriverWait(driver, WAIT_TIMEOUT);
        try {
            return wait.until(driver1 -> {
                // Compare current text with original text
                String currentText = element.getText();
                return !currentText.equals(originalText);
            });
        } catch (Exception e) {
            return false; // text did not change within timeout
        }
    }

    /**
     * Waits for the page to be fully loaded.
     */
    public static void pageLoaded() {
        new WebDriverWait(driver, WAIT_TIMEOUT)
                .until(driver -> ((JavascriptExecutor) driver).executeScript("return document.readyState").equals("complete"));
    }

    /**
     * Waits until the URL contains a specific keyword.
     *
     * @param text The text to be present in the URL.
     * @return True if the URL contains the text within the timeout, otherwise false.
     */
    public static boolean urlContains(String text) {
        try {
            return new WebDriverWait(driver, WAIT_TIMEOUT)
                    .until(ExpectedConditions.urlContains(text));
        } catch (Exception e) {
            return false; // URL did not contain the text within timeout
        }
    }

    public static boolean textIsPresent(WebElement element, String text) {
        try {
            return new WebDriverWait(driver, WAIT_TIMEOUT)
                    .until(driver1 -> element.getText().equals(text));
        } catch (Exception e) {
            try {
                return new WebDriverWait(driver, WAIT_TIMEOUT)
                        .until(ExpectedConditions.textToBePresentInElement(element, text));
            }catch (Exception ex){
                return false; // Text did not appear within timeout
            }
        }
    }

    /**
     * Helper method: waits for a single element.
     */
    private static boolean tryWait(WebElement element) {
        try {
            new WebDriverWait(driver, WAIT_TIMEOUT)
                    .until(ExpectedConditions.visibilityOf(element));
            return true;
        } catch (Exception ignored) {
            return false;
        }
    }

    /**
     * Helper method: waits for all elements in a list.
     */
    private static boolean tryWaitForList(List<WebElement> elements) {
        try {
            new WebDriverWait(driver, WAIT_TIMEOUT)
                    .until(ExpectedConditions.visibilityOfAllElements(elements));
            return true;
        } catch (Exception ignored) {
            return false;
        }
    }
}