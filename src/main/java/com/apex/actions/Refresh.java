package com.apex.actions;

import com.apex.factory.WebDriverFactory;
import org.openqa.selenium.WebDriver;

public class Refresh {
    private static WebDriver driver = WebDriverFactory.getInstance().getWebDriver();

    public static void page(){
        driver.navigate().refresh();
    }
}
