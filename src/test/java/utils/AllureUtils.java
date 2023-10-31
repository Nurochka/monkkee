package utils;

import driver.DriverSingleton;
import org.openqa.selenium.OutputType;
import io.qameta.allure.Attachment;
import org.openqa.selenium.TakesScreenshot;

public class AllureUtils {
    @Attachment(value = "screenshot", type = "image/png")
    public static byte[] takeScreenshot() {
        return ((TakesScreenshot) DriverSingleton.getInstance().getDriver()).getScreenshotAs(OutputType.BYTES);
    }
}
