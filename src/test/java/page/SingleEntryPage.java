package page;

import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;
import service.EntriesPageService;
import service.SingleEntryPageService;
import utils.Waiter;

import java.util.ArrayList;
import java.util.List;


@Log4j2
public class SingleEntryPage extends Page {
    @FindBy(xpath = "//div[@id='editable']//p")
    private WebElement entryField;

    @FindBy(xpath = "//i[@class='icon-home']")
    private WebElement backToOverviewIcon;

    @FindBy(xpath = "//span[@class='cke_button_icon']")
    private WebElement savedIcon;

    @FindBy(xpath = "//input[@id='new-tag']")
    private WebElement newTagField;

    @FindBy(xpath = "//button[@id='assign-new-tag']")
    private WebElement addNewTagButton;

    @FindBy(xpath = "//select[@id='select-tag']")
    private WebElement selectTagDropdown;

    @FindBy(xpath = "//button[@id='assign-existing-tag']")
    private WebElement assignExistingTagButton;

    @FindBy(xpath = "//a[@class='tag pointer ng-binding']")
    private List<WebElement> listOfAssignedTags;


    public SingleEntryPage fillInEntryFieldWithRandomText(String text) {
        log.info("Enter text into Entry text field");
        Waiter.waitElementToBeVisible(entryField).sendKeys(text);
        return this;
    }

    public SingleEntryPage clickSaveButton() {
        log.info("Clicking Save button");
        Waiter.waitElementToBeClickable(savedIcon).click();
        return this;
    }

    public EntriesPageService clickBackToOverviewButton() {
        log.info("Clicking 'Back To Overview' Button");
        Waiter.waitElementToBeVisible(backToOverviewIcon).click();
        return new EntriesPageService();
    }

    public SingleEntryPage fillInNewTagName(String tagName) {
        log.info("Entering new tag name");
        Waiter.waitElementToBeVisible(newTagField).sendKeys(tagName);
        return this;
    }

    public SingleEntryPageService clickOkButtonOnAddingNewTag() {
        log.info("Clicking 'Ok' button for a new Tag");
        Waiter.waitElementToBeClickable(addNewTagButton).click();
        return new SingleEntryPageService();
    }

    public int getNumberOfAssignedTags() {
        log.info("Getting number of assigned tags");
        return listOfAssignedTags.size();
    }

    public String getTextOfAssignedTagByIndex(int index) {
        log.info("Getting text from " + index + " tag");
        return Waiter.waitElementToBeVisible(listOfAssignedTags.get(index)).getText();
    }

    public List<String> getTheListOfExistingTags() {
        log.info("Getting the list of existing tags");
        Select dropdownWithExistingTags = new Select(selectTagDropdown);
        List<WebElement> existingTags = dropdownWithExistingTags.getOptions();
        List<String> existingTagsOptions = new ArrayList<String>();
        for (WebElement option : existingTags
        ) {
            existingTagsOptions.add(option.getText());
        }
        return existingTagsOptions;
    }

    public SingleEntryPage selectExistingTag(String tagOption) {
        log.info("Selecting existing tag in dropdown");
        Select dropdownWithExistingTags = new Select(selectTagDropdown);
        dropdownWithExistingTags.selectByVisibleText(tagOption);
        return this;
    }

    public SingleEntryPage clickOkButtonOnAddingExistingTag() {
        log.info("Clicking 'Ok' button when assigning existing Tag");
        Waiter.waitElementToBeClickable(assignExistingTagButton).click();
        return this;
    }

    public void clickOnAssignedTagByIndex(int index) {
        log.info("Clicking on existing Tag by index");
        Waiter.waitElementToBeClickable(listOfAssignedTags.get(index)).click();
    }

}
