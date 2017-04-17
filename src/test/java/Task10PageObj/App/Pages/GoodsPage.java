package Task10PageObj.App.Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class GoodsPage extends Page{
    WebDriver browser;
    WebDriverWait wait;

    public GoodsPage(WebDriver browser, WebDriverWait wait) {
        super(browser, wait);
        this.browser = browser;
        this.wait = wait;
//        PageFactory.initElements(browser, this);
    }

//    @FindBy(name="options[Size]")
//    WebElement sizeList;
//    @FindBy(css="button[name=add_cart_product]")
//    WebElement addToCartBtn;
    By sizeList = By.name("options[Size]");
    By addToCartBtn = By.cssSelector("button[name=add_cart_product]");


    public void addGoodsToCart(){
        //==> select size
        if (areElementsPresent(sizeList)) {
            Select list = new Select(browser.findElement(sizeList));
            list.selectByIndex(1);
        }
        //==> add product to cart
        browser.findElement(addToCartBtn).click();
        // wait until cart quantity changed
        //wait.until(ExpectedConditions.textToBePresentInElement(quantity, Integer.toString(i)));

    }

}
