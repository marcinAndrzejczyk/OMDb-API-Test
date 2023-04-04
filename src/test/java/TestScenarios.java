import io.restassured.response.Response;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.anything;
import static org.hamcrest.Matchers.equalTo;


public class TestScenarios extends TestSetup {
    public static String noApiKeyErrorMessage = "No API key provided.";
    public static String invalidApiKeyErrorMessage = "Invalid API key!";
    String responseErrorMessage;


    @Test
    @DisplayName("Check response message without API Key")
    public void requestForMovieWithoutApiKey_expect401() {
        Response response = given()
                .queryParam("i", "tt1285016")
                .get(URI)
                .then()
                .assertThat()
                .statusCode(401)
                .extract().response();

        responseErrorMessage = response.jsonPath().getString("Error");
        Assertions.assertEquals(responseErrorMessage, noApiKeyErrorMessage);
    }

    @Test
    @DisplayName("Check response message with invalid API Key")
    public void requestForMovieWithInvalidApiKey_expect401() {
        Response response = given()
                .queryParam("apikey", "InvalidApiKey")
                .queryParam("i", "tt1285016")
                .get(URI)
                .then()
                .assertThat()
                .statusCode(401)
                .extract().response();

        responseErrorMessage = response.jsonPath().getString("Error");
        Assertions.assertEquals(responseErrorMessage, invalidApiKeyErrorMessage);
    }

    @Test
    @DisplayName("Check response with correct API Key for movie with valid IMDb ID")
    public void requestForMovieWithCorrectApiKey_expect200() {
        given()
                .queryParam("apikey", API_KEY)
                .queryParam("i", "tt1285016")
                .get(URI)
                .then()
                .assertThat()
                .statusCode(200)
                .body("Title", equalTo("The Social Network"))
                .body("Language", Matchers.containsString("French"))
                .log().body();
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/csv/movies.csv", delimiter = ';', numLinesToSkip = 1)
    @DisplayName("Check if top 5 movies on imdb exist - data delivered via csv file")
    public void requestForTop10ImbdMoviesFromCsvFile_expect200(String title, String year) {
        given()
                .queryParam("apikey", API_KEY)
                .queryParam("t", title)
                .queryParam("y", year)
                .get(URI)
                .then()
                .assertThat()
                .statusCode(200)
                .body("Runtime", anything())
                .log().body();
    }
}




