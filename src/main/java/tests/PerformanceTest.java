package tests;


import io.restassured.response.Response;
import org.testng.Reporter;
import org.testng.annotations.Test;
import util.DataProviderUtil;
import util.TestUtil;
import static io.restassured.RestAssured.*;

/*
This test class confirms that:
- Response time is less than 5000ms for 1st request and less than 500ms for subsequent ones
 */
public class PerformanceTest {

    @Test(dataProvider = "testPerformance", dataProviderClass = DataProviderUtil.class)
    public void testPerformance(String testcase, String value) throws Exception {

        baseURI = TestUtil.readProperties("URL.properties", "URL");

        String id = "/" + value;
        Reporter.log("Input -> " + value, true);

        Response rs = given().get(id);
        Reporter.log("Response -> " + rs.getBody().asString(), true);

        TestUtil.performPerformanceAssertions(rs, testcase);
    }
}
