package test_util;

import io.restassured.RestAssured;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;

public abstract class SpartanNoAuthBaseTest {

    @BeforeAll
    public static void init() {

        // This will set the part of URL at RestAssured
        RestAssured.baseURI = "http://18.213.0.89:8000";
        RestAssured.basePath = "/api";

        String url = ConfigurationReader.getProperty("spartan.database.url");
        String username = ConfigurationReader.getProperty("spartan.database.username");
        String password = ConfigurationReader.getProperty("spartan.database.password");
        DB_Utility.createConnection(url,username,password);

    }

    @AfterAll
    public static void cleanUp() {
        RestAssured.reset();
        DB_Utility.destroy();
    }


}
