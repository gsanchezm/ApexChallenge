package com.apex.core;

import com.apex.config.PropertyManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class ChromeRemoteDriverManager implements IDriver {
    ChromeOptions chromeOptions = null;

    public ChromeRemoteDriverManager() {

    }

    @Override
    public WebDriver createDriver() {
        chromeOptions = new ChromeOptions();
        chromeOptions.setPlatformName("Windows 11");
        chromeOptions.setBrowserVersion("latest");

        Map<String, Object> sauceOptions = new HashMap<>();
        sauceOptions.put("username", PropertyManager.getInstance().getProperty("UserName"));
        sauceOptions.put("accessKey", PropertyManager.getInstance().getProperty("AccessKey"));

        chromeOptions.setCapability("sauce:options", sauceOptions);

        URI onDemandUri = URI.create(PropertyManager.getInstance().getProperty("OnDemandURL"));
        try {
            URL onDemandUrl = onDemandUri.toURL(); // Convert URI to URL
            return new RemoteWebDriver(onDemandUrl, chromeOptions);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }
}