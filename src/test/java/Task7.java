import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import io.github.bonigarcia.wdm.ChromeDriverManager;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.util.List;
import static org.junit.Assert.*;

public class Task7 {

    private WebDriver browser;
    private WebDriverWait wait;
    String url = "http://172.22.50.10/litecart";
    int repeats = 3;

    @Before
    public void start() {
        ChromeDriverManager.getInstance().setup();
        browser = new ChromeDriver();
        wait = new WebDriverWait(browser, 10);
        //login as admin if login form present
        browser.get(url);
        if(areElementsPresent(By.id("box-login"))) {login_as("admin", "admin");}
        wait.until(ExpectedConditions.titleIs("Online Store | My Store"));
    }

    @Test
    public void Task7Test() {
        // some query
        By allProducts = By.cssSelector(".image-wrapper");
        By addToCartBtn = By.cssSelector("button[name=add_cart_product]");
        By acceptCookiesBtn = By.cssSelector("button[name=accept_cookies]");
        By homeImg = By.cssSelector("#logotype-wrapper");
        By sizeList = By.name("options[Size]");
        By quantity = By.cssSelector(".quantity");
        By cartLink = By.cssSelector("#cart-wrapper .link");
        By removeItemBtn = By.cssSelector("button[name=remove_cart_item]");
        By orderItem = By.cssSelector("#checkout-summary-wrapper tr .sku");
        By emptyMessage = By.cssSelector("#checkout-cart-wrapper em");


        // add few products to the cart
        for (int i = 1; i <= repeats; i++) {

            //==> open home page and wait it
            browser.findElement(homeImg).click();
            wait.until(ExpectedConditions.elementToBeClickable(acceptCookiesBtn));

            //==> open product
            List<WebElement> productsList = browser.findElements(allProducts);
            productsList.get(0).click();
            // wait for new page
            wait.until(ExpectedConditions.visibilityOfElementLocated(addToCartBtn));

            //==> select size if sizeList present
            if (areElementsPresent(sizeList)) {
                Select list = new Select(browser.findElement(sizeList));
                list.selectByIndex(1);
            }
            //==> add product to cart
            browser.findElement(addToCartBtn).click();
            wait.until(ExpectedConditions.textToBePresentInElement(quantity, Integer.toString(i)));
        }
        //==> remove all goods from cart
        browser.findElement(cartLink).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(removeItemBtn));

        while (areElementsPresent(orderItem)){
            browser.findElement(removeItemBtn).click();
            wait.until(ExpectedConditions.stalenessOf(browser.findElement(orderItem)));
        }

        // wait for info massage
        wait.until(ExpectedConditions.textToBePresentInElement(emptyMessage, "There are no items in your cart."));

        //verify cart is empty, from home page
        browser.findElement(homeImg).click();
        String itemsInCart = browser.findElement(quantity).getText();
        assertTrue("Cart is not empty", itemsInCart.equals("0"));
    }
    @After
    public void stop() {
        browser.quit();
        browser = null;
    }

    boolean areElementsPresent(By element){
        return  browser.findElements(element).size() > 0;
    }

    void login_as(String user, String pass){
        WebElement loginForm = browser.findElement(By.id("box-login"));
        loginForm.findElement(By.name("username")).sendKeys(user);
        loginForm.findElement(By.name("password")).sendKeys(pass);
        loginForm.findElement(By.name("login")).click();
    }

}