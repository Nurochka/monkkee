package service;

import io.qameta.allure.Step;
import org.openqa.selenium.Alert;
import page.EntriesPage;

import java.util.ArrayList;

import static utils.Waiter.waitAlertIsPresent;

public class EntriesPageService extends EntriesPage {
    private static final String ENTRIES_PAGE_URL = "https://monkkee.com/app/#/entries";

    private EntriesPage entriesPage = new EntriesPage();

    @Step("Check Entries page url is expected")
    public boolean checkEntriesPageUrlIsCorrect() {
        String actualUrl = entriesPage.getPageUrl();
        return actualUrl.equals(ENTRIES_PAGE_URL);
    }

    @Step("Logout button is present")
    public boolean checkLogoutButtonIsDisplayedOnPage() {
        return entriesPage.checkLogoutButtonIsPresent();
    }

    @Step("Logout")
    public LoginPageService logout() {
        return entriesPage.logout();
    }

    @Step("Click on 'Create an Entry' button")
    public SingleEntryPageService clickCreateNewEntryButton() {
        return entriesPage.clickCreateEntryButton();
    }

    @Step("Get text of an Entry by index")
    public String getEntryText(int index) {
        return entriesPage.getEntryTextByIndex(index);
    }

    @Step("Click on Entry by index")
    public SingleEntryPageService clickOnEntryTextByIndex(int index) {
        return entriesPage.clickOnEntryByIndex(index);
    }

    @Step("Remove Entry by index")
    public EntriesPageService removeEntryByIndex(int index) {
        return entriesPage.checkEntryCheckboxByIndex(index)
                .clickDeleteSelectedEntriesButton()
                .confirmRemovingInAlertPopup()
                .clickLogoImage();
    }

    @Step("Count the number of existing Entries")
    public int getTheNumberOfExistingEntries() {
        return entriesPage.countTheNumberOfEntries();
    }

    @Step("Search for an entry by text")
    public EntriesPageService searchForEntryByText(String textToSearch) {
        return entriesPage.clickLogoImage()
                .enterSearchText(textToSearch)
                .clickSearchButton();
    }

    @Step("Search entries by Tag Name")
    public EntriesPageService searchForEntryByTagName(String tagName) {
        return entriesPage.clickLogoImage()
                .clickTagLinkByTagName(tagName);
    }

    @Step("Check search explanation text contains correct tag name")
    public boolean SearchExplanationTextContainsCorrectTagName(String tagNameToContain) {
        System.out.println(entriesPage.getTagCriteriaText() + " " + tagNameToContain);
        return (entriesPage.getTagCriteriaText().equals(tagNameToContain));
    }

    @Step("Get text from all entries on a page")
    public ArrayList<String> getTextOfAllEntries() {
        int numberOfEntries = entriesPage.getTheNumberOfEntriesOnPage();
        ArrayList<String> listOfTextFromAllEntries = new ArrayList<>();
        for (int i = 0; i < numberOfEntries; i++) {
            listOfTextFromAllEntries.add(getEntryTextByIndex(i));
        }
        return listOfTextFromAllEntries;
    }


    private EntriesPageService confirmRemovingInAlertPopup() {
        waitAlertIsPresent();
        Alert alert = driver.switchTo().alert();
        alert.accept();
        return this;
    }


}
