package day10;
import io.restassured.path.xml.XmlPath;
import org.junit.jupiter.api.*;

import java.util.List;
import java.util.Locale;

import static io.restassured.RestAssured.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertAll;

public class FormulaOneAPI_Test {

    @BeforeAll
    public static void init() {
        baseURI = "http://ergast.com";
        basePath = "/api/f1/";
    }

    @DisplayName("Test GET /drivers/{driver_id}")
    @Test
    public void testDriverOne() {

        // send request to driver to get info of driver_id alonso and save xml assert or assert in the chain
        XmlPath xp = given()
                        .pathParam("driver_id","alonso").
                when()
                        .get("/drivers/{driver_id}")
                        .xmlPath();
        String givenName = xp.getString("MRData.DriverTable.Driver.GivenName");
        System.out.println("givenName = " + givenName);
        String familyName = xp.getString("MRData.DriverTable.Driver.FamilyName");
        System.out.println("familyName = " + familyName);




    }

    @DisplayName("Test GET /drivers")
    @Test
    public void testAllDrivers() {

        XmlPath xp = get("/drivers").xmlPath();
        // get first given name
        String firstGivenName = xp.getString("MRData.DriverTable.Driver[0].GivenName");
        System.out.println("firstGivenName = " + firstGivenName);
        // get third nationality
        String thirdNationality = xp.getString("MRData.DriverTable.Driver[2].Nationality");
        System.out.println("thirdNationality = " + thirdNationality);
        // get all last names
        List<String> allNames = xp.getList("MRData.DriverTable.Driver.FamilyName");
        System.out.println("allNames = " + allNames);

    }

    @DisplayName("Test GET /drivers/{driver_id} get attribute")
    @Test
    public void testDriverOneAttribute() {

        XmlPath xp = given()
                .pathParam("driver_id","alonso").
                        when()
                .get("/drivers/{driver_id}")
                .xmlPath();

        String driverCode = xp.getString("MRData.DriverTable.Driver.@code");
        System.out.println("driverCode = " + driverCode);

        String url = xp.getString("MRData.DriverTable.Driver.@url");
        System.out.println("url = " + url);



    }




    @AfterAll
    public static void cleanup() {
        reset();
    }
}