package com.apex.data;

import com.apex.utils.data.ExcelArrayData;
import com.apex.utils.data.IGetTableArray;
import org.testng.annotations.DataProvider;

import static com.apex.config.Constants.WORKBOOK_FILE;

public class ExcelFiltersDataProvider {

    private final IGetTableArray iGetTableArray = new ExcelArrayData();

    /**
     * Generic method to retrieve data from a given Excel sheet.
     *
     * @param sheetName Name of the sheet
     * @return Object[][] data from the sheet
     */
    private Object[][] getDataFromSheet(String sheetName) {
        return iGetTableArray.getTableArray(WORKBOOK_FILE, sheetName);
    }

    @DataProvider
    public Object[][] getBrandsData() {
        return getDataFromSheet("Brand");
    }

    @DataProvider
    public Object[][] getSizesData() {
        return getDataFromSheet("Size");
    }

    @DataProvider
    public Object[][] getPricesData() {
        return getDataFromSheet("Price");
    }

    @DataProvider
    public Object[][] getColorsData() {
        return getDataFromSheet("Color");
    }

    @DataProvider
    public Object[][] getSalesByData() {
        return getDataFromSheet("SalesBy");
    }
}
