import io.restassured.response.Response;
import models.Pet;
import org.junit.Test;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertEquals;

public class PetsTest extends TestBase {
    @Test
    public void shouldHaveStatus200ForAllPetsList() {
        REQUEST.get("/pets").then().statusCode(200);
    }

    @Test
    public void shouldHavePetInTheList() {
        Response response = REQUEST.get("/pets");
        response.then().body("name", hasItem("Azor"));
    }

    @Test
    public void shouldHaveProperUserIdInSingleUserPet() {
        Integer userId = 1;
        Response response = REQUEST.get("/user/" + userId + "/pets");
        response.then().body("userId", everyItem(equalTo(userId)));
    }

    @Test
    public void shouldCreateNewPet() {
        Pet newPet = generatePetForUserId(1);
        Integer petId = createPetAndReturnID(newPet);
        Pet createdPet = getPetWithId(petId);
        assertEquals(createdPet.name, newPet.name);
        assertEquals(createdPet.userId, newPet.userId);
    }


    @Test
    public void shouldUpdateExistingPet() {
        Pet newPet = generatePetForUserId(1);
        Integer petId = createPetAndReturnID(newPet);
        String newName = "newname" + FAKER.crypto().md5();
        newPet.name = newName;
        updatePet(petId, newPet);
        Pet actual = getPetWithId(petId);
        assertEquals(newName, actual.name);
    }

    private Integer createPetAndReturnID(Pet p) {
        return REQUEST.body(p).post("/pets").body().jsonPath().getInt("id");
    }

    private Pet getPetWithId(Integer id) {
        return REQUEST.get("/pets/" + id).as(Pet.class);
    }

    private Pet generatePetForUserId(Integer userId) {
        String uniqueName = FAKER.crypto().md5();
        Integer age = FAKER.number().numberBetween(0, 30);
        return new Pet(userId, uniqueName, age);
    }

    private void updatePet(Integer id, Pet p) {
        REQUEST.body(p).put("/pets/" + id);
    }
}
