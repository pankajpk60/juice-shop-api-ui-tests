package juiceshop.pageobjects;

import juiceshop.utilsobjects.WaitUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LoginPage extends WaitUtils {

    private WebDriver driver;

    public LoginPage(WebDriver driver) {
        super(driver);
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    @FindBy(name = "email")
    WebElement emailField;

    @FindBy(name = "password")
    WebElement passwordField;

    @FindBy(id = "loginButton")
    WebElement loginButton;


    By welcomeBanner = By.cssSelector("button[aria-label='Close Welcome Banner']");
    By cookieBanner = By.cssSelector("a[aria-label='dismiss cookie message']");

    public void loginJuiceShopApplication(String Email, String Password) {
        emailField.sendKeys(Email);
        passwordField.sendKeys(Password);
        loginButton.click();

    }


    public void closeWelcomeBannerPopup() {

        WebElement welcomePopup = waitForElement(welcomeBanner);
        welcomePopup.click();

    }

    public void closeCookieBannerPopup() {

        WebElement cookiePopup = waitForElement(cookieBanner);
        cookiePopup.click();


    }


}
