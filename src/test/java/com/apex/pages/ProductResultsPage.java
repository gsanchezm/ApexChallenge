package com.apex.pages;

import com.apex.core.BasePage;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class ProductResultsPage extends BasePage {

    @FindBy(className = "m-product__card")
    protected List<WebElement> lstProducts;
}
