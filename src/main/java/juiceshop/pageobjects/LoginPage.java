package juiceshop.pageobjects;

import juiceshop.utilsobjects.waitUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LoginPage extends waitUtils {

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


    By welcomebanner = By.cssSelector("button[aria-label='Close Welcome Banner']");
    By cookiebanner = By.cssSelector("a[aria-label='dismiss cookie message']");

    public void LoginJuiceShopApplication(String Email, String Password) {
        emailField.sendKeys(Email);
        passwordField.sendKeys(Password);
        loginButton.click();

    }


    public void CloseWelcomebannerpopup() {

        WebElement WelcomePopup = WaitForElement(welcomebanner);
        WelcomePopup.click();

    }

    public void CloseCookiebannerpopup() {

        WebElement CookiePopup = WaitForElement(cookiebanner);
        CookiePopup.click();


    }


}
