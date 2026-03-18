package juiceshop.api;

import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class ProductApi {
    public static int getProductIdByName(String baseUrl, String productName) {

        Response response =
                given()
                        .baseUri(baseUrl)
                        .when()
                        .get("/rest/products/search?q=" + productName)
                        .then()
                        .extract()
                        .response();

        // Debug
        System.out.println(response.asPrettyString());

        return response.jsonPath()
                .getInt("data.find { it.name == '" + productName + "' }.id");
    }
}