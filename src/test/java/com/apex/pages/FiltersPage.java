package com.apex.pages;

import com.apex.core.BasePage;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class FiltersPage extends BasePage {
    @FindBy(className = "div.col.a-text__filter>label")
    protected List<WebElement> dvSubCategoryMenu;

    @FindBy(xpath = "//div[contains(@id, 'countViewMore')]")
    protected List<WebElement> chkAllFilters;

    @FindBy(className = "a-link__viewMore")
    protected List<WebElement> lnkViewMoreBrand;

    @FindBy(css = "a.newFilterContainerLink")
    protected WebElement lnkCleanFilters;

    //Prices
    @FindBy(id = "max-price-filter")
    protected WebElement txtMaximum;

    @FindBy(id = "min-price-filter")
    protected WebElement txtMinimum;

    @FindBy(className = "m-radioButton")
    protected List<WebElement> rdbPrices;

    @FindBy(css = ".next-button__filter>i")
    protected WebElement spnNext;

    //Color
    @FindBy(id = "variants-normalizedColor-Blanco")
    protected WebElement rdbWhite;

    @FindBy(id = "variants-normalizedColor-Negro")
    protected WebElement rdbBlack;
}
