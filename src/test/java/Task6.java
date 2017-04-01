import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import io.github.bonigarcia.wdm.ChromeDriverManager;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import static org.junit.Assert.*;


public class Task6 {

    private WebDriver browser;
    private WebDriverWait wait;
    String url = "http://localhost/litecart/admin";

    @Before
    public void start() {
        ChromeDriverManager.getInstance().setup();
        browser = new ChromeDriver();
        wait = new WebDriverWait(browser, 10);

        //login to admin page
        browser.get(url);
        if(areElementsPresent(By.id("box-login"))) {login_as("admin", "admin");}
        wait.until(ExpectedConditions.titleIs("My Store"));
    }

    @Test
    public void addNewItem() {
        //values:
        String productName = "New Product";

        //open Catalog section
        browser.findElements(By.cssSelector("#app-")).get(1).click();
        browser.findElements(By.cssSelector("#box-apps-menu .selected li")).get(0).click();
        //Verify Summary on WEB
        browser.findElement(By.tagName("h1")).getText().equals("Catalog");
        //create new item
        browser.findElements(By.cssSelector((".button"))).get(1).click();

        List<WebElement> radioButtons = browser.findElements(By.cssSelector("input[type=radio]"));
        List<WebElement> textFields = browser.findElements(By.cssSelector("input[type=text]"));
        List<WebElement> checkboxes = browser.findElements(By.cssSelector("input[type=checkbox]"));
        List<WebElement> numberFields = browser.findElements(By.cssSelector("input[type=number]"));
        List<WebElement> dateFields = browser.findElements(By.cssSelector("input[type=date]"));

        //========================fill fields on general
        radioButtons.get(0).click();
        textFields.get(0).sendKeys(productName);
        textFields.get(1).sendKeys("333");
        checkboxes.get(1).click();
        Select category = new Select(browser.findElement(By.name("default_category_id")));
        category.selectByVisibleText("Root");
        checkboxes.get(4).click();
        numberFields.get(0).clear();
        numberFields.get(0).sendKeys("444");
        Select quantityUnit = new Select(browser.findElement(By.name("quantity_unit_id")));
        quantityUnit.selectByVisibleText("pcs");
        Select deliveryStatus = new Select(browser.findElement(By.name("delivery_status_id")));
        deliveryStatus.selectByVisibleText("3-5 days");
        Select soldOut = new Select(browser.findElement(By.name("sold_out_status_id")));
        soldOut.selectByVisibleText("Sold out");
//        ClassLoader classLoader = getClass().getClassLoader();
//        File file = new File(classLoader.getResource("image.png").getFile());
//        browser.findElement(By.cssSelector("input[type=file]")).sendKeys(file.getAbsolutePath());
        dateFields.get(0).sendKeys("01042017");
        dateFields.get(1).sendKeys("01042017");
        //===========================fill fields on Info page
        browser.findElements(By.cssSelector(".index a")).get(1).click();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.name("manufacturer_id")));

        Select manufacture = new Select(browser.findElement(By.name("manufacturer_id")));
        manufacture.selectByIndex(1);
        Select supplier = new Select(browser.findElement(By.name("supplier_id")));
        supplier.selectByIndex(0);
        //
        List<WebElement> textFields0nInfo = browser.findElements(By.cssSelector((".content [type=text]")));
        WebElement rte = browser.findElement(By.cssSelector(".trumbowyg-editor"));

        textFields0nInfo.get(2).sendKeys("bla-bla-bla");
        textFields0nInfo.get(3).sendKeys("short description bla-bla-bla");
        rte.sendKeys("text for RTE");

        textFields0nInfo.get(4).sendKeys("title bla-bla-bla");
        textFields0nInfo.get(5).sendKeys("meta description bla-bla-bl");
        //===========================fill fields on Prices page
        browser.findElements(By.cssSelector(".index a")).get(3).click();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.name("gross_prices[USD]")));

        WebElement purchasePrice = browser.findElement(By.name("gross_prices[USD]"));
        Select currency = new Select(browser.findElement(By.name("purchase_price_currency_code")));
        purchasePrice.clear();
        purchasePrice.sendKeys("5");
        currency.selectByIndex(1);
        browser.findElement(By.cssSelector("button[name=save]")).click();

        //Verify: item was created
        List<WebElement> allProducts = browser.findElements(By.cssSelector(".dataTable .row"));
        List<String> allProductsValue = allProducts.stream().map(WebElement::getText).collect(Collectors.toList());
        assertTrue(productName, allProductsValue.contains(productName));
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


