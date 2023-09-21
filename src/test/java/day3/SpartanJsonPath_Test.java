package day3;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import test_util.SpartanNoAuthBaseTest;

import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.get;
import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;


public class SpartanJsonPath_Test extends SpartanNoAuthBaseTest {

    // http://54.197.45.30:8000/api/spartans/12

    @Test
    public void testOne(){

        Response response =

        given()
                .log().all()
                .pathParam("id",12).
        when()
                .get("/spartans/{id}")
                .prettyPeek() ;

        // using path method to extract data
        int myId = response.path("id");
        System.out.println("myId = " + myId);

        // Few meaning of JsonPath :
          // 1. Just like xpath -->> it is used to provide location of certain data
          // 2. JsonPath as a class coming from RestAssured to provide reusable methods to extract data
          // 3. jsonpath() method of Response object to get JsonPath object

        JsonPath jp = response.jsonPath(); // number for variable type, number 3 for the method
        jp.getInt("id");
        System.out.println("result = " + myId);

        long phoneNum = jp.getLong("phone");
        System.out.println("phoneNum = " + phoneNum);


        System.out.println("Save whole json object = "+jp.getMap(""));

        Map<String,Object> resultJsonInMap = jp.getMap("");
        System.out.println("resultJsonInMap = " + resultJsonInMap);

    }

    @DisplayName("Extract data from GET /spartans")
    @Test
    public void testGetAllSpartan(){

      //  Response response = get("/spartans");
      //  JsonPath jp = response.jsonPath();

        JsonPath jp = get("/spartans").jsonPath();

        // print first id in the json array response
        // [{},{},{}]
        System.out.println("jp.getInt(\"id[0]\") = " + jp.getInt("id[0]"));

        // print second json object name in the json array response
        System.out.println("jp.getString(\"name[1]\") = " + jp.getString("name[1]"));

        System.out.println("jp.getString(\"[0]\") = " + jp.getString("[0]"));

        System.out.println("jp.getMap(\"[0]\") = " + jp.getMap("[0]"));

        // print first id in the json array response
        System.out.println("jp.getInt(\"[0].id\") = " + jp.getInt("[0].id"));


    }

    @DisplayName("Extract data from GET /spartans/search ")
    @Test
    public void testGetSearchSpartan(){

        // http://54.197.45.30:8000/api/spartans/search
        // ?nameContains=&gender=Male

        JsonPath jp =
        given()
                .queryParam("nameContains","Dana")
                .queryParam("gender", "Female")
                .log().all().
        when()
                .get("/spartans/search")
                .prettyPeek()
                .jsonPath()
                ;

        // find out our first guy id, second guy name(in my example i don't have a lot of Dana , so i get only this
        // but if i have a lot of data i will use --->>>
        // content[0].id
        System.out.println("jp.getInt(\"content[0].id\") = " + jp.getInt("content[0].id"));
        System.out.println("jp.getString(\"content[0].name\") = " + jp.getString("content[0].name"));

        // store first jsonObject into a map
        Map<String,Object> firstJsonInMap = jp.getMap("content[0]");
        System.out.println("firstJsonInMap = " + firstJsonInMap);

    }

    @DisplayName("Saving json array fields into List")
    @Test
    public void testSavingJsonArrayFieldsIntoList(){
        JsonPath jp =
                given()
                        .queryParam("nameContains","k")
                        .queryParam("gender","Female")
                        .log().all().
                        when()
                        .get("/spartans/search")
                        .prettyPeek()
                        .jsonPath();

        // save all into the list
        System.out.println("jp.getList(\"content.id\") = " + jp.getList("content.id"));

        System.out.println("jp.getList(\"content.name\") = " + jp.getList("content.name"));

        System.out.println("jp.getList(\"content.phone\") = " + jp.getList("content.phone "));


        // getList method as teo overloaded versions
        // 1. jp.getList("json path here"); -->> the type of list will bw automatically determined
        List<Integer> allId = jp.getList("content.id") ;
        // 2. jp.getList("json path here", class type you want that list to have)
        List<Integer> allId2 = jp.getList("content.id",Integer.class);

        List<String> allNames = jp.getList("content.name");
        List<String> allNames2 = jp.getList("content.name",String.class);

        List<Long> allPhones = jp.getList("content.phone");
        List<Long> allPhones2 = jp.getList("content.phone",Long.class);

    }

    @DisplayName("Get List Practice for GET /spartans")
    @Test
    public void testGetListOutOfAllSpartans(){

        JsonPath jp = get("/spartans").jsonPath();

        // save the list into List object and assert thr size

        List<Integer> allId = jp.getList("id",Integer.class);
        assertThat(allId,hasSize(119));
        List<String> allNames = jp.getList("name",String.class);
        assertThat(allNames,hasSize(119));
        List<Long> allPhones = jp.getList("phone",Long.class);
        assertThat(allPhones,hasSize(119));


    }


}






