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
import utils.Retry;

import static utils.Langs.compareTranslatedLoginLabels;

public class LoginPageTest extends BaseTest {

    @BeforeClass(alwaysRun = true)
    public void setUp() {
        loginPageService = new LoginPageService();
        user = new User();
    }

    @DataProvider
    private Object[][] switchLanguageTo() {
        return new Object[][]{
                {"FR"},
                {"DE"},
                {"EN"}
        };
    }

    @Test(description = "Checking languages can be switched correctly", dataProvider = "switchLanguageTo", priority = 2, groups = { "regression"}, retryAnalyzer = Retry.class)
    @Description("Validation of correct translation when changing language on login page")
    public void checkLoginLabelsTranslatedCorrectlyTest(String language) {
        String[] actualTranslatedText = loginPageService.openLogin()
                .changeLoginLanguage(language)
                .takeElementsText();
        Assert.assertTrue(compareTranslatedLoginLabels(language, actualTranslatedText),
                "Login labels are translated wrong!");
    }

    @Test(description = "Checking validation message on login without password", priority = 3, groups = {"smoke", "regression"}, retryAnalyzer = Retry.class)
    @Description("Validation when login with empty Password field")
    public void checkPasswordFieldIsMandatoryTest() {
        String expectedPasswordFieldValidationText = "Mandatory field";
        String actualPasswordFieldValidationText = loginPageService.loginWithoutPassword(user)
                .getPasswordValidationText();
        Assert.assertEquals(actualPasswordFieldValidationText, expectedPasswordFieldValidationText,
                "Password field validation is wrong!");
    }

    @Test(description = "Checking success login", priority = 4, groups = {"smoke", "regression"}, retryAnalyzer = Retry.class)
    @Description("Validation of success login process")
    public void checkSuccessfulLoginTest() {
        EntriesPageService entriesPageService = loginPageService.login(user);
        SoftAssert softAssert = new SoftAssert();
        boolean logoutButtonIsDisplayed = entriesPageService.checkLogoutButtonIsDisplayedOnPage();
        softAssert.assertTrue(logoutButtonIsDisplayed, "Logout button is not displayed!");
        boolean isUrlCorrect = entriesPageService.checkEntriesPageUrlIsCorrect();
        softAssert.assertTrue(isUrlCorrect, "Entries page URL is wrong!");
        entriesPageService.logout();
        softAssert.assertAll();
    }

    @Test(description = "Checking success logout", priority = 1, groups = {"smoke", "regression"}, retryAnalyzer = Retry.class)
    @Description("Validation of success logout process")
    public void checkSuccessfulLogoutTest() {
        LoginPageService afterLogoutPage = loginPageService.login(user).logout();
        SoftAssert softAssert = new SoftAssert();
        boolean loginButtonIsDisplayed = afterLogoutPage.checkLoginButtonIsDisplayedOnPage();
        softAssert.assertTrue(loginButtonIsDisplayed, "Login button is not displayed after logout!");
        boolean isUrlCorrect = afterLogoutPage.checkPageUrlAfterLogoutIsCorrect();
        softAssert.assertTrue(isUrlCorrect, "Page URL after logout is wrong");
        softAssert.assertAll();
    }

}
