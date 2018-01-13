import io.restassured.response.Response;
import models.Pet;
import org.junit.Test;

import java.util.List;

public class PetsTest extends TestBase {
    @Test
    public void shouldGetAllPetsList() {
        Response response = REQUEST.get("/pets");

        response.then().log().body();

        List<Pet> pets = response.jsonPath().getList("", Pet.class);
        for (Pet p : pets) {
            System.out.println(p.name + ", " + p.age);
        }
    }
}
