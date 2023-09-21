package day10;

import org.junit.jupiter.api.*;
import test_util.SpartanNoAuthBaseTest;

import java.io.File;

import static io.restassured.RestAssured.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertAll;
import static io.restassured.module.jsv.JsonSchemaValidator.*;


public class JsonSchemaValidationTest extends SpartanNoAuthBaseTest {

    @DisplayName("Check GET /spartans/{id} json schema")
    @Test
    public void testSpartanJsonSchema(){

        given()
                .pathParam("id", 43).
        when()
                .get("/spartans/{id}").
        then()
                .log().body()
                .statusCode(200)
        // check the response body against the schema file singleSpartanSchema.json we added under resources folder
                .body(matchesJsonSchemaInClasspath("singleSpartanSchema.json"));



    }

    @DisplayName("Check GET /spartans json schema")
    @Test
    public void testAllSpartansJsonSchema(){


        when()
                .get("/spartans").
        then()
                .body(matchesJsonSchemaInClasspath("allSpartansSchema.json"))
                // what if this schema was under day 10 package
                .body(matchesJsonSchema(new File("src/test/java/day10/allSpartansSchema.json")));

    }



}
