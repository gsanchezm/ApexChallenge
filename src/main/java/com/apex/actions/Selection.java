package com.apex.actions;

import com.apex.factory.WebDriverFactory;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.Optional;

import static com.apex.config.Constants.WAIT_TIMEOUT;

public class Selection {

    public static void option(WebElement element, String selection) {
        Optional.ofNullable(element).ifPresent(el -> {
            WebDriverWait wait = new WebDriverWait(WebDriverFactory.getInstance().getWebDriver(), WAIT_TIMEOUT);
            wait.until(ExpectedConditions.visibilityOf(el));

            Select select = new Select(el);
            if (!trySelect(select, selection)) {
                throw new IllegalArgumentException("Invalid selection: " + selection);
            }
        });
    }

    private static boolean trySelect(Select select, String selection) {
        return tryMethod(() -> select.selectByVisibleText(selection))
                || tryMethod(() -> select.selectByValue(selection))
                || tryMethod(() -> select.selectByIndex(Integer.parseInt(selection)));
    }

    private static boolean tryMethod(Runnable action) {
        try {
            action.run();
            return true;
        } catch (WebDriverException | NumberFormatException ignored) {
            return false;
        }
    }
}