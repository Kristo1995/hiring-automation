package util;

import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.annotations.DataProvider;

import java.io.FileInputStream;
import java.util.Properties;

public class TestUtil {

    static String resourcePath ="src/main/resources/";
    static String testData = resourcePath + "Testcases.xlsx";

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

    @DataProvider(name="testStatusCodeEquals200")
    public static Object[][] testStatusCodeEquals200() throws Exception {
        return getExcelData(testData,"testStatusCodeEquals200");
    }

    @DataProvider(name="testStatusCodeEquals400")
    public static Object[][] testStatusCodeEquals400() throws Exception {
        return getExcelData(testData,"testStatusCodeEquals400");
    }

    @DataProvider(name="testStatusCodeEquals500")
    public static Object[][] testStatusCodeEquals500() throws Exception {
        return getExcelData(testData,"testStatusCodeEquals500");
    }
}
