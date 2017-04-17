package Task10PageObj.App.App;

import Task10PageObj.App.Data.User;
import Task10PageObj.App.Pages.CartPage;
import Task10PageObj.App.Pages.GoodsPage;
import Task10PageObj.App.Pages.MainPage;
import Task10PageObj.App.Pages.Page;
import io.github.bonigarcia.wdm.ChromeDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

public class App {

    WebDriver browser;
    WebDriverWait wait;
    String url = "http://localhost/litecart";

    MainPage mainPage;
    GoodsPage goodsPage;
    CartPage cartPage;
    Page page;
    User user;

    public void start(){
        ChromeDriverManager.getInstance().setup();
        browser = new ChromeDriver();
        wait = new WebDriverWait(browser, 10);
        browser.navigate().to(url);

        page = new Page(browser, wait);
        mainPage = new MainPage(browser, wait);
        goodsPage = new GoodsPage(browser, wait);
        cartPage = new CartPage(browser, wait);
    }

    public void gotoGoods(){
        mainPage.gotoGoods();
    }
    public void addGoodsToCart(){
        goodsPage.addGoodsToCart();
    }
    public void gotoCart(){
        mainPage.gotoCart();
    }
    public void removeAllGoods(){
        cartPage.removeAllGoods();
    }
    public void verifyItemsInCart(String items){
        mainPage.verifyCartSize(items);
    }

    public void stop(){
        browser.quit();
    }
}
