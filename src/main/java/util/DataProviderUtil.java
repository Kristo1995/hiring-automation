package util;

import org.testng.annotations.DataProvider;

public class DataProviderUtil{

    static String resourcePath ="src/main/resources/";
    static String testData = resourcePath + "Testcases.xlsx";

    @DataProvider(name="testSuccessfulResponse")
    public static Object[][] testSuccessfulResponse() throws Exception {
        return TestUtil.getExcelData(testData,"testSuccessfulResponse");
    }

    @DataProvider(name="testNonPositiveIntegerResponse")
    public static Object[][] testNonPositiveIntegerResponse() throws Exception {
        return TestUtil.getExcelData(testData,"testNonPositiveIntegerResponse");
    }

    @DataProvider(name="testErrorResponse")
    public static Object[][] testErrorResponse() throws Exception {
        return TestUtil.getExcelData(testData,"testErrorResponse");
    }

    @DataProvider(name="testPerformance")
    public static Object[][] testPerformance() throws Exception {
        return TestUtil.getExcelData(testData,"testPerformance");
    }
}
