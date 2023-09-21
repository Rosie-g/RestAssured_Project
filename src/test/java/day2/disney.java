package day2;

import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Locale;

import static io.restassured.RestAssured.*;

public class disney {


    @BeforeAll
    public static void init(){

        baseURI = "https://api.disneyapi.dev";
      //  basePath = "/characters";

    }

    @Test
    public void testAllCharacters(){

        given()
                .accept(ContentType.JSON).
        when()
                .get("/characters").
then()
                .contentType(ContentType.JSON)
        .log().all()
        .log().body();


    }
}
