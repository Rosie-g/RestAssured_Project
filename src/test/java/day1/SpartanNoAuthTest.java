package day1;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.*;
import test_util.SpartanNoAuthBaseTest;
import static org.hamcrest.MatcherAssert.assertThat;
import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;

@DisplayName("Spartan App Get Requests")
public class SpartanNoAuthTest extends SpartanNoAuthBaseTest {

    // http://54.197.45.30:8000/api/spartans


    @Test
    public void sayHello() {

        // the actual request url you have sent is this
        // baseURI + basePath + "/hello/"
        get("/hello").prettyPeek();


    }

    @Test
    public void getAllSpartan() {

        // the actual request url you have sent is this
        // baseURI + basePath + "/hello/"
        get("/spartans").prettyPeek();


    }


}
