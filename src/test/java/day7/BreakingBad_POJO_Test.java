package day7;

import static io.restassured.RestAssured.*;

import org.junit.jupiter.api.*;
import pojo.Character;

import java.util.List;

public class BreakingBad_POJO_Test {

    @BeforeAll
    public static void init() {
        baseURI = "https://www.breakingbadapi.com";
        basePath = "/api";
    }

    @AfterAll
    public static void cleanup() {
        reset();
    }

    //https://www.breakingbadapi.com/api/characters
    @DisplayName("GET /characters")
    @Test
    public void testPracticeDeserialization() {

        // Send request to GET https://www.breakingbadapi.com/api/characters
        // save first item into Character pojo
        Character c1 = get("/characters").jsonPath()
                .getObject("[0]", Character.class);
        System.out.println("c1 = " + c1);

        // save all items into List<Character>
        List<Character> allCharacters = get("/characters").jsonPath()
                .getList("", Character.class);
        // System.out.println("allCharacters = " + allCharacters);

        allCharacters.forEach(each -> System.out.println(each));

        // print all character name who appeared exactly 5 times (all 5 seasons)  --- check appearance list size

        System.out.println("==========================================================");

        for (Character each : allCharacters) {
            if (each.getAppearance().size() == 5) {
                System.out.println(each.getName());
            }
        }

        // find out all characters showed up only in season 3

        System.out.println("==============================================");

        for (Character each : allCharacters) {
            if (each.getAppearance().size() == 1 && each.getAppearance().get(0) == 3) {
                System.out.println("each = " + each);
            }
        }


    }

}
