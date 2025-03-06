package com.apex.actions;

import com.apex.factory.WebDriverFactory;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.Optional;

import static com.apex.config.Constants.WAIT_TIMEOUT;

public class Obtain {

    public static String text(WebElement element) {
        return Optional.ofNullable(element)
                .map(el -> {
                    WebDriverWait wait = new WebDriverWait(WebDriverFactory.getInstance().getWebDriver(), WAIT_TIMEOUT);
                    wait.until(ExpectedConditions.visibilityOf(el));
                    return "input".equals(el.getTagName()) ? el.getDomProperty("value") : el.getText();
                })
                .orElse("");
    }
}