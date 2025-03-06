package com.apex.actions;

import com.apex.factory.WebDriverFactory;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.time.Duration;
import java.util.Optional;

public class Scroll {

    public static void toElement(WebElement element) {
        WebDriver driver = WebDriverFactory.getInstance().getWebDriver();

        Optional.ofNullable(element).ifPresent(el ->
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", element)
        );
    }
}
