package com.apex.tests.sales;

import com.apex.actions.Refresh;
import com.apex.config.PropertyManager;
import com.apex.data.ExcelFiltersDataProvider;
import com.apex.data.JsonFiltersDataProvider;
import com.apex.data.SQLFiltersDataProvider;
import com.apex.questions.IsProductDisplayed;
import com.apex.tasks.PerformProductSelection;
import com.apex.tests.BaseWebClass;
import com.apex.utils.PriceParser;
import com.apex.utils.testrail.CaseModel;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import static com.apex.config.Constants.TEST_CASE_FAILED_STATUS;
import static com.apex.config.Constants.TEST_CASE_PASSED_STATUS;
import static com.apex.utils.SQLUtil.closeConnection;
import static com.apex.utils.testrail.TestRailUtil.addResultTestCase;

public class SalesSmartTVTests extends BaseWebClass {
    private PerformProductSelection selection;
    private String caseId;

    @BeforeMethod
    public void setup(){
        caseModel = new CaseModel();
        selection = getInstance(PerformProductSelection.class).as(PerformProductSelection.class);
    }

    @AfterMethod
    public void cleanFilters(ITestResult iTestResult) throws IOException {
        closeConnection();

        caseModel.setCaseId(Integer.parseInt(caseId));
        i++;

        // Setup the mapping for all possible statuses
        Map<Integer,Integer> results = new HashMap<>();
        results.put(ITestResult.SUCCESS, TEST_CASE_PASSED_STATUS);
        results.put(ITestResult.FAILURE, TEST_CASE_FAILED_STATUS);
        results.put(ITestResult.SKIP, TEST_CASE_FAILED_STATUS);
        results.put(ITestResult.SUCCESS_PERCENTAGE_FAILURE, TEST_CASE_FAILED_STATUS);

        // Retrieve the correct status ID, or fallback if missing
        Integer statusId = results.get(iTestResult.getStatus());
        if (statusId == null) {
            statusId = TEST_CASE_FAILED_STATUS; // fallback
        }
        caseModel.setStatusId(statusId);

        caseModel.setComment(
                statusId == TEST_CASE_PASSED_STATUS
                        ? iTestResult.getName() + " Passed! in iteration " + i
                        : iTestResult.getName() + " Failed! in iteration " + i
        );

        addResultTestCase(caseModel);

        System.out.println("🧹 Cleaning Filters...");
        selection.cleanFilters();
        Refresh.page();
    }

    @Test(dataProvider = "getBrandsData", dataProviderClass = ExcelFiltersDataProvider.class, enabled = true)
    void VerifyThatUserCanSearchForSmartTVByBrand(LinkedHashMap<String, String> brands) {
        caseId = PropertyManager.getInstance().getProperty("SmartTVBrandTest");
        String brand = brands.values().iterator().next(); // Get the first value directly
        executeFilterTest("brand-", brand);
    }

    @Test(dataProvider = "getSizesData", dataProviderClass = ExcelFiltersDataProvider.class, enabled = false)
    void VerifyThatUserCanSearchForSmartTVBySize(LinkedHashMap<String, String> sizes) {
        caseId = PropertyManager.getInstance().getProperty("SmartTVSizeTest");
        String size = sizes.values().iterator().next(); // Get the first value directly
        executeFilterTest("variants.normalizedSize-", size);
        //nota, con 115" falla
    }

    @Test(dataProvider = "getPricesData", dataProviderClass = ExcelFiltersDataProvider.class, enabled = false)
    void VerifyThatUserCanSearchForSmartTVByPriceRange(LinkedHashMap<String, String> prices) {
        caseId = PropertyManager.getInstance().getProperty("SmartTVPriceTest");
        String priceMin = prices.values().toArray()[0].toString();
        String priceMax = prices.values().toArray()[1].toString();
        int expectedProductCount = Integer.parseInt(prices.values().toArray()[2].toString());

        System.out.println("🔍 Searching for Smart TV by min price: " + priceMin + " and max price " + priceMax);

        // Step 1: Perform price Selection & Get Product Count
        selection.andShowMore().filterPriceAs(priceMin, priceMax);

        // Step 2: Validate Product Count
        IsProductDisplayed productCheck = getInstance(IsProductDisplayed.class).as(IsProductDisplayed.class);
        productCheck.asCount(expectedProductCount);

        System.out.println("✅ Test completed successfully for min price: " + priceMin + " and max price " + priceMax);
    }

    @Test(dataProvider = "getColorsData", dataProviderClass = ExcelFiltersDataProvider.class, enabled = false)
    void VerifyThatUserCanSearchForSmartTVByColor(LinkedHashMap<String, String> colors) {
        caseId = PropertyManager.getInstance().getProperty("SmartTVColorTest");
        String color = colors.values().toArray()[0].toString();
        int expectedProductCount = Integer.parseInt(colors.values().toArray()[1].toString());

        System.out.println("🔍 Searching for Smart TV by color: " + color);

        // Step 1: Perform Color Selection & Get Product Count
        selection.andShowMore().filterColorAs(color);

        // Step 2: Validate Product Count
        IsProductDisplayed productCheck = getInstance(IsProductDisplayed.class).as(IsProductDisplayed.class);
        productCheck.asCount(expectedProductCount);

        System.out.println("✅ Test completed successfully for Color" + color);

    }

