package service;

import io.qameta.allure.Step;
import page.SingleEntryPage;

import static utils.TestDataGenerator.generateRandomString;

public class SingleEntryPageService extends SingleEntryPage {

    private SingleEntryPage singleEntryPage = new SingleEntryPage();

    @Step("Create a new Entry")
    public EntriesPageService createOrEditEntry(String text) {
        return singleEntryPage.fillInEntryFieldWithRandomText(text)
                .clickSaveButton()
                .clickBackToOverviewButton();
    }


}
