package day3;

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
import static io.restassured.RestAssured.* ;
import static org.hamcrest.Matchers.* ;

public class SpartanPostingData_Test extends SpartanNoAuthBaseTest{

        @DisplayName("POST /spartans with String")
        @Test
        public void testPostDataWithStringBody(){

            String postStringBody = "{\n" +

                    "            \"name\": \"Jack\",\n" +
                    "            \"gender\": \"Male\",\n" +
                    "            \"phone\": 8716150217\n" +
                    "        }";

            given()
                    .log().all()
//                    .header("Content-Typo", "application/json")
//                    .contentType("application/json")
                    .contentType(ContentType.JSON) // this is providing header to the request
                    .body(postStringBody).
            when()
                    .post("/spartans").
            then()
                    .log().all()
                    .statusCode(is(201))
                    .contentType(ContentType.JSON) // this is asserting response header in json
                    .body("success", is("A Spartan is Born!"))
                    .body("data.name", is("Jack"))
                    ;

        }

    @DisplayName("POST /spartans with external file")
    @Test
    public void testPostDataWithJsonFileAsBody(){

            // singleSpartan.json with below content
             /*

             "name": "Leila",
             "gender": "Female",
             "phone": 8700150200

         */

        File jsonFile = new File("singleSpartan.json") ;

        given()
                .log().all()
                .contentType(ContentType.JSON)
                .body(jsonFile).
        when()
                .post("/spartans").
        then()
                .log().all()
                .statusCode(201)
                ;


    }

    @DisplayName("POST /spartans with Map Object")
    @Test
    public void testPostDataWithMapObjectAsBody() {

            /*

             "name": "Riza",
             "gender": "Female",
             "phone": 8700678201

             */

        Map<String,Object> bodyMap = new LinkedHashMap<>();
        bodyMap.put("name", "Riza");
        bodyMap.put("gender", "Female");
        bodyMap.put("phone", 8700678201l);

        System.out.println("bodyMap = " + bodyMap);

        given()
                .log().all()
                .contentType(ContentType.JSON)
                .body(bodyMap).
        when()
                .post("/spartans").
        then()
                .log().all()
                .statusCode(201)
        ;

    }

    @DisplayName("POST /spartans with POJO")
    @Test
    public void testPostDataWithPOJOAsBody(){

            Spartan sp = new Spartan("Mike", "Male",7465498364l);
           // turn into below

        /*
           {  "name": "Mike",
             "gender": "Male",
             "phone": 7465498364
            }

         */

        System.out.println("sp = " + sp);

        given()
                .log().all()
                .contentType(ContentType.JSON)
                .body(sp).
        when()
                .post("/spartans").
        then()
                .log().all()
                .statusCode(201)
        ;




    }

}
