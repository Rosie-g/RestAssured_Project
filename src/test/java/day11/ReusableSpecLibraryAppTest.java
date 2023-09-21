package day11;

import io.restassured.specification.ResponseSpecification;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import test_util.LibraryAppBaseTest;

import static io.restassured.RestAssured.*;

public class ReusableSpecLibraryAppTest extends LibraryAppBaseTest {

    @DisplayName("GET /dashboard_stats")
    @Test
    public void testDashboardStats() {

        given()
                .spec(libraryRequestSpec).
        when()
                .get("/dashboard_stats").
        then()
                .spec(libraryResponseSpec);


    }


    @DisplayName("GET /get_all_users")
    @Test
    public void testGetAllUser(){

        // in class task
        // use the Reusable request specification
        // we build to send the request to get all users

        // build reusable Response specification
        //  status code of 200
        //  content type of json
        //  and log all
        // Add it to the BaseTest

        // try reusing it in both of the tests in this class


        given()
                .spec(libraryRequestSpec).
        when()
                .get("/get_all_users").
        then()
                .spec(getDynamicResponseSpec(200));

    }

    public static ResponseSpecification getDynamicResponseSpec(int statusCode){

        return expect().statusCode(statusCode);
    }
}
