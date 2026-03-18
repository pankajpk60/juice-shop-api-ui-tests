package juiceshop.pageobjects;

import juiceshop.utilsobjects.WaitUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class ProductPage extends WaitUtils {

    private WebDriver driver;

    public ProductPage(WebDriver driver) {
        super(driver);
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    @FindBy(css = "div[aria-label='Click for more information about the product']")
    List<WebElement> allProducts;


    By allProductsBy = By.cssSelector("div[aria-label='Click for more information about the product']");
    By reviewPage = By.xpath("//textarea[@placeholder='What did you like or dislike?']");
    By submitButton=By.xpath("//span[text()=' Submit ']");
    By reviewSubmitConfirmation=By.xpath("//span[text()='You review has been saved.']");



    public List<WebElement> getProductList() {

        waitForElement(allProductsBy);
        return allProducts;


    }

    public WebElement productByName(String productName) {

        WebElement filteredProduct = getProductList().stream().filter(product -> product.findElement(By.cssSelector(".item-name")).getText().trim().equals(productName)).findFirst().orElse(null);
        return filteredProduct;

    }


    public void landOnReviewPageForSelectedProduct(String productName) {
        WebElement prod = productByName(productName);
        prod.click();


    }

    public void submitReviewForSelectedProduct(String reviewComment) {
        waitForElement(reviewPage);
        driver.findElement(reviewPage).sendKeys(reviewComment);
        waitForElement(submitButton);
        driver.findElement(submitButton).click();
        driver.findElement(By.xpath("//span[text()=' Close']")).click();

    }


    public void reviewSubmitConfirmation()
    {
        WebElement waitForReviewMessage=waitForElement(reviewSubmitConfirmation);
        assert waitForReviewMessage.getText().contains("You review has been saved.");


    }

}
