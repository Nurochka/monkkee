package tests;

import driver.DriverSingleton;
import io.qameta.allure.Description;
import model.User;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import service.LoginPageService;

import static utils.TestDataGenerator.generateRandomString;

public class SingleEntryPageTest extends BaseTest {

    private static final String ENTRIES_PAGE_URL = "https://monkkee.com/app/#/entries";

    @BeforeClass
    public void setUp() {
        loginPageService = new LoginPageService();
        user = new User();
    }

    @AfterMethod
    public void refreshPage() {
        DriverSingleton.getInstance().getDriver().get(ENTRIES_PAGE_URL);
        DriverSingleton.getInstance().getDriver().navigate().refresh();
    }

    @Test(description = "Checking a new Tag can be assigned to Entry", priority = 1)
    @Description("Verify that a new Tag is assigned to a New Entry")
    public void checkNewTagIsAssignedToNewEntryTest() {
        String randomEntryText = generateRandomString(50);
        String randomTagText = generateRandomString(5);
        Boolean isNewTagAssigned = loginPageService.login(user)
                .clickCreateNewEntryButton()
                .createEntryWithTag(randomEntryText, randomTagText)
                .checkNewTagIsAssigned(randomTagText);
        Assert.assertTrue(isNewTagAssigned, "New Tag is not in the list of assigned tags!");
    }

    @Test(description = "Checking previously created tag is available for a new Entry", priority = 3)
    @Description("Verify that previously created tag is displayed in 'Not assigned tags' dropdown")
    public void checkExistingTagIsAvailableInANewEntryTest() {
        String randomEntryText = generateRandomString(50);
        String randomTagText = generateRandomString(5);
        Boolean isPreviouslyCreatedTagAvailable = loginPageService.login(user)
                .clickCreateNewEntryButton()
                .createEntryWithTag(randomEntryText, randomTagText)
                .navigateBackToOverviewPage()
                .clickCreateNewEntryButton()
                .checkPreviouslyCreatedTagIsInTheList(randomTagText);
        Assert.assertTrue(isPreviouslyCreatedTagAvailable,
                "Previously created Tag is not available for other entries!");
    }

    @Test(description = "Checking assigned tag can be removed", priority = 2)
    @Description("Verify that assigned tag is removed when clicking on it")
    public void checkAssignedTagCanBeRemovedTest() {
        String randomEntryText = generateRandomString(50);
        String randomTagText = generateRandomString(5);
        Boolean isTagPresentInAssigned = loginPageService.login(user)
                .clickCreateNewEntryButton()
                .createEntryWithTag(randomEntryText, randomTagText)
                .clickOnAssignedTagByName(randomTagText)
                .checkNewTagIsAssigned(randomTagText);
        Assert.assertFalse(isTagPresentInAssigned, "Previously assigned tag is not removed!");
    }

}
