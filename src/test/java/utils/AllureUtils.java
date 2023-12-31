package utils;

import driver.DriverSingleton;
import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.OutputType;
import io.qameta.allure.Attachment;
import org.openqa.selenium.TakesScreenshot;

@Log4j2
public class AllureUtils {

    private AllureUtils() {
    }

    @Attachment(value = "screenshot", type = "image/png")
    public static byte[] takeScreenshot() {
        log.info("Taking screenshot");
        return ((TakesScreenshot) DriverSingleton.getInstance().getDriver()).getScreenshotAs(OutputType.BYTES);
    }
}
