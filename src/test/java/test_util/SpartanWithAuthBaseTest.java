package test_util;

import io.restassured.RestAssured;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import test_util.ConfigurationReader;
import test_util.DB_Utility;

public abstract class SpartanWithAuthBaseTest {


    @BeforeAll
    public static void init() {

        // This will set the part of URL at RestAssured
        RestAssured.baseURI = "http://18.213.0.89:7000";
        RestAssured.basePath = "/api";


    }

    @AfterAll
    public static void cleanUp() {
        RestAssured.reset();
        DB_Utility.destroy();
    }
}
