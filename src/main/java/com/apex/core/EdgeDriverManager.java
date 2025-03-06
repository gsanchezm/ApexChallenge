package com.apex.core;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.edge.EdgeDriver;

public class EdgeDriverManager implements IDriver {

    public EdgeDriverManager() {

    }

    @Override
    public WebDriver createDriver() {
        return new EdgeDriver();
    }
}
