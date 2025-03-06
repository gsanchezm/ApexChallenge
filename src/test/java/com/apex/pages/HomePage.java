package com.apex.pages;

import com.apex.core.BasePage;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class HomePage extends BasePage {
    @FindBy(css = "i.icon-hammenu.i-hammenu")
    @CacheLookup
    protected WebElement icnCategories;

    @FindBy(css = "li.m-product__card.card-masonry.a")
    protected List<WebElement> lstProductsCard;

    @FindBy(css = "p.a-plp-results-title")
    protected WebElement lblResultCountProduct;
}
