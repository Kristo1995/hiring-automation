package tests;


import io.restassured.response.Response;
import org.testng.Reporter;
import org.testng.annotations.Test;
import util.DataProviderUtil;
import util.TestUtil;


import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;

/*
This test class confirms that:
- Response Status Code = 200
- Response has a 36 character ID
- Response has a application/json Content Type
- Response body has the correct "initial" and "primes" values
 */
public class SuccessfulResponseTest {

    @Test(dataProvider = "testSuccessfulResponse", dataProviderClass = DataProviderUtil.class)
    public void testSuccessfulResponse(String testcase, String value) throws Exception {

        baseURI = TestUtil.readProperties("URL.properties", "URL");

        String id = "/" + value;
        Reporter.log("Input -> " + value, true);

        Response rs = given().get(id);
        Reporter.log("Response -> " + rs.getBody().asString(), true);

        int statusCodeExpected = 200;

        TestUtil.performBaseAssertions(rs, testcase, statusCodeExpected);

        if (rs.getStatusCode() == statusCodeExpected) {

            TestUtil.performResponseBodyAssertions(rs, testcase, value);
        }
    }
}
