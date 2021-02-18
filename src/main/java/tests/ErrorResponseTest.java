package tests;


import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;
import util.TestUtil;
import static io.restassured.RestAssured.*;

public class ErrorResponseTest {

    @Test(dataProvider = "testErrorResponse", dataProviderClass = TestUtil.class)
    public void testErrorResponse(String testcase, String value) throws Exception {

        baseURI = TestUtil.readProperties("URL.properties", "URL");

        String id = "/" + value;
        Reporter.log("Input -> " + value, true);

        Response rs = given().get(id);
        Reporter.log("Response -> " + rs.getBody().asString(), true);

        String contentType = rs.getContentType();
        Reporter.log("Content Type -> " + contentType, true);

        Assert.assertEquals(contentType, "application/json" , "Testcase " + testcase + " has incorrect Content Type:");

        String responseCode = rs.getHeaders().get("x-amzn-RequestId").getValue();
        Reporter.log("Unique Response Code -> " + responseCode, true);

        Assert.assertEquals(responseCode.length(), 36, "Testcase " + testcase + " does not have a Valid Unique Response Code:");

        int statusCode = rs.getStatusCode();
        Reporter.log("Status Code -> " + statusCode, true);

        Assert.assertEquals(statusCode, 500 , "Testcase " + testcase + " has incorrect Status Code:");

        int time = (int) rs.getTime();
        Reporter.log("Response Time in ms -> " + time, true);

        int timeLimit = (testcase.equals("1")) ? 5000 : 500;

        Assert.assertTrue(time <= timeLimit, "Testcase " + testcase + " has response time over 500ms:");
    }
}