package day1;

import io.restassured.response.Response;
import org.junit.jupiter.api.*;

import static org.hamcrest.MatcherAssert.assertThat;

import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;

@DisplayName("Intro to RestAssured")
public class RestAssured_Intro {

    @DisplayName("Testing hello endpoint")
    @Test
    public void testHelloEndPoint() {

        // http://54.197.45.30:8000/api/hello
        // Save the response into the object with type Response
        Response response = get("http://54.197.45.30:8000/api/hello");
        // extracting information from Response object
        // getting status code
        System.out.println("response.statusCode() = " + response.statusCode());
        System.out.println("response.getStatusCode() = " + response.getStatusCode());

        // getting specific header
        System.out.println("response.getHeader() = " + response.getHeader("Content-Type"));

        System.out.println("response.header(\"Date\") = " + response.header("Date"));

        // getting content type header using ready method
        System.out.println("response.getContentType() = " + response.getContentType());

        // getting the time of execution
        System.out.println("response.time() = " + response.time());

        // getting body as String
        System.out.println("response.asString() = " + response.asString());
        // System.out.println("response.asPrettyString() = " + response.asPrettyString());

        assertThat(response.statusCode(), is(200));
        assertThat(response.contentType(), is("text/plain;charset=UTF-8"));
        assertThat(response.contentType(), startsWith("text/plain"));
        assertThat(response.asString(), is("Hello from Sparta"));

        // printing the result
        // prettyPrint() -->> print and return String
        // prettyPeek()  -->> print and return same Response Object

        System.out.println("response.prettyPrint() = " + response.prettyPrint());
        System.out.println("response.prettyPeek() = " + response.prettyPeek());

    }

    @DisplayName("Testing GET/api/spartans/{id} Endpoint ")
    @Test
    public void testSingleSpartan() {

        // Send request to GET http://54.197.45.30:8000/api
        // Save the response and print out whole response

        Response response = get("http://54.197.45.30:8000/api/spartans/21").prettyPeek();

        assertThat(response.statusCode(), is(equalTo(200)));
        assertThat(response.contentType(),is("application/json"));
        assertThat(response.header("Connection"), equalTo("keep-alive") );

        // you can use this to compare pretty format and regular format
        // System.out.println("response.asString() = " + response.asString());

        // getting the field value of Json Body
        // path method
        System.out.println("response.path(\"id\") = " + response.path("id"));
        System.out.println("response.path(\"name\") = " + response.path("name"));
        System.out.println("response.path(\"gender\") = " + response.path("gender"));
        System.out.println("response.path(\"phone\") = " + response.path("phone"));

        // save id and name into specific data type
        int myId = response.path("id");
        String name = response.path("name");
        long phone = response.path("phone");

        System.out.println("myId = " + myId);
        System.out.println("name = " + name);
        System.out.println("phone = " + phone);

    }


}
