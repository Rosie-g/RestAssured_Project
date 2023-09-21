package day11;


import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import test_util.SpartanWithAuthBaseTest;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class SpecificationReuseTest extends SpartanWithAuthBaseTest {

    private static RequestSpecification requestSpec = given()
            .auth().basic("admin","admin")
            .contentType(ContentType.JSON);

    private static  ResponseSpecification responseSpec = expect()
            // adding the log in expectation we use LogDetail method
            .logDetail(LogDetail.ALL)
            .statusCode(200)
            .contentType(ContentType.JSON);

    //all tests in this class will use admin credentials
    // all tests will expect json result from response
    // all tests response status code expected to be 200
    // all tests response content type header is json

    @DisplayName("Admin GET /spartans and expect 200 and json")
    @Test
    public void testAdminGetAll(){


        given()
                .spec(requestSpec).
        when()
                .get("/spartans").
        then()
                .spec(responseSpec);

    }

    @DisplayName("Admin GET /spartans/{id} and expect 200 and json")
    @Test
    public void testAdminGetOne(){

        given()
                .spec(requestSpec)
                .pathParam("id",1).
//                .auth().basic("admin","admin")
//                .contentType(ContentType.JSON).
        when()
                .get("/spartans/{id}").
        then()
                //.log().body()
                .spec(responseSpec)
//                .statusCode(200)
//                .contentType(ContentType.JSON)
                .body("id", is(1))
                ;

    }




}
