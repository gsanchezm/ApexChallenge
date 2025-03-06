package com.apex.actions;

import com.apex.factory.WebDriverFactory;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.Optional;

import static com.apex.config.Constants.WAIT_TIMEOUT;

public class Enter {

    public static void text(WebElement element, String text) {
        Optional.ofNullable(element).ifPresent(el -> {
            new WebDriverWait(WebDriverFactory.getInstance().getWebDriver(), WAIT_TIMEOUT)
                    .until(ExpectedConditions.visibilityOf(el));
            el.clear();
            Optional.ofNullable(text).ifPresent(el::sendKeys);
        });
    }
}

