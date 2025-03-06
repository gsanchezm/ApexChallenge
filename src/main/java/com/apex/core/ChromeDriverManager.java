package com.apex.core;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class ChromeDriverManager implements IDriver {

    public ChromeDriverManager() {

    }

    @Override
    public WebDriver createDriver() {
        return new ChromeDriver();
    }
}
