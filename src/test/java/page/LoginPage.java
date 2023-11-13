package page;

import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import utils.Waiter;

@Log4j2
public class LoginPage extends Page {

    private static final String LANGUAGE_OPTION = "//a[contains(text(), '%s')]";

    @FindBy(xpath = "//input[@id='login']")
    private WebElement nameField;

    @FindBy(xpath = "//input[@id='password']")
    private WebElement passwordField;

    @FindBy(xpath = "//button[@type='submit']")
    private WebElement loginButton;

    @FindBy(xpath = "//label[@for ='login']")
    private WebElement nameFieldLabel;

    @FindBy(xpath = "//label[@for ='password']")
    private WebElement passwordFieldLabel;

    @FindBy(xpath = "//button//div[@class = 'btn-text-content']")
    private WebElement loginButtonLabel;

    @FindBy(xpath = "//div[@class='form-group has-error']//descendant::div[@class='help-block ng-binding']")
    private WebElement passwordValidation;

    public LoginPage openPage(String url) {
        driver.get(url);
        return this;
    }

    public LoginPage fillInUserName(String userName) {
        log.info("Enter user name");
        Waiter.waitElementToBeVisible(nameField).clear();
        nameField.sendKeys(userName);
        return this;
    }

    public LoginPage fillInPassword(String password) {
        log.info("Enter password");
        Waiter.waitElementToBeVisible(passwordField).clear();
        passwordField.sendKeys(password);
        return this;
    }

    public void clickLoginButton() {
        log.info("Click Login button");
        Waiter.waitElementToBeClickable(loginButton).click();
    }

    public void switchLanguage(String lang) {
        log.info("Switching language to " + lang);
        driver.findElement(By.xpath(String.format(LANGUAGE_OPTION, lang))).click();
    }

    public String getUserNameLabelText() {
        log.info("Get user name label text");
        return nameFieldLabel.getText();
    }

    public String getPasswordLabelText() {
        log.info("Get password label text");
        return passwordFieldLabel.getText();
    }

    public String getLoginButtonText() {
        log.info("Get login button text");
        return loginButtonLabel.getText();
    }

    public String getPasswordFieldValidationText() {
        log.info("Get password field validation text");
        return Waiter.waitElementToBeVisible(passwordValidation).getText();
    }

    public boolean checkLoginButtonIsPresent() {
        log.info("Checking Login button is displayed");
        return Waiter.waitElementToBeClickable(loginButton).isDisplayed();
    }

}
