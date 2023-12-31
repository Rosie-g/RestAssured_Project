package day9;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import test_util.SpartanWithAuthBaseTest;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;

public class SpartanWithAuthTest extends SpartanWithAuthBaseTest {

    @DisplayName("GET /spartans as public user expect 401")
    @Test
    public void testPublicUser(){

        when()
                .get("/spartans").
        then()
                .statusCode(401)
                .log().all();



    }

    @DisplayName("GET /spartans as admin user expect 200")
    @Test
    public void testAdminUser(){

        given()
                .auth().basic("admin","admin").
        when()
                .get("/spartans").
        then()
                .log().headers()
                .statusCode(200);

    }

    @DisplayName("DELETE /spartans/{id} as editor user expect 403")
    @Test
    public void testEditor(){

        given()
                .pathParam("id", 100)
                .auth().basic("editor","editor").
        when()
                .delete("/spartans/{id}").
        then()
                .log().all()
                .statusCode(403);




    }
}
