package page;

import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.Alert;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import service.EntriesPageService;
import service.LoginPageService;
import service.SingleEntryPageService;
import utils.Waiter;

import java.time.Duration;
import java.util.List;

import static utils.Waiter.waitAlertIsPresent;


@Log4j2
public class EntriesPage extends Page {

    @FindBy(xpath = "//button[@class='user-menu__btn']")
    private WebElement logoutButton;

    @FindBy(xpath = "//i[@class='icon-plus']")
    private WebElement createEntryButton;

    @FindBy(xpath = "//a[@class='entry']//div[@class=' body']")
    private List<WebElement> listOfEntries;

    @FindBy(xpath = " //div[@class='checkbox-wrapper']//input[@class='ng-pristine ng-valid']")
    private List<WebElement> listOfEntriesCheckboxes;

    @FindBy(xpath = "//a[@id='delete-entries']")
    private WebElement deleteSelectedEntriesButton;

    @FindBy(xpath = "//img[@alt='Logo']")
    private WebElement logoImage;

    @FindBy(xpath = "//input[@type='search']")
    private WebElement searchInputField;

    @FindBy(xpath = "//button[@type='submit']")
    private WebElement searchButton;


    public boolean checkLogoutButtonIsPresent() {
        log.info("Checking Logout button is displayed");
        return Waiter.waitElementToBeVisible(logoutButton).isDisplayed();
    }

    public LoginPageService logout() {
        log.info("Clicking Logout button");
        Waiter.waitElementToBeVisible(logoutButton).click();
        return new LoginPageService();
    }

    public SingleEntryPageService clickCreateEntryButton() {
        log.info("Clicking 'Create an Entry' button");
        Waiter.waitElementToBeVisible(createEntryButton).click();
        return new SingleEntryPageService();
    }

    public String getEntryTextByIndex(int index) {
        log.info("Getting text of " + index + " entry");
        String entryText = Waiter.waitElementToBeVisible(listOfEntries.get(index)).getText();
        return entryText;
    }

    public SingleEntryPageService clickOnEntryByIndex(int index) {
        log.info("Clicking on entry with " + index + " index");
        Waiter.waitElementToBeVisible(listOfEntries.get(index)).click();
        return new SingleEntryPageService();
    }

    public EntriesPage checkEntryCheckboxByIndex(int index) {
        log.info("Clicking entry's checkbox with " + index + " index");
        Waiter.waitElementToBeVisible(listOfEntriesCheckboxes.get(index)).click();
        return this;
    }

    public EntriesPageService clickDeleteSelectedEntriesButton() {
        log.info("Clicking 'Delete selected entries' button");
        Waiter.waitElementToBeClickable(deleteSelectedEntriesButton).click();
        return new EntriesPageService();
    }

    public int countTheNumberOfEntries() {
        log.info("Counting the number of Entries");
        int numberOfEntries = listOfEntries.size();
        return numberOfEntries;
    }

    public EntriesPageService clickLogoImage() {
        log.info("Clicking logo image");
        Waiter.waitElementToBeClickable(logoImage).click();
        return new EntriesPageService();
    }

    public EntriesPage enterSearchText(String textToSearch) {
        log.info("Entering text in Search field");
        Waiter.waitElementToBeVisible(searchInputField).sendKeys(textToSearch);
        return this;
    }

    public EntriesPageService clickSearchButton() {
        log.info("Clicking Search button");
        Waiter.waitElementToBeClickable(searchButton).click();
        return new EntriesPageService();
    }


}
