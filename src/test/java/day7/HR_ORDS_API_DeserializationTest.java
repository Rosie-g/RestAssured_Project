package day7;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pojo.Country;
import pojo.Department;
import pojo.Employee;
import pojo.Region;
import test_util.HR_ORDS_API_BaseTest;

import java.util.List;

import static io.restassured.RestAssured.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class HR_ORDS_API_DeserializationTest extends HR_ORDS_API_BaseTest {


    // Send request to /regions and save the result into List<Region>
    // assert the list has size 4

    @DisplayName("GET /regions")
    @Test
    public void testGetAllRegionAndSaveToListOfPOJO(){

        List<Region> list =
                get("/regions")
                .jsonPath()
                .getList("items",Region.class);
        System.out.println("list = " + list);
    }

    @DisplayName("GET /countries")
    @Test
    public void testAllCountries() {

//        Country c1 = new Country("AR","Argentina",1);
//        System.out.println("c1 = " + c1);

        Country thirdCountry = get("/countries").jsonPath().getObject("items[2]",Country.class) ;
        System.out.println("thirdCountry = " + thirdCountry);

        List<Country> allCountries = get("/countries").jsonPath().getList("items",Country.class);
        //System.out.println("allCountries = " + allCountries);
        allCountries.forEach(p-> System.out.println(p));
    }

    @DisplayName("GET /employees")
    @Test
    public void testAllEmployees() {

        // get the first employee and save it into POJO object

        Employee e1 = get("/employees").jsonPath().getObject("items[0]",Employee.class);
        System.out.println("e1 = " + e1);

    }

    // Till this moment we have been naming our pojo class field names
    //   to match exact name from json field
    //  and yet there will be situations that the json field name
    //    does not really work for java naming convention
    // we want to be able to name the POJO field anything we want

    // Create a POJO class called Departments with below fields
    // departmentId , name , manager_id , location_id



    @DisplayName("GET /departments")
    @Test
    public void testAllDepartments() {
        Department d1 = get("/departments").jsonPath()
                .getObject("items[0]",Department.class);
        System.out.println("d1 = " + d1);


    }



    }
