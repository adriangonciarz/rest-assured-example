import io.restassured.RestAssured;
import io.restassured.specification.RequestSpecification;

public class TestBase {
    public RequestSpecification REQUEST;

    public TestBase() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = 30040;
        REQUEST = RestAssured.given();
    }
}
