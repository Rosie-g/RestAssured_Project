package day2;

import test_util.SpartanNoAuthBaseTest;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.*;
import test_util.SpartanNoAuthBaseTest;

import static org.hamcrest.MatcherAssert.assertThat;
import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;

public class VerifyingResponseInTheChain extends SpartanNoAuthBaseTest {

    @DisplayName("Verifying the GET /spartans/{id} response directly in the chain")
    @Test
    public void testOneSpartanInOneShot() {

        given()
               // .log().ifValidationFails() // this will log the request
                .pathParam("id", 16).
        when()
                .get("/spartans/{id}").
        then()
               // .log().all() // this will log the response
               // .log().body()
               // .log().ifValidationFails()
               // .log().headers()
               // .log().ifError()
                .log().ifStatusCodeIsEqualTo(200)
                .statusCode(200)
                .header("Content-Type", "application/json")
                .contentType("application/json")
                .body("id", is(16))
                .body("name",is("Sinclair"))
                .body("gender", is("Male"))
                .body("phone",is(9714460354l) )
        ;


    }
}
