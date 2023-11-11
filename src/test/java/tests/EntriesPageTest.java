package tests;

import driver.DriverSingleton;
import io.qameta.allure.Description;
import model.User;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import service.EntriesPageService;
import service.LoginPageService;
import utils.Retry;

import java.util.ArrayList;

import static utils.TestDataGenerator.generateRandomString;
import static utils.TestDataGenerator.generateSubString;

public class EntriesPageTest extends BaseTest {

    @BeforeClass(alwaysRun = true)
    public void setUp() {
        loginPageService = new LoginPageService();
        user = new User();
    }

    @AfterMethod(alwaysRun = true)
    public void refreshPage() {
        DriverSingleton.getInstance().getDriver().navigate().refresh();
    }

    @Test(description = "Checking a new Entry can be saved successfully", priority = 1, groups = {"smoke", "regression"},
    retryAnalyzer = Retry.class)
    @Description("Verify that a new Entry can be created")
    public void checkNewEntryIsAddedTest() {
        int indexOfTheLatestSavedEntry = 0;
        String randomText = generateRandomString(50);
        String actualEntryText = loginPageService.login(user)
                .clickCreateNewEntryButton()
                .createOrEditEntry(randomText)
                .getEntryTextByIndex(indexOfTheLatestSavedEntry);
        Assert.assertEquals(actualEntryText, randomText, "Text of created entry is not as expected!");
    }

    @Test(description = "Checking Entry text can be edited", priority = 2, groups = {"smoke", "regression"}, retryAnalyzer = Retry.class)
    @Description("Verify that Entry text can be edited and saved successfully")
    public void checkEditedEntryIsSavedTest() {
        int indexOfTheLatestSavedEntry = 0;
        String randomTextOnCreation = generateRandomString(50);
        String randomTextOnEditing = generateRandomString(30);
        String actualEntryTextAfterEditing = loginPageService.login(user)
                .clickCreateNewEntryButton()
                .createOrEditEntry(randomTextOnCreation)
                .clickOnEntryTextByIndex(indexOfTheLatestSavedEntry)
                .createOrEditEntry(randomTextOnEditing)
                .getEntryTextByIndex(indexOfTheLatestSavedEntry);
        Assert.assertEquals(actualEntryTextAfterEditing, randomTextOnCreation + randomTextOnEditing,
                "Text of edited entry is not as expected!");
    }

    @Test(description = "Checking Entry can be removed", priority = 2, groups = {"smoke", "regression"}, retryAnalyzer = Retry.class)
    @Description("Verify that created Entry can be removed successfully")
    public void checkEntryIsRemovedTest() {
        int indexOfEntryToRemove = 0;
        String randomTextOnCreation = generateRandomString(50);
        EntriesPageService entriesPageService = loginPageService.login(user)
                .clickCreateNewEntryButton()
                .createOrEditEntry(randomTextOnCreation);
        int numberOfEntriesBeforeRemoving = entriesPageService.getTheNumberOfExistingEntries();
        int numberOfEntriesAfterRemoving = entriesPageService.removeEntryByIndex(indexOfEntryToRemove)
                .getTheNumberOfExistingEntries();
        int expectedNumberOfDeletedEntries = 1;
        int actualNumberOfDeletedEntries = numberOfEntriesBeforeRemoving - numberOfEntriesAfterRemoving;
        Assert.assertEquals(actualNumberOfDeletedEntries, expectedNumberOfDeletedEntries,
                "Entry was not removed correctly!");
    }

    @Test(description = "Search an Entry by partial text", priority = 2, groups = {"regression"}, retryAnalyzer = Retry.class)
    @Description("Verify that Entry is displayed in Search results when searching by text")
    public void checkEntryIsSearchedByTextTest() {
        String randomTextOnCreation = generateRandomString(20);
        int subStringStartIndex = 2;
        int subStringEndIndex = 10;
        int indexOfFoundEntry = 0;
        String subStringToSearch = generateSubString(randomTextOnCreation, subStringStartIndex, subStringEndIndex);
        String textFromFoundEntry = loginPageService.login(user)
                .clickCreateNewEntryButton()
                .createOrEditEntry(randomTextOnCreation)
                .searchForEntryByText(subStringToSearch)
                .getEntryText(indexOfFoundEntry);
        Assert.assertTrue(textFromFoundEntry.contains(subStringToSearch), "Returned value doesn't is wrong!");
    }

    @Test(description = "Search Entries by tag", priority = 2, groups = {"regression"}, retryAnalyzer = Retry.class)
    @Description("Verify that Entry is displayed in Search results when searching by tag")
    public void checkEntryIsSearchedByTagTest() {
        String firstEntryText = generateRandomString(20);
        String secondEntryText = generateRandomString(20);
        String tag = generateRandomString(4);
        EntriesPageService entriesPageService = loginPageService.login(user)
                .clickCreateNewEntryButton()
                .createEntryWithTag(firstEntryText, tag)
                .navigateBackToOverviewPage()
                .clickCreateNewEntryButton()
                .createEntryWithExistingTag(secondEntryText, tag)
                .searchForEntryByTagName(tag);
        Boolean tagNameIsInExplanationMessage = entriesPageService.SearchExplanationTextContainsCorrectTagName(tag);
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertTrue(tagNameIsInExplanationMessage,
                "Required Tag name is not displayed in explanation message!");
        ArrayList<String> entriesText = entriesPageService.getTextOfAllEntries();
        softAssert.assertTrue(entriesText.contains(firstEntryText), "The first entry is not found by tag!");
        softAssert.assertTrue(entriesText.contains(secondEntryText), "The second entry is not found by tag!");
        softAssert.assertAll();
    }

}
