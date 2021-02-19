package tests;


import io.restassured.response.Response;
import org.testng.Reporter;
import org.testng.annotations.Test;
import util.DataProviderUtil;
import util.TestUtil;
import static io.restassured.RestAssured.*;

/*
This test class confirms that:
- Response Status Code = 500
- Response has a 36 character ID
- Response has a application/json Content Type
 */
public class ErrorResponseTest {

    @Test(dataProvider = "testErrorResponse", dataProviderClass = DataProviderUtil.class)
    public void testErrorResponse(String testcase, String value) throws Exception {

        baseURI = TestUtil.readProperties("URL.properties", "URL");

        String id = "/" + value;
        Reporter.log("Input -> " + value, true);

        Response rs = given().get(id);
        Reporter.log("Response -> " + rs.getBody().asString(), true);

        int statusCodeExpected = 500;

        TestUtil.performBaseAssertions(rs, testcase, statusCodeExpected);
    }
}