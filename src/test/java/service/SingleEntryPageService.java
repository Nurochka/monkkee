package service;

import io.qameta.allure.Step;
import page.SingleEntryPage;

import java.util.ArrayList;
import java.util.List;

public class SingleEntryPageService extends SingleEntryPage {

    private SingleEntryPage singleEntryPage = new SingleEntryPage();

    @Step("Create a new Entry")
    public EntriesPageService createOrEditEntry(String text) {
        return singleEntryPage.fillInEntryFieldWithRandomText(text)
                .clickSaveButton()
                .clickBackToOverviewButton();
    }

    @Step("Create a new Entry with Tag")
    public SingleEntryPageService createEntryWithTag(String text, String tag) {
        return singleEntryPage.fillInEntryFieldWithRandomText(text)
                .clickSaveButton()
                .fillInNewTagName(tag)
                .clickOkButtonOnAddingNewTag();
    }

    @Step("Navigate back to Overview page")
    public EntriesPageService navigateBackToOverviewPage() {
        return singleEntryPage.clickBackToOverviewButton();
    }

    @Step("Get the list of all assigned tags")
    public ArrayList<String> getTheListOfAllAssignedTags() {
        int numberOfTags = singleEntryPage.getNumberOfAssignedTags();
        ArrayList<String> listOfTags = new ArrayList<String>();
        for (int i = 0; i < numberOfTags; i++) {
            listOfTags.add(singleEntryPage.getTextOfAssignedTagByIndex(i));
        }
        return listOfTags;
    }

    @Step("Check created tag is in 'Assigned tags' list")
    public boolean checkNewTagIsAssigned(String tag) {
        ArrayList<String> allTags = this.getTheListOfAllAssignedTags();
        return allTags.contains(tag);
    }

    @Step("Click on assigned tag by Name")
    public SingleEntryPageService clickOnAssignedTagByName(String tagNameToClick) {
        int numberOfAssignedTags = singleEntryPage.getNumberOfAssignedTags();

        for (int i = 0; i < numberOfAssignedTags; i++) {
            if (singleEntryPage.getTextOfAssignedTagByIndex(i).equals(tagNameToClick)) {
                singleEntryPage.clickOnAssignedTagByIndex(i);
            }
        }
        return new SingleEntryPageService();
    }

    @Step("Check previously created tag is in 'Not assigned tags' dropdown")
    public boolean checkPreviouslyCreatedTagIsInTheList(String tag) {
        List<String> availableTags = singleEntryPage.getTheListOfExistingTags();
        return availableTags.contains(tag);
    }

    @Step("Create a new Entry with existing Tag")
    public EntriesPageService createEntryWithExistingTag(String text, String tag) {
        return singleEntryPage.fillInEntryFieldWithRandomText(text)
                .clickSaveButton()
                .selectExistingTag(tag)
                .clickOkButtonOnAddingExistingTag()
                .clickBackToOverviewButton();
    }

}
