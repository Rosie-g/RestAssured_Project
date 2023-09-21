package test_util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import test_util.DB_Utility;
import test_util.SpartanNoAuthBaseTest;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class SpartanSearchTest extends SpartanNoAuthBaseTest {

    @DisplayName("Test GET /spartans/search result with DB result")
    @Test
    public void testSearch() {

        // nameContains a and gender Female

        String query = "SELECT * FROM SPARTANS WHERE LOWER(NAME) LIKE '%a%' and GENDER = 'Female'";

        DB_Utility.runQuery(query);
       // DB_Utility.displayAllData();

        int expectedCount = DB_Utility.getRowCount();
        System.out.println("count = " + expectedCount);

        given()
                .queryParam("nameContains", "a")
                .queryParam("gender","Female").
        when()
                .get("/spartans/search").

        then()
                .statusCode(200)
                .body("totalElement", equalTo(expectedCount))
                .body("content", hasSize(expectedCount));


    }

    @ParameterizedTest
    @CsvSource({
            "e, Male",
            "le, Female",
            "k, Male",
            "g, Male",
            "u, Female",
            "f, Male"
    })
    public void testSearchParameterizedTest(String nameArg, String genderArg){

        System.out.println("nameArg = " + nameArg);
        System.out.println("genderArg = " + genderArg);

        String query = "SELECT * FROM SPARTANS WHERE LOWER(NAME) LIKE '%" + nameArg + "%' and GENDER = '"+ genderArg+"'";
        System.out.println("query = " + query);
        DB_Utility.runQuery(query);

        int expectedCount = DB_Utility.getRowCount();
        System.out.println("expectedCount = " + expectedCount);

        given()
                .queryParam("nameContains", nameArg)
                .queryParam("gender",genderArg).
        when()
                .get("/spartans/search").

        then()
                .statusCode(200)
                .body("totalElement", equalTo(expectedCount))
                // OPTIONALLY , continue from here and check
                // if every item name containsString ignore case what we search for
                .body("content.name", everyItem(containsStringIgnoringCase(nameArg) ) )
                // if every item gender is what we search for
                .body("content.gender", everyItem(is(genderArg)))
                ;





    }


}
