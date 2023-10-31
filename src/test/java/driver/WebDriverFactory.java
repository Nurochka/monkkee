package driver;

import io.github.bonigarcia.wdm.WebDriverManager;
import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.time.Duration;

import static utils.Waiter.WAIT_2_SECONDS;

@Log4j2
public class WebDriverFactory {

    private WebDriverFactory() {
    }

    public static WebDriver getWebDriver() {
        WebDriver driver = null;
        switch (System.getProperty("browser", "chrome")) {
            case "firefox": {
                WebDriverManager.firefoxdriver().setup();
                driver = new FirefoxDriver();
                break;
            }
            case "edge": {
                WebDriverManager.edgedriver();
                driver = new EdgeDriver();
                break;
            }
            default: {
                try {
                    WebDriverManager.chromedriver().setup();
                    ChromeOptions chromeOptions = new ChromeOptions();
                    chromeOptions.addArguments("--incognito");
                    chromeOptions.addArguments("--remote-allow-origins=*");
                    driver = new ChromeDriver(chromeOptions);
                } catch (Exception e) {
                    log.fatal("Driver did not start!");
                }
            }
        }
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(WAIT_2_SECONDS));
        return driver;
    }

}
