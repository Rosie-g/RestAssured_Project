package day11;

import io.restassured.http.ContentType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pojo.Spartan;
import spartan_util.SpartanUtil;
import test_util.SpartanWithAuthBaseTest;
import static io.restassured.RestAssured.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import io.restassured.specification.ResponseSpecification;

import java.io.File;

import static io.restassured.module.jsv.JsonSchemaValidator.*;

public class SpartanPostSchemaTest extends SpartanWithAuthBaseTest {

    @DisplayName("Test Json Schema for POST Response")
    @Test
    public void testAddOneSpartanResponse(){

        Spartan bodyPojo = SpartanUtil.getRandomSpartanPOJO();

        given()
                .log().body()
                .auth().basic("admin","admin")
                .contentType(ContentType.JSON)
                .body(bodyPojo).
        when()
                .post("/spartans").
        then()
                .statusCode(201)
                .log().all()
                .body(matchesJsonSchemaInClasspath("SpartanPostJsonSchema.json"))
                .body(matchesJsonSchema(new File("src/test/resources/SpartanPostJsonSchema.json")));




    }

}
