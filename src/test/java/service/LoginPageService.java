package service;

import io.qameta.allure.Step;
import model.User;
import page.LoginPage;

public class LoginPageService extends LoginPage {
    private static final String LOGIN_PAGE_URL = "https://monkkee.com/app/#/";

    private LoginPage loginPage = new LoginPage();

    @Step("Open login page")
    public LoginPageService openLogin() {
        loginPage.openPage(LOGIN_PAGE_URL);
        return new LoginPageService();
    }

    @Step("Valid login process")
    public EntriesPageService login(User user) {
        loginPage.openPage(LOGIN_PAGE_URL)
                .fillInUserName(user.getUserName())
                .fillInPassword(user.getPassword())
                .clickLoginButton();
        return new EntriesPageService();
    }

    @Step("Switch language")
    public LoginPageService changeLoginLanguage(String lang) {
        loginPage.switchLanguage(lang);
        return new LoginPageService();
    }

    @Step("Get text of login elements")
    public String[] takeElementsText() {
        String nameText = loginPage.getUserNameLabelText();
        String passwordText = loginPage.getPasswordLabelText();
        String loginButtonText = loginPage.getLoginButtonText();
        String[] texts = {nameText, passwordText, loginButtonText};
        return texts;
    }

    @Step("Login attempt without populating Password field")
        public LoginPageService loginWithoutPassword(User user) {
        loginPage.openPage(LOGIN_PAGE_URL)
                .fillInUserName(user.getUserName())
                .clickLoginButton();
        return new LoginPageService();
    }

    @Step("Get validation text for password field")
    public String getPasswordValidationText() {
        String validation = loginPage.getPasswordFieldValidationText();
        return validation;
    }

    @Step("Check page url is correct after logout")
    public boolean checkPageUrlAfterLogoutIsCorrect() {
        String actualUrl = loginPage.getPageUrl();
        return actualUrl.equals(LOGIN_PAGE_URL);
    }

    @Step("Login button is present")
    public boolean checkLoginButtonIsDisplayedOnPage() {
        return loginPage.checkLoginButtonIsPresent();
    }

}
