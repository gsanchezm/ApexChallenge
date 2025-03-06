package com.apex.pages;

import com.apex.core.BasePage;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class SubCategoryMenuPage extends BasePage {

    @FindBy(className = "modal-content")
    protected WebElement mdlMenu;

    @FindBy(className = "m-megamenu__category_menu-item")
    protected List<WebElement> dvSubCategoryMenu;

    @FindBy(className = "a-category__listElement")
    protected List<WebElement> lnkChildCategoryMenu;
}
