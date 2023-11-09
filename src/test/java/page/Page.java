package page;

import driver.DriverSingleton;
import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

@Log4j2
public class Page {
    protected WebDriver driver = DriverSingleton.getInstance().getDriver();

    protected Page() {
        PageFactory.initElements(driver, this);
    }

    public String getPageUrl() {
        log.info("Getting page URL");
        return driver.getCurrentUrl();
    }

}
