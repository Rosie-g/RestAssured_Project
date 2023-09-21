package day6;

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
public class HR_ORDS_API_Practice {

    // http://18.235.32.166:1000/ords/hr/api/regions

    @BeforeAll
    public static void init(){

        baseURI = "http://18.235.32.166:1000";
        basePath = "/ords/hr/api" ;

    }


    @DisplayName("GET /regions test")
    @Test
    public void testAllRegions(){

     //   get("/regions").prettyPeek();
        given()
                .log().all().
        when()
                .get("/regions ").
        then()
                .log().all()
                .statusCode(200)
                .body("count",equalTo(4))
                .body("item",hasSize(4));



    }

    @DisplayName("GET /regions test")
    @Test
    public void testAllRegions2(){


        // another style

        Response response =

                given()
                        .log().all().
                when()
                        .get("/regions ");

                        assertThat(response.statusCode(),is(200));
                        assertThat(response.path("count"),equalTo(4));
                       // assertThat();
    }



    @AfterAll
    public static void cleanUp() {
        RestAssured.reset();
    }

}
