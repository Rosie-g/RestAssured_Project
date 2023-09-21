package day9;

import io.restassured.http.ContentType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import test_util.DB_Utility;
import test_util.LibraryAppBaseTest;

import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertAll;

public class Library_API_DB_Post_Test extends LibraryAppBaseTest {

    // Add a random book using POST /add_book
    // grab the id and write a query to get book information with this id
    // assert all data match exactly as it was posted

    @DisplayName("Add one book, Validate from DB")
    @Test
    public void testAddBookPersisted(){

        Map<String,Object> randomBookMapBody = getRandomBook();
        System.out.println("randomBookMap = " + randomBookMapBody);

        int newBookId = given()

                .header("X-LIBRARY-TOKEN", librarianToken)
                .contentType(ContentType.URLENC)
                .formParams(randomBookMapBody).
        when()
                .post("/add_book").
        then()
                .statusCode(200)// normally it returns 201, this one decided to return 200
                .log().body().
        extract()
                .jsonPath().getInt("book_id");


        System.out.println("newBookId = " + newBookId);

        DB_Utility.runQuery("SELECT * FROM books WHERE id = " + newBookId);
        // DB_Utility.displayAllData();

        Map<String,String> dbResultMap = DB_Utility.getRowMap(1);
        System.out.println("dbResultMap = " + dbResultMap);

        assertThat(dbResultMap.get("name"), is(randomBookMapBody.get("name")));

        assertAll(
                () -> assertThat(dbResultMap.get("name"), is(randomBookMapBody.get("name"))),
                () -> assertThat(dbResultMap.get("id"), is(newBookId+"")),
                () -> assertThat(dbResultMap.get("isbn"), is(randomBookMapBody.get("isbn")))

        );
    }
}
