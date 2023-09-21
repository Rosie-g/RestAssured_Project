package day10;

import io.restassured.path.xml.XmlPath;
import org.junit.jupiter.api.*;

import java.util.List;
import java.util.Locale;

import static io.restassured.RestAssured.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertAll;


public class MovieAPIXml_Test {


    @DisplayName("Get movie attributes in xml")
    @Test
    public void testMovieAttributes(){

        XmlPath xp = given()
                .baseUri("http://www.omdbapi.com/")
                .queryParam("apikey","8744abdb")
                .queryParam("t","Superman")
                .queryParam("r","xml").
        when()
                .get()
                .xmlPath();

        String title = xp.getString("root.movie.@title") ;
        System.out.println("title = " + title);


    }
    @DisplayName("Get movies attributes in xml")
    @Test
    public void testAllMovieAttributes(){

        XmlPath xp = given()
                .baseUri("http://www.omdbapi.com")
                .queryParam("apikey","8744abdb")
                .queryParam("s","WandaVision")
                .queryParam("r","xml").
                        when()
                .get()
                .xmlPath() ;
        List<String> allTitles = xp.getList("root.result.@title", String.class);
        System.out.println("allTitles = " + allTitles);


    }


    }





