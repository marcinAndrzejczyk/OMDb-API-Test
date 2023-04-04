import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.junit.jupiter.api.BeforeEach;

public class TestSetup {
    final String URI = "http://www.omdbapi.com";
    final String API_KEY = "YourAPIKey";
    RequestSpecification requestSpecification;
    ResponseSpecification responseSpecification;

    public TestSetup() {}

    @BeforeEach
    public void setUpClass() {
        requestSpecification = new RequestSpecBuilder()
                .setBaseUri(URI)
                .setAccept(ContentType.JSON)
                .setContentType(ContentType.JSON)
                .build();

        responseSpecification = new ResponseSpecBuilder()
                .expectHeader("Server", "cloudfire")
                .build();
    }
}
