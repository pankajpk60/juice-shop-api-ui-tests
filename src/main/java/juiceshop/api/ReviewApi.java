package juiceshop.api;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class ReviewApi {

    public static void submitReview(String baseUrl, int productId, String token, String review, String author) {
        given()
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + token)
                .body(String.format("{\"message\":\"%s\",\"author\":\"%s\"}", review, author))
                .when()
                .put(baseUrl + "/rest/products/" + productId + "/reviews")
                .then()
                .statusCode(201);
    }

    public static void verifyReview(String baseUrl, int productId, String review) {
        given()
                .when()
                .get(baseUrl + "/rest/products/" + productId + "/reviews")
                .then()
                .body("data.message", hasItem(review));
    }
}