package day2;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.*;
import test_util.SpartanNoAuthBaseTest;

import static org.hamcrest.MatcherAssert.assertThat;
import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;

public class BreakingBad_Test {

    // https://www.breakingbadapi.com/api/characters?name=Walter

    @BeforeAll
    public static void init(){

        baseURI = "https://www.breakingbadapi.com";
        basePath = "/api";
    }

    @AfterAll
    public static void cleanUp() {
        reset();
    }

    @Test
    public void testFilterCharacterName(){

        given()
                .log().uri()
                .queryParam("name", "Walter").
        when()
                .get("/characters").
        then()
                .log().all()
                // and() this is just for readability
                .statusCode(200)
                .contentType("application/json; charset=utf-8")
                ;

    }

    @DisplayName("Test GET /characters/{char_id}")
    @Test
    public void testOneCharacter(){

        given()
                .log().uri()
                .pathParam("char_id", 1).
        when()
                .get("/characters/{char_id}").
        then()
                .log().all()
                .statusCode(200)
                .contentType("application/json; charset=utf-8")
        ;

    }

    @DisplayName("Test GET /episodes/{episode_id}")
    @Test
    public void test1Episode(){

        given()
                .pathParam("episode_id", 60)
                .log().all().

        when()
                .get("/episodes/{episode_id}").

        then()
                .statusCode(200)
                .contentType(ContentType.JSON);


    }

}
