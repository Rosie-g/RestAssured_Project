package day3;

import test_util.SpartanNoAuthBaseTest;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import pojo.Spartan;
import test_util.SpartanNoAuthBaseTest;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.*;
import test_util.SpartanNoAuthBaseTest;

import java.io.File;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class SpartanPost_NegativeTest extends SpartanNoAuthBaseTest {

    @DisplayName("Post request without content type expect 415")
    @Test
    public void test1() {

        Spartan sp = new Spartan("B21", "Male", 6457897635l);

        given()
                .log().body()
                .body(sp).
        when()
                .post("/spartans").
        then()
                .log().all()
                .statusCode(415);


    }

    @DisplayName("Post request without valid json expect 400")
    @Test
    public void test2() {

        given()
                .log().all()
                .contentType(ContentType.JSON)
                .body(" BAD JSON STRUCTURE HERE ").
        when()
                .post("/spartans").
        then()
                .log().all()
                .statusCode(400)
        ;

    }

    @DisplayName("Post request with valid json expect, bad name expect 400 with detailed name error message")
    @Test
    public void test3() {

        Spartan sp = new Spartan("1", "Male", 6457897635l);

        given()
                .log().body()
                .contentType(ContentType.JSON)
                .body(sp).
        when()
                .post("/spartans").
        then()
                .log().all()
                .statusCode(400)
                .body("message", is("Invalid Input!"))
                .body("errorCount", equalTo(1))
                .body("errors[0].reason", is("name should be at least 2 character and max 15 character"))

        ;


    }

    @DisplayName("Post request with bad name, phone - expect 400 with detailed name, phone error message")
    @Test
    public void test4() {

        Spartan sp = new Spartan("2", "Male", 7897635);

        given()
                .log().body()
                .contentType(ContentType.JSON)
                .body(sp).
        when()
                .post("/spartans").
        then()
                .log().all()
                .statusCode(400);


    }

    @DisplayName("Post request with bad name, phone, gender - expect 400 with 3 errors")
    @Test
    public void test5() {

        Spartan sp = new Spartan("2", "Male", 7897635);

        given()
                .log().body()
                .contentType(ContentType.JSON)
                .body(sp).
        when()
                .post("/spartans").
        then()
                .log().all()
                .statusCode(400);




    }

}
