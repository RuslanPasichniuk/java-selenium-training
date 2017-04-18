package Task10PageObj.App.Pages;

import Task10PageObj.App.Data.User;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

import static org.junit.Assert.assertTrue;

public class MainPage {

    WebDriver browser;
    WebDriverWait wait;
//
//    By allProducts = By.cssSelector(".image-wrapper");
//    By addToCartBtn = By.cssSelector("button[name=add_cart_product]");
//    By removeItemBtn = By.cssSelector("button[name=remove_cart_item]");
//
//    @FindBy (css = "#logotype-wrapper")
//    WebElement homeImg;
//    @FindBy(css="#cart-wrapper .link")
//    WebElement cartLink;
//    @FindBy(css=".quantity")
//    WebElement quantity;

    By homeImg = By.cssSelector("#logotype-wrapper");
    By allProducts = By.cssSelector(".image-wrapper");
    By addToCartBtn = By.cssSelector("button[name=add_cart_product]");
    By cartLink = By.cssSelector("#cart-wrapper .link");
    By removeItemBtn = By.cssSelector("button[name=remove_cart_item]");
    By quantity = By.cssSelector(".quantity");

    public MainPage(WebDriver browser) {
        this.browser = browser;
        wait = new WebDriverWait(browser, 5);
    }

    public void open(){
        //==> goto home page
        browser.findElement(homeImg).click();
        // wait home page
        wait.until(ExpectedConditions.titleIs("Online Store | My Store"));
    }

    public void gotoGoods(){
        open();
        //==> open product
        List<WebElement> productsList = browser.findElements(allProducts);
        productsList.get(0).click();
        // wait for new page
        wait.until(ExpectedConditions.visibilityOfElementLocated(addToCartBtn));
    }

    public void gotoCart(){
        //==> goto cart
        browser.findElement(cartLink).click();
        // wait for new page
        wait.until(ExpectedConditions.visibilityOfElementLocated(removeItemBtn));
    }

    public boolean verifyCartSize(String items) {
        //==> goto home page
        open();
        String itemsInCart = browser.findElement(quantity).getText();
        assertTrue("Cart is not empty", itemsInCart.equals(items));
        return true;
    }
}
