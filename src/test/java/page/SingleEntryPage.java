package page;

import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import service.EntriesPageService;
import utils.Waiter;


@Log4j2
public class SingleEntryPage extends Page {
    @FindBy(xpath = "//div[@id='editable']//p")
    private WebElement entryField;

    @FindBy(xpath = "//i[@class='icon-home']")
    private WebElement backToOverviewIcon;

    @FindBy(xpath = "//span[@class='cke_button_icon']")
    private WebElement savedIcon;


    public SingleEntryPage fillInEntryFieldWithRandomText(String text) {
        log.info("Enter text into Entry text field");
        Waiter.waitElementToBeVisible(entryField).sendKeys(text);
        return this;
    }

    public SingleEntryPage clickSaveButton(){
        log.info("Clicking Save button");
        Waiter.waitElementToBeClickable(savedIcon).click();
        return this;
    }

    public EntriesPageService clickBackToOverviewButton() {
        log.info("Click 'Back To Overview' Button");
        Waiter.waitElementToBeVisible(backToOverviewIcon).click();
        return new EntriesPageService();
    }


}
