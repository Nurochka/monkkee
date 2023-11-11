package utils;

import driver.DriverSingleton;
import org.openqa.selenium.Alert;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class Waiter {
    public final static int WAIT_2_SECONDS = 2;
    public final static int WAIT_10_SECONDS = 10;
    public final static int WAIT_500_MILLISECONDS = 500;

    private Waiter() {

    }

    public static WebElement waitElementToBeClickable(WebElement element) {
        return new WebDriverWait(DriverSingleton.getInstance().getDriver(), Duration.ofSeconds(WAIT_10_SECONDS))
                .until(ExpectedConditions.elementToBeClickable(element));
    }

    public static WebElement waitElementToBeVisible(WebElement element) {
        return new WebDriverWait(DriverSingleton.getInstance().getDriver(), Duration.ofSeconds(WAIT_10_SECONDS))
                .until(ExpectedConditions.visibilityOf(element));
    }

    public static void fluentWaitElementToBeClickable(WebElement element) {
        new FluentWait<>(DriverSingleton.getInstance().getDriver())
                .withTimeout(Duration.ofSeconds(WAIT_10_SECONDS))
                .pollingEvery(Duration.ofSeconds(WAIT_500_MILLISECONDS)) //интервал опрашиваяя элемента
                .ignoring(NoSuchElementException.class)
                .until((ExpectedCondition<Boolean>) webDriver -> element.isDisplayed() && element.isEnabled());
    }

    public static Alert waitAlertIsPresent() {
        return new WebDriverWait(DriverSingleton.getInstance().getDriver(), Duration.ofSeconds(WAIT_10_SECONDS))
                .until(ExpectedConditions.alertIsPresent());
    }

}
