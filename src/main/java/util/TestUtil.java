package util;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.Assert;
import org.testng.Reporter;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class TestUtil {

    static String resourcePath ="src/main/resources/";

    public static String readProperties(String fileName, String key) throws Exception{

        String fullFilename = resourcePath + fileName;
        FileInputStream fis = new FileInputStream(fullFilename);
        Properties props = new Properties();
        props.load(fis);

        return props.getProperty(key);
    }

    public static String[][] getExcelData(String fileName, String sheetName) throws Exception{

        FileInputStream fis = new FileInputStream(fileName);
        XSSFWorkbook workbook = new XSSFWorkbook(fis);
        XSSFSheet sheet = workbook.getSheet(sheetName);
        DataFormatter formatter = new DataFormatter();

        int numberOfColumns = sheet.getRow(1).getLastCellNum();
        int numberOfRows = sheet.getLastRowNum();

        String[][] excelData = new String[numberOfRows][numberOfColumns];

        for(int i = 1; i <= numberOfRows; i++){
            for (int j = 1; j <= numberOfColumns; j++){
                excelData[i-1][j-1] = formatter.formatCellValue(sheet.getRow(i).getCell(j-1));
            }
        }

        return excelData;
    }

    public static void performBaseAssertions(Response rs, String testcase, int statusCodeExpected){
        String contentType = rs.getContentType();
        Reporter.log("Content Type -> " + contentType, true);

        Assert.assertEquals(contentType, "application/json" , "Testcase " + testcase + " has incorrect Content Type:");

        String responseCode = rs.getHeaders().get("x-amzn-RequestId").getValue();
        Reporter.log("Unique Response Code -> " + responseCode, true);

        Assert.assertEquals(responseCode.length(), 36, "Testcase " + testcase + " does not have a Valid Unique Response Code:");

        int statusCode = rs.getStatusCode();
        Reporter.log("Status Code -> " + statusCode, true);

        Assert.assertEquals(statusCode, statusCodeExpected , "Testcase " + testcase + " has incorrect Status Code:");
    }

    public static void performResponseBodyAssertions(Response rs, String testcase, String value){
        JsonPath jsonPathEvaluator = rs.jsonPath();
        String actualResponseInitial = jsonPathEvaluator.get("initial").toString();

        Assert.assertEquals(actualResponseInitial, value, "Testcase " + testcase + " has incorrect Response Initial Value:");

        String actualResponsePrimes = jsonPathEvaluator.get("primes").toString();

        List<Integer> primes = new ArrayList<>();

        for (int i = 2; i <= Integer.parseInt(value); i++) {
            boolean isPrime = true;
            for (int j = 2; j <= Math.sqrt(i); j++) {
                if (i % j == 0) {
                    isPrime = false;
                    break;
                }
            }
            if (isPrime) {
                primes.add(i);
            }
        }

        Assert.assertEquals(actualResponsePrimes, primes.toString(), "Testcase " + testcase + " has incorrect Response Primes Value:");
    }

    public static void performPerformanceAssertions(Response rs, String testcase) {

        int time = (int) rs.getTime();
        Reporter.log("Response Time in ms -> " + time, true);

        if (testcase.equals("1")) {
            Assert.assertTrue(time <= 5000, "Testcase " + testcase + " has response time over 5000ms:");
        } else {
            Assert.assertTrue(time <= 500, "Testcase " + testcase + " has response time over 500ms:");
        }
    }
}
