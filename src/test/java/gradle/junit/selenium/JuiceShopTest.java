package gradle.junit.selenium;

import io.github.bonigarcia.wdm.WebDriverManager;
import juiceshop.api.ProductApi;
import juiceshop.api.ReviewApi;
import juiceshop.api.UserApi;
import juiceshop.pageobjects.LoginPage;
import juiceshop.pageobjects.ProductPage;
import juiceshop.utilsobjects.DataReader;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

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
                .setEmail(DataReader.get("email"))
                .setPassword(DataReader.get("password"))
                .setSecurityAnswer(DataReader.get("securityAnswer"))
                .build();
    }

    @AfterAll
    static void teardown() {
        driver.quit();
    }

    //TODO Task2: Login and post a product review using Selenium
    @Test
    @Disabled
    void loginAndPostProductReviewViaUi() {
        String productName = DataReader.get("productName");
        String reviewComment = DataReader.get("reviewComment");
        driver.get(baseUrl + "/#/login");
        LoginPage loginPage = new LoginPage(driver);

        // TODO Dismiss popup (click close)
        loginPage.closeWelcomeBannerPopup();//calling function to close welcome banner popup
        loginPage.closeCookieBannerPopup();//Calling function to close cookie banner popup

        // Login with credentials
        loginPage.loginJuiceShopApplication(customer.getEmail(), customer.getPassword());

        // TODO Navigate to product and post review

        ProductPage productPage = new ProductPage(driver);
        List<WebElement> allProducts = productPage.getProductList();
        productPage.landOnReviewPageForSelectedProduct(productName);
        productPage.submitReviewForSelectedProduct(reviewComment);


        // TODO Assert that the review has been created successfully
        productPage.reviewSubmitConfirmation();
    }

    // TODO Task3: Login and post a product review using the Juice Shop API
    @Test
    void loginAndPostProductReviewViaApi() {


        // TODO Retrieve token via login API

        String review = DataReader.get("apiReview");
        String productName = DataReader.get("productName");
        int productId = ProductApi.getProductIdByName(baseUrl, productName);

        String token = UserApi.login(baseUrl + "/rest/user/login", customer.getEmail(), customer.getPassword());

        // TODO Use token to post review to product
        ReviewApi.submitReview(baseUrl, productId, token, review, customer.getEmail());

        // TODO Assert that the product review has persisted

        ReviewApi.verifyReview(baseUrl, productId, review);


    }
}



