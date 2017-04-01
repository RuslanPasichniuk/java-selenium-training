import com.google.common.collect.ImmutableMap;
import org.junit.*;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.util.Map;
import static junit.framework.TestCase.assertEquals;


@RunWith(Parameterized.class)
public class Task5 {

    @Parameterized.Parameter
    public  WebDriver browser;
    public  WebDriverWait wait;

    String url = "http://localhost/litecart";
    Map<String, String> colorValue = ImmutableMap.of("greyOnMain", "rgba(119, 119, 119, 1)", "redOnMain", "rgba(204, 0, 0, 1)", "greyOnItem", "rgba(102, 102, 102, 1)", "redOnItem", "rgba(204, 0, 0, 1)");


    @Before
    public void start() {
        wait = new WebDriverWait(browser, 5);
    }

    @Test
    public void compareItemOptions(){
        browser.get(url);
        WebElement sectionCampains = browser.findElement(By.id("box-campaigns"));
        //get info for element on mainPage
        String discountMainPrise = sectionCampains.findElement(By.className("regular-price")).getText();
        String discountMainColor = sectionCampains.findElement(By.className("regular-price")).getCssValue("color");
        String regularMainPrise = sectionCampains.findElement(By.className("campaign-price")).getText();
        String regularMainColor = sectionCampains.findElement(By.className("campaign-price")).getCssValue("color");
        String itemNameMain = sectionCampains.findElement(By.className("name")).getText();


        //open item page
        sectionCampains.findElement(By.className("link")).click();
        WebElement itemPage = browser.findElement(By.id("box-product"));
        //get info for element on itemPage
        String discountItemPrise = itemPage.findElement(By.className("regular-price")).getText();
        String discountItemColor = itemPage.findElement(By.className("regular-price")).getCssValue("color");
        String regularItemPrise = itemPage.findElement(By.className("campaign-price")).getText();
        String regularItemColor = itemPage.findElement(By.className("campaign-price")).getCssValue("color");
        String itemName = itemPage.findElement(By.tagName("h1")).getText();

        //verify names are correct on both pages
        assertEquals(itemName, itemNameMain);
        //verify discount prise  are correct on both pages
        assertEquals(discountItemPrise, discountMainPrise);
        //color
        assertEquals(discountMainColor, colorValue.get("greyOnMain"));
        assertEquals(discountItemColor, colorValue.get("greyOnItem"));
        //verify regular prise are correct on both pages
        assertEquals(regularItemPrise, regularMainPrise);
        //color
        assertEquals(regularMainColor, colorValue.get("redOnMain"));
        assertEquals(regularItemColor, colorValue.get("redOnItem"));
    }

    @After
    public void stop(){
        browser.quit();
        browser = null;
    }

    @Parameterized.Parameters
    public static WebDriver[] driver(){
        return new WebDriver[]{new ChromeDriver(), new FirefoxDriver(), new InternetExplorerDriver()};
        //return new WebDriver[]{new ChromeDriver()};
    }
}
