package gradle.junit.selenium;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

import java.time.Duration;

class JuiceTest2 {
    private static String address = "localhost";
    private static String port = "3000";
    private static String baseUrl = String.format("http://%s:%s", address, port);

    static WebDriver driver;
    static Customer customer;

    @BeforeAll
    static void setup() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();

        //TODO Task1: Add your credentials to customer i.e. email, password and security answer.
        //customer = new Customer.Builder().build();

        customer = new Customer.Builder()
                .setEmail("pankajpathak1988@gmail.com")
                .setPassword("Balegere@123")
                .setSecurityAnswer("titanic")
                .build();
    }

    @AfterAll
    static void teardown() {
        driver.quit();
    }

    //TODO Task2: Login and post a product review using Selenium
    @Test
    void loginAndPostProductReviewViaUi() {
        driver.get(baseUrl + "/#/login");

        // TODO Dismiss popup (click close)
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        WebElement WelcomeBannerPopup = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.cssSelector("button[aria-label='Close Welcome Banner']")));
        WelcomeBannerPopup.click();

        WebElement CookiePopup = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.cssSelector("a[aria-label='dismiss cookie message']")));
        CookiePopup.click();


        // Login with credentials

        WebElement emailField = driver.findElement(By.name("email"));
        WebElement passwordField = driver.findElement(By.name("password"));
        WebElement loginButton = driver.findElement(By.id("loginButton"));

        emailField.sendKeys(customer.getEmail());
        passwordField.sendKeys(customer.getPassword());
        loginButton.click();

        // TODO Navigate to product and post review
        driver.findElement(By.xpath("(//div[contains(@class,'product')])[1]")).click();
        driver.findElement(By.xpath("//textarea[@placeholder='What did you like or dislike?']")).sendKeys("My first Review");

        // Wait for the Submit button to be clickable before clicking

        WebElement WaitForSubmit = wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.xpath("//span[text()=' Submit ']")
                )
        );
        WaitForSubmit.click();
        driver.findElement(By.xpath("//span[text()=' Close']")).click();


        // TODO Assert that the review has been created successfully

        //WebElement reviewMessage = driver.findElement(By.xpath("//span[text()='You review has been saved.']"));
        WebElement WaitForReviewMessage = wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.xpath("//span[text()='You review has been saved.']")
                )
        );

        assert WaitForReviewMessage.getText().contains("You review has been saved.");

    }

    // TODO Task3: Login and post a product review using the Juice Shop API
    @Test
    void loginAndPostProductReviewViaApi() {


        // TODO Retrieve token via login API

        String token = given()
                .header("Content-Type", "application/json")
                .body(String.format("{\"email\":\"%s\"," +
                                "\"password\":\"%s\"}",
                        customer.getEmail(),
                        customer.getPassword()))
                .when()
                .post(baseUrl + "/rest/user/login")
                .then()
                .statusCode(200)
                .extract()
                .path("authentication.token");

        customer.saveToken(token);


        // TODO Use token to post review to product


        String review = "This review is submitted by API call";

        given()
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + customer.getToken())
                .body(String.format("{\"message\":\"%s\",\"author\":\"%s\"}",
                        review,
                        customer.getEmail()))
                .when()
                .put(baseUrl + "/rest/products/1/reviews")
                .then()
                .statusCode(201);

        // TODO Assert that the product review has persisted

        given()
                .when()
                .get(baseUrl + "/rest/products/1/reviews")
                .then()
                .statusCode(200)
                .body("data.message", hasItem(review));
    }
}
