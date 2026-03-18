package juiceshop.api;

import static io.restassured.RestAssured.*;

public class UserApi {

    public static String login(String url, String email, String password) {
        return given()
                .header("Content-Type", "application/json")
                .body(String.format("{\"email\":\"%s\",\"password\":\"%s\"}", email, password))
                .when()
                .post(url)
                .then()
                .statusCode(200)
                .extract()
                .path("authentication.token");
    }
}
