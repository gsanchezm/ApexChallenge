package com.apex.pages;

import com.apex.core.BasePage;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class MenuPage extends BasePage {
    @FindBy(className = "m-megamenu__category_menu-item")
    protected List<WebElement> dvMenu;
}
