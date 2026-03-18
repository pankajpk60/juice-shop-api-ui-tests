package juiceshop.utilsobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class WaitUtils {

    WebDriver driver;

    public WaitUtils(WebDriver driver) {

        this.driver = driver;
    }

    public WebElement waitForElement(By findLocator) {

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));

        return wait.until(ExpectedConditions.visibilityOfElementLocated(
                findLocator));


    }
}