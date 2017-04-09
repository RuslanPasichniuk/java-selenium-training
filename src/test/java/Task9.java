import io.github.bonigarcia.wdm.ChromeDriverManager;
import org.junit.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.events.AbstractWebDriverEventListener;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.sql.Time;
import java.util.Date;
import java.util.List;

public class Task9 {

    EventFiringWebDriver browser;
    public static WebDriverWait wait;
    String url = "http://localhost/litecart/admin";

    //inner class
    public static class EventsListener extends AbstractWebDriverEventListener{
        @Override
        public void beforeFindBy(By by, WebElement element, WebDriver driver) {
            System.out.println("By => "+by);
        }
        @Override
        public void afterFindBy(By by, WebElement element, WebDriver driver) {
            System.out.println("Element was found");
        }
        @Override
        public void beforeClickOn(WebElement element, WebDriver driver) {
            System.out.println("Click on element: "+element);
        }
        @Override
        public void onException(Throwable throwable, WebDriver driver) {
            System.out.println("Exeption: "+throwable);

            File bufferFile = ((TakesScreenshot) driver). getScreenshotAs(OutputType.FILE);
            try {
                Date date = new Date();
                Files.copy(bufferFile, new File("screenshot"+date+".png"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    @Before
    public void start() {
        ChromeDriverManager.getInstance().setup();
        browser = new EventFiringWebDriver(new ChromeDriver());
        browser.register(new EventsListener());

        wait = new WebDriverWait(browser, 5);
    }
    @Test
    public  void NavigateThroughSections() {
        //login as admin user
        browser.get(url);
        if(areElementsPresent(By.id("box-login"))){login_as("admin", "admin");}
        wait.until(ExpectedConditions.titleIs("My Store"));
        //Get all main options
        List<WebElement> sections = browser.findElements(By.cssSelector("#box-apps-menu a"));
        for (int i =0; i < sections.size(); i++){
            //expand option
            browser.findElements(By.cssSelector("#app-")).get(i).click();
            //get sub-options
            List<WebElement> subSections = browser.findElements(By.cssSelector("#box-apps-menu .selected li"));
            if (subSections.size() > 1) {
                for (int j = 0; j < subSections.size(); j++) {
                    //clock on sub section
                    browser.findElements(By.cssSelector("#box-apps-menu .selected li")).get(j).click();
                    //Verify browser title
                    String subOptionText = browser.findElements(By.cssSelector("#box-apps-menu li")).get(i).findElements(By.cssSelector("li")).get(j).getText();
                    //Verify Summary on WEB
                    browser.findElement(By.tagName("h1")).getText().equals(subOptionText);
                }
            }
        }
    }
    @After
    public void stop(){
        browser.quit();
        browser = null;
    }
//------------------Methods----------------
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
