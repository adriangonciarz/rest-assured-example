import io.restassured.RestAssured;
import org.junit.Test;

import static io.restassured.RestAssured.when;


public class UsersTest {

    @Test
    public void shouldFetchListOfAllUsers() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = 30040;
        when().
                get("/users").
        then().statusCode(200);
    }
}
