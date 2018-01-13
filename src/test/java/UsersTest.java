import io.restassured.response.Response;
import models.User;
import org.junit.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItem;
import static org.junit.Assert.assertEquals;

public class UsersTest extends TestBase {

    //The simplest case of Rest-Assured Usage: simple get and status code verification
    @Test
    public void shouldFetchListOfAllUsers() {
        REQUEST.get("/users")
                .then()
                    .statusCode(200);
    }

    //Here I use more built-in assertions for checking simple JSON data
    @Test
    public void shouldGetFirstUserAndVerifyFirstNameAndId(){
        REQUEST.get("/users/1")
                .then()
                    .statusCode(200)
                .and()
                    .body("id", equalTo(1))
                    .body("first_name", equalTo("Kasia"));
    }

    /*
    This is more complex test that uses POJO serialization and deserialization
    as well as JUnit assertions and very nice AssertJ assertions with extracting fields from objects
     */
    @Test
    public void shouldCreateNewUser() {
        //Generate new User
        String firstName = FAKER.name().firstName();
        String lastName = FAKER.name().lastName();
        Integer age = FAKER.number().numberBetween(0,80);
        User expected = new User(firstName, lastName, age);

        //Send POST request to create User
        Response createUserResponse = REQUEST
                .body(expected)
                .post("/users");

        //Use Rest-Assured assertions for status code
        createUserResponse
                .then()
                    .assertThat().statusCode(201);

        //Extract created user ID from the response
        expected.id = createUserResponse.jsonPath().getInt("id");

        //Get created user
        User actual = REQUEST.get("/users/"+expected.id).as(User.class);

        //Assert first name is as expected
        assertEquals(expected.last_name, actual.last_name);

        //Get list of all users
        List<User> allUsers = REQUEST.get("/users").body().jsonPath().getList("", User.class);

        //Assert All Users list contains created user
        assertThat(allUsers)
                .extracting("first_name", "last_name", "age")
                .contains(tuple(firstName, lastName, age));
    }
}
