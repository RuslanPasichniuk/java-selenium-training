import io.github.bonigarcia.wdm.ChromeDriverManager;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LoginToAdmin {

    private WebDriver browser;
    private WebDriverWait wait;

    @Before
    public void start() {
        ChromeDriverManager.getInstance().setup();
        browser = new ChromeDriver();
        wait = new WebDriverWait(browser, 30);
    }

    @Test
    public void LoginToAdminPage() {
        browser.get("http://localhost/litecart/admin/");
        WebElement loginForm = browser.findElement(By.id("box-login"));
        loginForm.findElement(By.name("username")).sendKeys("admin");
        loginForm.findElement(By.name("password")).sendKeys("admin");
        loginForm.findElement(By.name("login")).click();
        wait.until(ExpectedConditions.titleIs("My Store"));
    }

    @After
    public void stop() {
        browser.quit();
        browser = null;
    }
}