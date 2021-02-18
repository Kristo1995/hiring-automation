package tests;


import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.Assert;
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

        String contentType = rs.getContentType();
        Reporter.log("Content Type -> " + contentType, true);

        Assert.assertEquals(contentType, "application/json" , "Testcase " + testcase + " has incorrect Content Type:");

        String responseCode = rs.getHeaders().get("x-amzn-RequestId").getValue();
        Reporter.log("Unique Response Code -> " + responseCode, true);

        Assert.assertEquals(responseCode.length(), 36, "Testcase " + testcase + " does not have a Valid Unique Response Code:");

        int statusCode = rs.getStatusCode();
        Reporter.log("Status Code -> " + statusCode, true);

        Assert.assertEquals(statusCode, 200 , "Testcase " + testcase + " has incorrect Status Code:");

        int time = (int) rs.getTime();
        Reporter.log("Response Time in ms -> " + time, true);

        int timeLimit = (testcase.equals("1")) ? 5000 : 500;

        Assert.assertTrue(time <= timeLimit, "Testcase " + testcase + " has response time over 500ms:");

        if (statusCode == 200) {
            JsonPath jsonPathEvaluator = rs.jsonPath();
            String actualResponseInitial = jsonPathEvaluator.get("initial").toString();

            Assert.assertEquals(actualResponseInitial, value, "Testcase " + testcase + " has incorrect Response Initial Value:");

            String actualResponsePrimes = jsonPathEvaluator.get("primes").toString();

            int i;
            int num;
            StringBuilder primes = new StringBuilder();

            for (i = 1; i <= Integer.parseInt(value); i++)
            {
                int counter = 0;
                for(num = i; num >= 1; num--)
                {
                    if(i % num == 0)
                    {
                        counter = counter + 1;
                    }
                }
                if (counter == 2)
                {
                    primes.append(i).append(", ");
                }
            }

            if (primes.length() > 1) {
                primes.setLength(primes.length() - 2);
            }

            primes = new StringBuilder("[" + primes + "]");

            Assert.assertEquals(actualResponsePrimes, primes.toString(), "Testcase " + testcase + " has incorrect Response Primes Value:");
        }
    }
}
