package tests;

import io.qameta.allure.Description;
import model.User;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import service.EntriesPageService;
import service.LoginPageService;

import static model.Langs.compareTranslatedLoginLabels;


public class LoginPageTest extends BaseTest {

    @BeforeClass
    public void setUp() {
        loginPageService = new LoginPageService();
        user = new User();
    }

    @DataProvider
    private Object[][] LanguagesToSwitch() {
        return new Object[][]{
                {"FR"},
                {"DE"},
                {"EN"}
        };
    }

    @Test(description = "Checking languages can be switched correctly", dataProvider = "LanguagesToSwitch")
    @Description("Validation of correct translation when changing language on login page")
    public void checkLoginLabelsTranslatedCorrectlyTest(String language) {
        String[] actualTranslatedText = loginPageService.openLogin()
                .changeLoginLanguage(language)
                .takeElementsText();
        Assert.assertTrue(compareTranslatedLoginLabels(language, actualTranslatedText),
                "Login labels are translated wrong!");
    }

    @Test(description = "Checking there is a validation message on login without password")
    @Description("Validation when login with empty Password field")
    public void checkPasswordFieldIsMandatoryTest() {
        String expectedPasswordFieldValidationText = "Mandatory field";
        String actualPasswordFieldValidationText = loginPageService.loginWithoutPassword(user)
                .getPasswordValidationText();
        Assert.assertEquals(actualPasswordFieldValidationText, expectedPasswordFieldValidationText,
                "Password field validation is wrong!");
    }

    @Test(description = "Checking success login")
    @Description("Validation of success login process")
    public void checkSuccessfulLoginTest() {
        EntriesPageService entriesPageService = loginPageService.login(user);
        SoftAssert softAssert = new SoftAssert();
        Boolean logoutButtonIsDisplayed = entriesPageService.checkLogoutButtonIsDisplayedOnPage();
        softAssert.assertTrue(logoutButtonIsDisplayed, "Logout button is not displayed!");
        Boolean isUrlCorrect = entriesPageService.checkEntriesPageUrlIsCorrect();
        softAssert.assertTrue(isUrlCorrect, "Entries page URL is wrong!");
        entriesPageService.logout();
        softAssert.assertAll();
    }

    @Test(description = "Checking success logout")
    @Description("Validation of success logout process")
    public void checkSuccessfulLogoutTest() {
        LoginPageService afterLogoutPage = loginPageService.login(user).logout();
        SoftAssert softAssert = new SoftAssert();
        Boolean loginButtonIsDisplayed = afterLogoutPage.checkLoginButtonIsDisplayedOnPage();
        softAssert.assertTrue(loginButtonIsDisplayed, "Login button is not displayed after logout!");
        Boolean isUrlCorrect = afterLogoutPage.checkPageUrlAfterLogoutIsCorrect();
        softAssert.assertTrue(isUrlCorrect, "Page URL after logout is wrong");
        softAssert.assertAll();
    }

}
