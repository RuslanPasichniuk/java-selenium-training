package Task10PageObj.App.Tasts;

import Task10PageObj.App.App.App;
import Task10PageObj.App.Data.User;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Task10 {

    App app;
    User user;
    int repeats = 3;
    By quantity = By.cssSelector("a>.quantity");
    WebDriverWait wait;

    @Before
    public void start() {
        app = new App();
//        user = new User();
//        user.setUser("admin");
//        user.setPassword("admin");
        app.start();
    }

    @Test
    public void Task10() {

        for (int i = 1; i <= repeats; i++) {
            app.gotoGoods();
            app.addGoodsToCart();
            // wait until cart quantity changed
            wait.until(ExpectedConditions.textToBePresentInElement(quantity, Integer.toString(i)));
        }
        app.gotoCart();
        app.removeAllGoods();
        app.verifyItemsInCart("0");
    }

    @After
    public void stop(){
        app.stop();
    }

}
