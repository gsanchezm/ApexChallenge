package com.apex.core;

import com.apex.config.FrameworkException;
import com.apex.factory.WebDriverFactory;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

import java.util.Optional;

public class BasePage implements IBase{
    protected static BasePage actualPage;

    @Override
    public <T extends IBase> T getInstance(Class<T> page) {
        WebDriver driver = Optional.ofNullable(WebDriverFactory.getInstance().getWebDriver())
                .orElseThrow(() -> new FrameworkException("WebDriver instance is null. Ensure WebDriver is initialized before using getInstance()."));

        Object objPage = PageFactory.initElements(driver, page);

        return Optional.ofNullable(objPage)
                .filter(page::isInstance)
                .map(page::cast)
                .orElseThrow(() -> new FrameworkException("Failed to initialize page: " + page.getSimpleName()));
    }

    @Override
    public <T extends IBase> T as(Class<T> pageInstance) {
        return Optional.of(this)
                .filter(pageInstance::isInstance)
                .map(pageInstance::cast)
                .orElseThrow(() -> new FrameworkException("Cannot cast " + this.getClass().getSimpleName() + " to " + pageInstance.getSimpleName()));
    }
}
