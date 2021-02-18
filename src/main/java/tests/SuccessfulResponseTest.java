package tests;


import io.restassured.response.Response;
import org.testng.Reporter;
import org.testng.annotations.Test;
import util.TestUtil;


import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;

public class SuccessfulResponseTest {

    @Test(dataProvider = "testSuccessfulResponse", dataProviderClass = TestUtil.class)
    public void testSuccessfulResponse(String testcase, String value) throws Exception {

        baseURI = TestUtil.readProperties("URL.properties", "URL");

        String id = "/" + value;
        Reporter.log("Input -> " + value, true);

        Response rs = given().get(id);
        Reporter.log("Response -> " + rs.getBody().asString(), true);

        int statusCodeExpected = 200;

        TestUtil.performAssertions(rs, testcase, statusCodeExpected);

        if (rs.getStatusCode() == statusCodeExpected) {

            TestUtil.performResponseBodyAssertions(rs, testcase, value);
        }
    }
}
