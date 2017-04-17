package Task10PageObj.App.Pages;


import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Page {
    WebDriver browser;
    WebDriverWait wait;

    public Page(WebDriver browser, WebDriverWait wait){
        this.browser = browser;
        this.wait = wait;
    }

    boolean areElementsPresent(By element){
        return  browser.findElements(element).size() > 0;
    }

}
