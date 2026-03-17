package juiceshop.utilsobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class waitUtils {

    WebDriver driver;

    public waitUtils(WebDriver driver) {

        this.driver = driver;
    }

    public WebElement WaitForElement(By Findlocator) {

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));

        return(wait.until(ExpectedConditions.visibilityOfElementLocated(
                Findlocator)));


    }
}