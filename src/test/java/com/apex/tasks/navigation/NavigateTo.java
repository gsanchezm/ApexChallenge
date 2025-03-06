package com.apex.tasks.navigation;

import com.apex.actions.Click;
import com.apex.pages.HomePage;


public class NavigateTo extends HomePage {

    public NavigateTo categoriesMenu(){
        Click.on(icnCategories);
        return this;
    }
}
