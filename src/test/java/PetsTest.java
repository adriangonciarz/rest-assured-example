import io.restassured.response.Response;
import models.Pet;
import org.hamcrest.Matchers;
import org.junit.Test;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertEquals;

public class PetsTest extends TestBase {
//  Again easy assertion for status code in all Pets List
    @Test
    public void shouldHaveStatus200ForAllPetsList() {
        REQUEST.get("/pets").then().statusCode(200);
    }

    /*
    This is not a good test in real life but may be when you always expect some element to be present (for example non-removable demo user
    It uses built-in Hamcrest Matcher "hasItem" for collections. Rest-Assured returns a collection of elements from certain body key "name"
    and we assert this collection has item -> String "Azor"
     */
    @Test
    public void shouldHavePetInTheList() {
        Response response = REQUEST.get("/pets");
        response.then().body("name", hasItem("Azor"));
    }

//  Here we use built in Hamcrest Matcher (more complex) to assert every item has proper user ID
    @Test
    public void shouldHaveProperUserIdInSingleUserPet() {
        Integer userId = 1;
        Response response = REQUEST.get("/user/" + userId + "/pets");
        response.then().body("userId", everyItem(equalTo(userId)));
    }

//  I extracted some methods to generate Pet, create, update and delete.
//  Now I use them along with JUnit assertions to verify particular fields
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

//  In this case I again use built-in assertions coupled with nice isEmpty() Rest-Assured method
    @Test
    public void shouldDeleteCreatedPet() {
        Pet newPet = generatePetForUserId(1);
        Integer petId = createPetAndReturnID(newPet);
        deletePet(petId);
        REQUEST.get("/pets/"+petId).then().body("isEmpty()", Matchers.is(true));
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

    private void deletePet(Integer id) {
        REQUEST.delete("/pets/"+id);
    }
}