    @Test(dataProvider = "getSalesByData", dataProviderClass = ExcelFiltersDataProvider.class, enabled = false)
    void VerifyThatUserCanSearchForSmartTVBySalesType(LinkedHashMap<String, String> salesBy) {
        caseId = PropertyManager.getInstance().getProperty("SmartTVSalesByTest");
        String saleBy = salesBy.values().iterator().next(); // Get the first value directly
        executeFilterTest("variants.sellernames-", saleBy);
    }

    @Test(dataProvider = "getScreenTechData", dataProviderClass = SQLFiltersDataProvider.class, enabled = false)
    void VerifyThatUserCanSearchForSmartTVByScreenTechnology(LinkedHashMap<Object, Object> screenTechnologies) {
        caseId = PropertyManager.getInstance().getProperty("SmartTVScreenTechnologyTest");
        String screenTechnology = screenTechnologies.values().toArray()[1].toString();
        executeFilterTest("dynamicFacets.pantallaatt-", screenTechnology);

        //nota, se encontró un bug donde no se muestra el filtro una vez seleccionado y removido
    }

    @Test(dataProvider = "getModelYearData", dataProviderClass = SQLFiltersDataProvider.class, enabled = false)
    void VerifyThatUserCanSearchForSmartTVByModelYear(LinkedHashMap<Object, Object> modelYears) {
        caseId = PropertyManager.getInstance().getProperty("SmartTVModelYearTest");
        String modelYear = modelYears.values().toArray()[1].toString();
        executeFilterTest("dynamicFacets.anodefabricacionvad-", modelYear);
    }

    @Test(dataProvider = "getResolutionData", dataProviderClass = SQLFiltersDataProvider.class, enabled = false)
    void VerifyThatUserCanSearchForSmartTVByResolution(LinkedHashMap<Object, Object> resolutions) {
        caseId = PropertyManager.getInstance().getProperty("SmartTVResolutionTest");
       String resolution = resolutions.values().toArray()[1].toString();
        executeFilterTest("dynamicFacets.definicionaudiovideoatt-", resolution);
    }

    @Test(dataProvider = "getProductTypeData", dataProviderClass = SQLFiltersDataProvider.class, enabled = false)
    void VerifyThatUserCanSearchForSmartTVByProductType(LinkedHashMap<Object, Object> productTypes) {
        caseId = PropertyManager.getInstance().getProperty("SmartTVProductTypeTest");
        String productType = productTypes.values().toArray()[1].toString();
        executeFilterTest("dynamicFacets.producttypesap-", productType);
    }

    @Test(dataProvider = "getSmartTVData", dataProviderClass = JsonFiltersDataProvider.class, enabled = false)
    void VerifyThatUserCanSearchForSmartTVUsingAllAvailableFilters(LinkedHashMap<String, String> smartTV) {
        caseId = PropertyManager.getInstance().getProperty("SmartTVMultipleFiltersTest");
        // Debug: Print all key-value pairs
        System.out.println("🔍 Debug: Received SmartTV data:");
        smartTV.forEach((key, value) -> System.out.println(key + " ➝ " + value));

        // 1. Brand
        String brand = smartTV.get("brand");
        if (brand != null) {
            executeFilterTest("brand-", brand);
        }

        // 2. Size
        String size = smartTV.get("size");
        if (size != null) {
            executeFilterTest("variants.normalizedSize-", size);
        }

        // 3. Price
        String price = smartTV.get("price");
        if (price != null) {
            // Parse out numeric min & max
            String[] range = PriceParser.parsePriceRange(price); // e.g. ["20000", "30000"]
            String min = range[0];
            String max = range[1];
            selection.andShowMore().filterPriceAs(min, max);
        }
    }

    /**
     * Generic method to execute filter-based test cases.
     * @param idPrefix The prefix for ID filtering
     * @param filterValue The value to filter (e.g., brand name, size, min/max price)
     */
    private void executeFilterTest(String idPrefix, String filterValue) {
        System.out.println("🔍 Searching for Smart TV by: " + filterValue);

        // Step 1: Perform Selection & Get Product Count
        int actualProductCount = selection.andShowMore().filterAs(idPrefix, filterValue);

        // Step 2: Validate Product Count (if applicable)
        IsProductDisplayed productCheck = getInstance(IsProductDisplayed.class).as(IsProductDisplayed.class);
        productCheck.asCount(actualProductCount);

        System.out.println("✅ Test completed successfully for: " + filterValue);
    }

}