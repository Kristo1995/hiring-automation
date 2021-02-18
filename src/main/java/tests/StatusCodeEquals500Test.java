package tests;


import io.restassured.response.Response;
import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;
import util.TestUtil;
import static io.restassured.RestAssured.*;

public class StatusCodeEquals500Test {

    @Test(dataProvider = "testStatusCodeEquals500", dataProviderClass = TestUtil.class)
    public void testStatusCodeEquals500(String testcase, String value) throws Exception {

        baseURI = TestUtil.readProperties("URL.properties", "URL");

        String id = "/" + value;
        System.out.println("Input -> " + value);

        Response rs = given().get(id);
        System.out.println("Response -> " + rs.getBody().asString());

        int statusCode = rs.getStatusCode();
        System.out.println("Status Code -> " + statusCode);

        Assert.assertEquals(statusCode, 500 , "Testcase " + testcase + " has incorrect Status Code:");
    }

    @Test(dataProvider = "testPostStatusCodeEquals500", dataProviderClass = TestUtil.class)
    public void testPostStatusCodeEquals500(String testcase, String value) throws Exception {

        baseURI = TestUtil.readProperties("URL.properties", "URL");

        String id = "/" + value;
        System.out.println("Input -> " + value);

        JSONObject request = new JSONObject();
        request.put("id", "test");
        System.out.println("Request -> " + request.toString());

        Response rs = given().post(id);
        System.out.println("Response -> " + rs.getBody().asString());

        int statusCode = rs.getStatusCode();
        System.out.println("Status Code -> " + statusCode);

        Assert.assertEquals(statusCode, 500 , "Testcase " + testcase + " has incorrect Status Code:");
    }
}