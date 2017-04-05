import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import io.github.bonigarcia.wdm.ChromeDriverManager;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.util.List;

public class Task8 {

    private WebDriver browser;
    private WebDriverWait wait;
    String url = "http://localhost/litecart/admin";

    @Before
    public void start() {
        ChromeDriverManager.getInstance().setup();
        browser = new ChromeDriver();
        wait = new WebDriverWait(browser, 10);
        //login as admin if login form present
        browser.get(url);
        if(areElementsPresent(By.id("box-login"))) {login_as("admin", "admin");}
        wait.until(ExpectedConditions.titleIs("My Store"));
    }

    @Test
    public void Task7Test() {
        // some query and var's
        By addNewCountryBtn = By.cssSelector("#content .button i");
        String parentHandle = browser.getWindowHandle();
        By header =  By.cssSelector("h1 span");

        //open countries section
        browser.findElements(By.cssSelector("#app-")).get(2).click();
        // wait for new page
        wait.until(ExpectedConditions.visibilityOfElementLocated(header));
        //click on add new Country
        browser.findElement(addNewCountryBtn).click();
        // wait
        wait.until(ExpectedConditions.visibilityOfElementLocated(header));
        //get all arrow-icons
        List<WebElement> arrowIcons = (browser.findElements(By.xpath(".//*[@id='content']//input[@type='text']/following::a[@target='_blank']")));

        for (WebElement icon : arrowIcons) {
            icon.click();

            for(String tab : browser.getWindowHandles()){
                if (!tab.equals(parentHandle)){
                    browser.switchTo().window(tab);
                    browser.close();
                    browser.switchTo().window(parentHandle);
                }
            }
            //Actions action = new Actions(browser);
            //ArrayList tabsId = new ArrayList<String> (browser.getWindowHandles());
            //action.keyDown(Keys.CONTROL).sendKeys("w").keyUp(Keys.CONTROL).perform();
            //browser.element.send_keys(:control, 'A')
        }

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