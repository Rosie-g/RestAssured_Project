package day10;

import io.restassured.path.xml.XmlPath;

import org.junit.jupiter.api.*;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

import static io.restassured.RestAssured.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertAll;

public class GOV_Data_XML_Test {

    // GET https://data.ct.gov/api/views/qm34-pq7e/rows.xml
    // save all the numbers in "unknown" element in the response into the list
    // save all the year into the list
    // find out the max number and year of that max number

    @DisplayName("Find out all the year max number of unknown race hired ")
    @Test
    public void testMax(){

        XmlPath xp = given()
                .baseUri("https://data.ct.gov")
                .basePath("api/views/qm34-pq7e").
         when()
                .get("rows.xml").xmlPath();

        List<Integer> unknownList = xp.getList("response.row.row.unknown",Integer.class);

        System.out.println("unknownList = " + unknownList);

        List<Integer> yearList = xp.getList("response.row.row.year");
        System.out.println("yearList = " + yearList);

        int maxUnknown = Collections.max(unknownList);
        System.out.println("maxUnknown = " + maxUnknown);

        // find index of that max number in the list so we can use it in the year
        int indexOfMaxNumber = unknownList.indexOf(maxUnknown);

        System.out.println("Year that most unknown race hired " + yearList.get(indexOfMaxNumber));



    }
}
