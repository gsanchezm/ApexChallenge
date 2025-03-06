package com.apex.actions;

import com.apex.factory.WebDriverFactory;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.Optional;

public class CheckBox {

    private static final Duration TIMEOUT = Duration.ofSeconds(3);
    private static final WebDriver driver = WebDriverFactory.getInstance().getWebDriver();

    /**
     * Verifies if a checkbox is selected.
     *
     * @param checkbox WebElement of the checkbox
     * @return true if checkbox is selected, otherwise false
     */
    public static boolean isSelected(WebElement checkbox) {
        return Optional.ofNullable(checkbox)
                .map(WebElement::isSelected)
                .orElse(false);
    }

    /**
     * Clicks on the checkbox if it's not already selected.
     *
     * @param checkbox WebElement of the checkbox
     */
    public static void select(WebElement checkbox) {
        Optional.ofNullable(checkbox)
                .filter(el -> !el.isSelected()) // Click only if not already selected
                .ifPresent(CheckBox::click);
    }

    /**
     * Unchecks the checkbox if it's selected.
     *
     * @param checkbox WebElement of the checkbox
     */
    public static void uncheck(WebElement checkbox) {
        Optional.ofNullable(checkbox)
                .filter(WebElement::isSelected) // Click only if it's selected
                .ifPresent(CheckBox::click);
    }

    /**
     * Clicks on the checkbox using a safe click method (tries normal click first, then JavaScript if needed).
     *
     * @param checkbox WebElement of the checkbox
     */
    private static void click(WebElement checkbox) {
            WebDriverWait wait = new WebDriverWait(driver, TIMEOUT);

        try {
            wait.until(ExpectedConditions.elementToBeClickable(checkbox));
            checkbox.click();
        } catch (Exception e) {
            forceClick(checkbox);
        }
    }

    /**
     * Uses JavaScript to force-click the checkbox.
     *
     * @param checkbox WebElement of the checkbox
     */
    private static void forceClick(WebElement checkbox) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", checkbox);
    }
}