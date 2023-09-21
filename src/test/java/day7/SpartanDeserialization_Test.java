package day7;

import io.restassured.path.json.JsonPath;
import org.junit.jupiter.api.Test;
import pojo.SpartanPOJO;
import test_util.SpartanNoAuthBaseTest;
import org.junit.jupiter.api.DisplayName;
import test_util.SpartanNoAuthBaseTest;
import static io.restassured.RestAssured.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import io.restassured.response.Response;

import java.util.List;

public class SpartanDeserialization_Test extends SpartanNoAuthBaseTest {

    // Serialization : Java Object to Json (or any type of text)
    // De-Serialization : Json(text) to Java

    @DisplayName("GET /spartans/{id}")
    @Test
    public void testGetOneData(){

        given()
                .pathParam("id", 100).
        when()
                .get("/spartans/{id}").
        then()
                .log().body()
                .statusCode(200);

        //Send same request, store the result into SpartanPOJO object

        Response response =

        given()
                .pathParam("id", 100).
        when()
                .get("/spartans/{id}");

        SpartanPOJO sp = response.as(SpartanPOJO.class);
        System.out.println("sp = " + sp);

        JsonPath jp = response.jsonPath();
        SpartanPOJO sp1 = jp.getObject("",SpartanPOJO.class);
        System.out.println("sp1 = " + sp1);


    }

    @DisplayName("GET /spartans/search")
    @Test
    public void testSearch(){
        ///spartans/search?nameContains=a&gender=Male
        // send get request to above endpoint and save first object with type SpartanPOJO
        Response response =
                given()
                        .log().uri()
                        .queryParam("nameContains", "a")
                        .queryParam("gender", "Male").
                when()
                        .get("/spartans/search") ;//.prettyPeek() ;

        JsonPath jp = response.jsonPath();
        SpartanPOJO sp = jp.getObject("content[0]",SpartanPOJO.class);
        System.out.println("sp = " + sp);

        // this is how we can do whole thing in one chain ;

        SpartanPOJO sp1 = given()
                .log().uri()
                .queryParam("nameContains", "a")
                .queryParam("gender", "Male").
        when()
                .get("/spartans/search")
                .jsonPath()
                .getObject("content[0]",SpartanPOJO.class);

        System.out.println("sp1 = " + sp1);

    }

    @DisplayName("GET /spartans/search and save as List<SpartanPOJO>")
    @Test
    public void testSearchSaveList(){

        List<SpartanPOJO> list = given()
                .queryParam("nameContains", "a")
                .queryParam("gender", "Male").
        when()
                .get("/spartans/search")
                .jsonPath()
                .getList("content", SpartanPOJO.class);
        // something.class return type class to specify wht kind of Item you want to have in your list

        System.out.println("list = " + list);
        list.forEach(lala -> System.out.println( lala));


    }

}
