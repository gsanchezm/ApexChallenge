package com.apex.data;

import com.apex.utils.data.IGetTableArray;
import com.apex.utils.data.SQLArrayData;
import org.testng.annotations.DataProvider;

import static com.apex.config.Constants.DATABASE_NAME;

public class SQLFiltersDataProvider {

    private final IGetTableArray iGetTableArray = new SQLArrayData();

    /**
     * Generic method to retrieve data from a given Excel sheet.
     *
     * @param sqlFile Name of the SQL query file
     * @return Object[][] data from the sheet
     */
    private Object[][] getDataFromTable(String sqlFile) {
        return iGetTableArray.getTableArray(sqlFile,DATABASE_NAME);
    }

    @DataProvider
    public Object[][] getScreenTechData() {
        return getDataFromTable("screen_technology_test_data.sql");
    }

    @DataProvider
    public Object[][] getModelYearData() {
        return getDataFromTable("model_year_test_data.sql");
    }

    @DataProvider
    public Object[][] getResolutionData() {
        return getDataFromTable("resolution_test_data.sql");
    }

    @DataProvider
    public Object[][] getProductTypeData() {
        return getDataFromTable("product_type_test_data.sql");
    }
}
