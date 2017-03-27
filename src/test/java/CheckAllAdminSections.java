import com.sun.xml.internal.fastinfoset.util.CharArray;
import io.github.bonigarcia.wdm.ChromeDriverManager;
import org.junit.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

import static java.lang.Thread.sleep;

public class CheckAllAdminSections {

    public static WebDriver browser;
    public static WebDriverWait wait;
    String url = "http://172.22.50.10/litecart/admin";


    @BeforeClass
     public static void start() {
        ChromeDriverManager.getInstance().setup();
        browser = new ChromeDriver();
        wait = new WebDriverWait(browser, 30);
    }

    @Test
    public void LoginToAdmin(){
        browser.get(url);
        if(areElementsPresent(By.id("box-login")))
        {
            login_as("admin", "admin");
        }
        wait.until(ExpectedConditions.titleIs("My Store"));
    }
    @Test
    public  void NavigateThroughSections() {
        //Get all main options
        List<WebElement> sections = browser.findElements(By.cssSelector("#box-apps-menu li"));
        //
        for (int i =0; i < sections.size(); i++){

            //wait.until(ExpectedConditions.elementToBeClickable(section));
            //expand option
            browser.findElements(By.cssSelector("#box-apps-menu li")).get(i).click();
            //get sub-options
             List<WebElement> subSections = browser.findElements(By.cssSelector("#box-apps-menu li")).get(i).findElements(By.cssSelector("li"));
            for(int j=0; j < subSections.size(); j++)
            {
                //wait.until(ExpectedConditions.elementToBeClickable(subSection));
               //clock on sub section
                browser.findElements(By.cssSelector("#box-apps-menu li")).get(i).findElements(By.cssSelector("li")).get(j).click();

                //Verify browser title
                String subOptionText = browser.findElements(By.cssSelector("#box-apps-menu li")).get(i).findElements(By.cssSelector("li")).get(j).getText();
                wait.until(ExpectedConditions.titleIs(subOptionText+" | My Store"));
                //Verify Summary on WEB
                //...
            }
        }
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
