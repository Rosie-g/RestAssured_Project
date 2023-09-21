package day10;

import io.restassured.http.ContentType;
import io.restassured.path.xml.XmlPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import test_util.SpartanWithAuthBaseTest;

import java.util.List;

import static io.restassured.RestAssured.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;


public class XML_ResponseTest extends SpartanWithAuthBaseTest {

    @DisplayName("Test GET /spartans xml response")
    @Test
    public void testXML(){
        // provide credentials and provide header as xml and send request
        // assert status code 200
        // assert content type is xml
        // assert first data name is Wonder Woman

        given()
                .auth().basic("user","user")
                .accept(ContentType.XML).
        when()
                .get("/spartans").
        then()
                .log().all()
                .statusCode(200)
                .contentType(ContentType.XML)
                .body("List.item[0].name",is("Meade"))
                .body("List.item[0].id",is(("1")));


    }

    @DisplayName("Test GET /spartans xml response with XmlPath ")
    @Test
    public void testXML_extractData(){

        Response response =

        given()
                .auth().basic("user","user")
                .accept(ContentType.XML).
        when()
                .get("/spartans");

        XmlPath xp = response.htmlPath();
        int firstId = xp.getInt("List.item[0].id");
        System.out.println("firstId = " + firstId);
        String firstName = xp.getString("List.item[0].name");
        System.out.println("firstName = " + firstName);

        long thirdPhoneNumber = xp.getLong("List.item[2].phone");
        System.out.println("thirdPhoneNumber = " + thirdPhoneNumber);

        //get all Ids into String

        List<String> allIDs = xp.getList("List.item.id");
        System.out.println("allIDs = " + allIDs);
        System.out.println(allIDs.size());

        assertAll(

                () -> assertEquals(1,firstId),
                () -> assertEquals("Meade",firstName),
                () -> assertEquals(6105035231l,thirdPhoneNumber),
                () -> assertEquals(100,allIDs.size())




        );




    }


}
