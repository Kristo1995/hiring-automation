package tests;


import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import util.TestUtil;
import static io.restassured.RestAssured.*;

public class StatusCodeEquals200Test {

    @Test(dataProvider = "testStatusCodeEquals200", dataProviderClass = TestUtil.class)
    public void testStatusCodeEquals200(String testcase, String value) throws Exception {

        baseURI = TestUtil.readProperties("URL.properties", "URL_PRIMES");

        String id = "/" + value;
        System.out.println("Input -> " + value);

        Response rs = given().get(id);
        System.out.println("Response -> " + rs.getBody().asString());

        int statusCode = rs.getStatusCode();
        System.out.println("Status Code -> " + statusCode);

        Assert.assertEquals(statusCode, 200 , "Testcase " + testcase + " has incorrect Status Code:");
    }
}
