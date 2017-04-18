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
    int repeats = 3;

    @Before
    public void start() {
        app = new App();
        app.start();
    }

    @Test
    public void Task10() {

        for (int i = 1; i <= repeats; i++) {
            app.addGoodsToCart();
            app.checkTheNumbersOfItemsInTheCart(i);
        }

        app.removeAllGoods();
        app.verifyItemsInCart("0");
    }

    @After
    public void stop(){
        app.stop();
    }

}
