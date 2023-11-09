package tests;

import driver.DriverSingleton;
import model.User;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Listeners;
import service.LoginPageService;

@Listeners(TestListener.class)
public class BaseTest {

    protected LoginPageService loginPageService;
    protected User user;

    @AfterClass(alwaysRun = true)
    public void stopBrowser() {
        DriverSingleton.getInstance().closeDriver();
    }

}
