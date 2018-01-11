import io.restassured.RestAssured;

public class TestBase {
    public TestBase() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = 30040;
    }
}
