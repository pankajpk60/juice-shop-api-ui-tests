package gradle.junit.selenium;

import io.github.bonigarcia.wdm.WebDriverManager;
import juiceshop.pageobjects.LoginPage;
import juiceshop.pageobjects.ProductPage;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.hasItem;

class JuiceShopTest {
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
        String productName = "Apple Pomace";
        String reviewComment="Good Experience";
        driver.get(baseUrl + "/#/login");
        LoginPage loginPage = new LoginPage(driver);

        // TODO Dismiss popup (click close)
        loginPage.CloseWelcomebannerpopup();//calling function to close welcome banner popup
        loginPage.CloseCookiebannerpopup();//Calling function to close cookie banner popup


        // Login with credentials


        loginPage.LoginJuiceShopApplication(customer.getEmail(), customer.getPassword());


        // TODO Navigate to product and post review

       ProductPage productPage=new ProductPage(driver);
       List<WebElement> AllProduct= productPage.getproductlist();
       productPage.LandOnReviewPageForSelectedProduct(productName);
       productPage.SubmitReviewForSelectedProduct(reviewComment);


//        // TODO Assert that the review has been created successfully
//
//        //WebElement reviewMessage = driver.findElement(By.xpath("//span[text()='You review has been saved.']"));
//        WebElement WaitForReviewMessage = wait.until(
//                ExpectedConditions.visibilityOfElementLocated(
//                        By.xpath("//span[text()='You review has been saved.']")
//                )
//        );
//
//        assert WaitForReviewMessage.getText().contains("You review has been saved.");

    }

    // TODO Task3: Login and post a product review using the Juice Shop API
    @Test
    @Disabled
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
