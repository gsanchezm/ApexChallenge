package com.apex.tasks;

import com.apex.actions.SelectFromElementList;
import com.apex.pages.SubCategoryMenuPage;

import static com.apex.actions.WaitUntil.*;

public class PerformCategorySelection extends SubCategoryMenuPage {

    public PerformCategorySelection categoryAs(String category){
        elementExists(mdlMenu);
        SelectFromElementList.byText(dvSubCategoryMenu,category);
        return this;
    }

    public PerformCategorySelection withSubCategory(String keyword, String subCategory){
        urlContains(keyword);
        SelectFromElementList.byText(lnkChildCategoryMenu, subCategory);
        return this;
    }
}
