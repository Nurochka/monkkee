package tests;

import driver.DriverSingleton;
import io.qameta.allure.Description;
import model.User;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import service.LoginPageService;
import utils.Retry;

import static utils.TestDataGenerator.generateRandomString;

public class SingleEntryPageTest extends BaseTest {

    private static final String ENTRIES_PAGE_URL = "https://monkkee.com/app/#/entries";

    @BeforeClass(alwaysRun = true)
    public void setUp() {
        loginPageService = new LoginPageService();
        user = new User();
    }

    @AfterMethod(alwaysRun = true)
    public void refreshPage() {
        DriverSingleton.getInstance().getDriver().get(ENTRIES_PAGE_URL);
        DriverSingleton.getInstance().getDriver().navigate().refresh();
    }

    @Test(description = "Checking a new Tag can be assigned to Entry", priority = 2, groups = {"smoke", "regression"}, retryAnalyzer = Retry.class)
    @Description("Verify that a new Tag is assigned to a New Entry")
    public void checkNewTagIsAssignedToNewEntryTest() {
        String EntryText = generateRandomString(50);
        String TagText = generateRandomString(5);
        boolean isNewTagAssigned = loginPageService.login(user)
                .clickCreateNewEntryButton()
                .createEntryWithTag(EntryText, TagText)
                .checkNewTagIsAssigned(TagText);
        Assert.assertTrue(isNewTagAssigned, "New Tag is not in the list of assigned tags!");
    }

    @Test(description = "Checking previously created tag is available for a new Entry", priority = 1, groups = {"regression"}, retryAnalyzer = Retry.class)
    @Description("Verify that previously created tag is displayed in 'Not assigned tags' dropdown")
    public void checkExistingTagIsAvailableInANewEntryTest() {
        String EntryText = generateRandomString(50);
        String TagText = generateRandomString(5);
        boolean isPreviouslyCreatedTagAvailable = loginPageService.login(user)
                .clickCreateNewEntryButton()
                .createEntryWithTag(EntryText, TagText)
                .navigateBackToOverviewPage()
                .clickCreateNewEntryButton()
                .checkPreviouslyCreatedTagIsInTheList(TagText);
        Assert.assertTrue(isPreviouslyCreatedTagAvailable,
                "Previously created Tag is not available for other entries!");
    }

    @Test(description = "Checking assigned tag can be removed", priority = 3, groups = {"smoke", "regression"}, retryAnalyzer = Retry.class)
    @Description("Verify that assigned tag is removed when clicking on it")
    public void checkAssignedTagCanBeRemovedTest() {
        String EntryText = generateRandomString(50);
        String TagText = generateRandomString(5);
        boolean isTagPresentInAssigned = loginPageService.login(user)
                .clickCreateNewEntryButton()
                .createEntryWithTag(EntryText, TagText)
                .clickOnAssignedTagByName(TagText)
                .checkNewTagIsAssigned(TagText);
        Assert.assertFalse(isTagPresentInAssigned, "Previously assigned tag is not removed!");
    }

}