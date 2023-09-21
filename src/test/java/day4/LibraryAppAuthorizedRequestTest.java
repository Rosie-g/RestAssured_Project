package day4;

import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import pojo.Spartan;
import test_util.SpartanNoAuthBaseTest;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.*;
import test_util.SpartanNoAuthBaseTest;

import java.io.File;
import java.util.*;

import static org.hamcrest.MatcherAssert.assertThat;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import test_util.LibraryAppBaseTest;

public class LibraryAppAuthorizedRequestTest extends LibraryAppBaseTest {

    private static String myToken = getToken("librarian69@library","KNPXrm3S");

    @DisplayName("GET /get_user_by_id/{user_id}")
    @Test
    public void testOneUser(){

        System.out.println("myToken = " + myToken);
        given()
                .header("x-library-token",myToken)
                .pathParam("user_id",1).
        when()
                .get("/get_user_by_id/{user_id}").
        then()
                .log().all()
                .statusCode(200);

    }

    @DisplayName("GET /get_all_users")
    @Test
    public void testGetAllUsers(){

        System.out.println("myToken = " + myToken);

        List<String> allNames =

        given()
                .header("x-library-token", myToken).
        when()
                .get("/get_all_users").
        then()
                //.log().all()
                .statusCode(200).
        extract()
                // extracting jsonPath so we can call getList method
                .jsonPath()
                .getList("name", String.class)
        ;

        // assert the size is 8645
        assertThat(allNames, hasSize(8665));
        // print the unique names count
        Set<String> uniqueNames = new HashSet<>(allNames);
        System.out.println("uniqueNames.size() = " + uniqueNames.size());


    }

    @DisplayName("POST /add_book")
    @Test
    public void testAddOneBook(){

        /*
name Awesome book
isbn IMDBS132
year 2019
author Ike
book_category_id 2
description good book
         */

        Map<String,Object> newBook = getRandomBook();

        int newBookId =
        given()
                .log().all()
                .header("x-library-token",librarianToken)
                .contentType(ContentType.URLENC)
                // using formParams we can pass multiple params in one shot
                .formParams(newBook).
        when()
                .post("/add_book").
        then()
                .log().all()
                .statusCode(200).
        extract()
                .jsonPath().getInt("book_id")

                ;

        //System.out.println("getRandomBook() = " + getRandomBook());

        // send additional request GET /get_book_by_id/{book_id}
        given()
                .header("x-library-token",librarianToken)
                .log().uri()
                .pathParam("book_id",newBookId).
        when()
                .get("/get_book_by_id/{book_id}").
        then()
                .log().body()
                .statusCode(200)
                .body("id",is(newBookId+""))
                .body("name",is(newBook.get("name")))
                .body("isbn",is(newBook.get("isbn")))
                .body("year",is(newBook.get("year")+""))
                .body("author",is(newBook.get("author")))
                .body("book_category_id",is(newBook.get("book_category_id")+""))
                .body("description",is(newBook.get("description")))


        ;



    }



}
