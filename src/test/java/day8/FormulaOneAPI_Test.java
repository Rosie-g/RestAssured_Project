package day8;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pojo.Driver;

import java.util.List;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class FormulaOneAPI_Test {

    @BeforeAll
    public static void init() {

        baseURI = "http://ergast.com";
        basePath = "/api/f1";

    }

    @DisplayName("GET /drivers.json and save result to pojo")
    @Test
    public void testDrivers() {

        // get first driver ad Driver POJO

        JsonPath jp = get("/drivers.json").jsonPath();
        Driver d1 = jp.getObject("MRData.DriverTable.Drivers[0]", Driver.class);
        System.out.println("d1 = " + d1);

        // Get all drivers as List<Driver>

        List<Driver> allDrivers = jp.getList("MRData.DriverTable.Drivers",Driver.class);
        System.out.println("allDrivers = " + allDrivers);


        //Print the name of all American drivers

        for (Driver driver: allDrivers){
            if (driver.getNationality().equals("American")){
                System.out.println("driver.getNationality() = " + driver.getNationality());
            }
        }
    }

    @AfterAll
    public static void cleanUp() {
        RestAssured.reset();
    }


}
