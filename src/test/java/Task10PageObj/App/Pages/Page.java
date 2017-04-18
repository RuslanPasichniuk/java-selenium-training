package Task10PageObj.App.Pages;


import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Page {
    WebDriver browser;
    WebDriverWait wait;

    public Page(WebDriver browser){
        this.browser = browser;
        wait = new WebDriverWait(browser, 5);
    }

    boolean areElementsPresent(By element){
        return  browser.findElements(element).size() > 0;
    }

}
