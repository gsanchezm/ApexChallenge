package com.apex.tests;

import com.apex.config.PropertyManager;
import com.apex.core.BasePage;
import com.apex.factory.WebDriverFactory;
import com.apex.tasks.PerformCategorySelection;
import com.apex.tasks.navigation.NavigateTo;
import com.apex.utils.testrail.CaseModel;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;

import java.util.List;
import java.util.Optional;

import static com.apex.config.Constants.SMART_TV_PRECONDITION_DATA;
import static com.apex.config.Constants.getBaseUrl;

public class BaseWebClass extends BasePage {
    private static final WebDriverFactory webDriverFactory = WebDriverFactory.getInstance();
    protected CaseModel caseModel;
    protected int i;

    @BeforeTest
    @Parameters("browser")
    public void initializeWebDriver(String browser) {
        i=0;
        List<String> steps = Optional.ofNullable(PropertyManager.getInstance().getPropertyArray(SMART_TV_PRECONDITION_DATA))
                .orElseThrow(() -> new IllegalArgumentException("❌ SmartTVSteps not found!"));

        // Initialize WebDriver & Navigate
        webDriverFactory.setWebDriver(browser);
        Optional.ofNullable(webDriverFactory.getWebDriver())
                .ifPresent(driver -> driver.navigate().to(getBaseUrl()));

        // Navigate to categories
        getInstance(NavigateTo.class).as(NavigateTo.class).categoriesMenu();

        // Perform category selection
        getInstance(PerformCategorySelection.class)
                .as(PerformCategorySelection.class)
                .categoryAs(steps.get(0))
                .withSubCategory(steps.get(1), steps.get(2))
                .withSubCategory(steps.get(3), steps.get(4));
    }

    @AfterTest
    public void cleanupWebDriver() {
        webDriverFactory.removeWebDriver();
    }
}