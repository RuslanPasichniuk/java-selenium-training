package Task10PageObj.App.Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class CartPage extends Page {
    WebDriver browser;
    WebDriverWait wait;


    By orderItem = By.cssSelector("#checkout-summary-wrapper tr .sku");
    By removeItemBtn = By.cssSelector("button[name=remove_cart_item]");
    By emptyMessage = By.cssSelector("#checkout-cart-wrapper em");

    public CartPage(WebDriver browser) {
        super(browser);
        this.browser = browser;
        wait = new WebDriverWait(browser, 5);
    }

    public void removeAllGoods() {
//        int countProductsInCard = browser.findElements(orderItem).size();
//        for (int i = 1; i < countProductsInCard; i++) {
//            wait.until(ExpectedConditions.elementToBeClickable(removeItemBtn));
//            browser.findElement(removeItemBtn).click();
//            int j = countProductsInCard-i;
//
//            if (j!=1) { wait.until(ExpectedConditions.numberOfElementsToBe(orderItem, j));
//            }else {
//                // wait massage for empty cart
//                wait.until(ExpectedConditions.textToBePresentInElement(emptyMessage, "There are no items in your cart."));
//            }
//        }

        while (areElementsPresent(orderItem)){
            browser.findElement(removeItemBtn).click();
            wait.until(ExpectedConditions.stalenessOf(browser.findElement(orderItem)));
        }
        // wait for info massage
        wait.until(ExpectedConditions.textToBePresentInElement(emptyMessage, "There are no items in your cart."));


    }
}
