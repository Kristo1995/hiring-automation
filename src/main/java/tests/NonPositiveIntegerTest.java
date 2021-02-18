package tests;


import io.restassured.response.Response;
import org.testng.Reporter;
import org.testng.annotations.Test;
import util.TestUtil;
import static io.restassured.RestAssured.*;

public class NonPositiveIntegerTest {

    @Test(dataProvider = "testNonPositiveIntegerResponse", dataProviderClass = TestUtil.class)
    public void testNonPositiveIntegerResponse(String testcase, String value) throws Exception {

        baseURI = TestUtil.readProperties("URL.properties", "URL");

        String id = "/" + value;
        Reporter.log("Input -> " + value, true);

        Response rs = given().get(id);
        Reporter.log("Response -> " + rs.getBody().asString(), true);

        int statusCodeExpected = 400;

        TestUtil.performAssertions(rs, testcase, statusCodeExpected);
    }
}
