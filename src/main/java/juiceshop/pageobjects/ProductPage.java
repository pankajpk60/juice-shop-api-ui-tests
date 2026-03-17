package juiceshop.pageobjects;

import juiceshop.utilsobjects.waitUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class ProductPage extends waitUtils {

    private WebDriver driver;

    public ProductPage(WebDriver driver) {
        super(driver);
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    @FindBy(css = "div[aria-label='Click for more information about the product']")
    List<WebElement> allproducts;


    By allproductsBy = By.cssSelector("div[aria-label='Click for more information about the product']");
    By ReviewPage = By.xpath("//textarea[@placeholder='What did you like or dislike?']");
    By SubmitButton=By.xpath("//span[text()=' Submit ']");
    By ReviewSubmitConfirmation=By.xpath("//span[text()='You review has been saved.']");



    public List<WebElement> getproductlist() {

        WaitForElement(allproductsBy);
        return allproducts;


    }

    public WebElement productByName(String productName) {

        WebElement filteredProduct = getproductlist().stream().filter(product -> product.findElement(By.cssSelector(".item-name")).getText().trim().equals(productName)).findFirst().orElse(null);
        return filteredProduct;

    }


    public void LandOnReviewPageForSelectedProduct(String productName) {
        WebElement prod = productByName(productName);
        prod.click();


    }

    public void SubmitReviewForSelectedProduct(String reviewComment) {
        WaitForElement(ReviewPage);
        driver.findElement(ReviewPage).sendKeys(reviewComment);
        WaitForElement(SubmitButton);
        driver.findElement(SubmitButton).click();
        driver.findElement(By.xpath("//span[text()=' Close']")).click();

    }


    public void ReviewSubmitConfirmation()
    {
        WebElement WaitForReviewMessage=WaitForElement(ReviewSubmitConfirmation);
        assert WaitForReviewMessage.getText().contains("You review has been saved.");


    }

}
