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

@DisplayName("Soartan Test with path variable anq query param")
public class SpartanTest_PathVariableQueryParam extends SpartanNoAuthBaseTest {

    @Test
    public void getOneSpartan() {

        Response r1 =

                get("/spartans/16").prettyPeek();

        // I wanna provide 16 as path variable(parameter)
        // I wanna provide accept header

        given()
                .pathParam("spartan_id", 16)
                .header("Accept", "application/json").
                when()
                .get("/spartans/{spartan_id}")
                .prettyPeek()
        ;

        Response r2 =

                given()
                        // this is same as .header("Accept", "application/json")
                        .accept("application/json").
                        when()
                        .get("/spartans/{spartan_id}", 16)
                        .prettyPeek();


    }

    @DisplayName("logging the request")
    @Test
    public void getOneSpartanInLog() {

        Response response =

                given()
                        .log().all()
                        .accept("application/json")
                        .pathParam("id", 16).

                        when()
                        .get("/spartans/{id}")
                        .prettyPeek();

        assertThat(response.statusCode(), equalTo(200));
        assertThat(response.contentType(), is("application/json"));
        assertThat(response.path("name"), is("Sinclair"));


    }


}
