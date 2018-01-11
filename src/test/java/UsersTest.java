import org.junit.Test;

import static io.restassured.RestAssured.when;


public class UsersTest extends TestBase{

    @Test
    public void shouldFetchListOfAllUsers() {
        when().
                get("/users").
        then().
                statusCode(200);
    }
}
