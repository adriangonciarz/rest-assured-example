import io.restassured.response.Response;
import models.User;
import org.junit.Test;

import java.util.List;

public class UsersTest extends TestBase {

    @Test
    public void shouldFetchListOfAllUsers() {
        Response r = REQUEST.get("/users");

        r.then().
                log().body().
                assertThat().statusCode(200);

        List<Integer> ages = r.path("age");
        List<User> users = r.body().jsonPath().getList("", User.class);

        System.out.println(ages);

        for (User u : users) {
            System.out.println(u.first_name);
        }
    }
}
