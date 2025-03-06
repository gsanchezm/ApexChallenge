package com.apex.data;

import com.apex.utils.data.ExcelArrayData;
import com.apex.utils.data.IGetTableArray;
import com.apex.utils.data.JSONArrayData;
import org.testng.annotations.DataProvider;

public class JsonFiltersDataProvider {

    IGetTableArray iGetTableArray;


    @DataProvider
    public Object[][] getSmartTVData(){
        iGetTableArray = new JSONArrayData();
        return iGetTableArray.getTableArray("smart_tv_filter_test_data.json","SmartTV");
    }
}
