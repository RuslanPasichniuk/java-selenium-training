import io.github.bonigarcia.wdm.ChromeDriverManager;
import org.junit.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.util.List;

import static junit.framework.TestCase.assertEquals;

public class Task5 {

    public static WebDriver browser;
    public static WebDriverWait wait;
    String url = "http://172.22.50.10/litecart";

    @BeforeClass
    public static void start() {
        ChromeDriverManager.getInstance().setup();
        browser = new ChromeDriver();
        wait = new WebDriverWait(browser, 5);
    }

    @Test
    public void gotoStore(){
        browser.get(url);
        if(areElementsPresent(By.id("box-login"))){login_as("admin", "admin");}
        wait.until(ExpectedConditions.titleIs("Online Store | My Store"));
    }

    @Test
    public  void compareTwoPages() {
        browser.get(url);
        WebElement sectionCampains = browser.findElement(By.id("box-campaigns"));
        //get info for element on mainPage
        String discountMainPrise = sectionCampains.findElement(By.className("regular-price")).getText();
        String regularMainPrise = sectionCampains.findElement(By.className("campaign-price")).getText();
        String itemNameMain = sectionCampains.findElement(By.className("name")).getText();
        //open item page
        browser.findElement(By.tagName("img", )).click();
        WebElement itemPage = browser.findElement(By.id("box-product"));
        //get info for element on itemPage
        String discountItemPrise = itemPage.findElement(By.className("regular-price")).getText();
        String regularItemPrise = itemPage.findElement(By.className("campaign-price")).getText();
        String itemName = itemPage.findElement(By.tagName("h1")).getText();

        //verify names are correct on both pages
        assertEquals(itemName, itemNameMain);
        //verify discount prise  are correct on both pages
        assertEquals(discountItemPrise, discountMainPrise);
        //verify regular prise are correct on both pages
        assertEquals(regularItemPrise, regularMainPrise);
    }


    @AfterClass
    public static void stop(){
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
