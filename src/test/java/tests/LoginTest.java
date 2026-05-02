package tests;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pages.LoginPage;
import utils.RandomDataUtil;
import utils.TestConfig;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Login tests")
class LoginTest extends BaseTest {

    @Test
    @DisplayName("The login form is visible on the login page")
    void loginFormIsVisibleOnLoginPage() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.open();

        assertTrue(loginPage.isLoginFormVisible(),
            "The login form should be visible on the login page");
    }

    @Test
    @DisplayName("The password field masks the input")
    void passwordFieldMasksInput() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.open();

        assertTrue(loginPage.isPasswordFieldMasked(),
            "The password field should mask the input");
    }

    @Test
    @DisplayName("Invalid credentials show an error message")
    void invalidCredentialsShowErrorMessage() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.open();
        loginPage.loginWith(TestConfig.INVALID_EMAIL, TestConfig.INVALID_PASSWORD)
                 .submitInvalidLogin();

        assertTrue(loginPage.isErrorDisplayed(),
            "Invalid credentials should display an error message");
    }

    @Test
    @DisplayName("The error message is not empty")
    void errorMessageIsNotEmpty() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.open();
        loginPage.loginWith(TestConfig.INVALID_EMAIL, TestConfig.INVALID_PASSWORD)
                 .submitInvalidLogin();

        assertFalse(loginPage.getErrorMessage().isEmpty(),
            "The error message should not be empty");
    }

    @Test
    @DisplayName("The registration link is present on the login page")
    void registrationLinkIsPresentOnLoginPage() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.open();

        assertTrue(loginPage.isRegisterLinkPresent(),
            "The registration link should be present on the login page");
    }

    @Test
    @DisplayName("Random credentials are rejected")
    void randomCredentialsAreRejected() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.open();
        loginPage.loginWith(RandomDataUtil.randomEmail(), RandomDataUtil.randomPassword())
                 .submitInvalidLogin();

        assertTrue(loginPage.isErrorDisplayed(),
            "Random credentials should be rejected");
    }

    @Test
    @DisplayName("Successful login shows the user as logged in")
    void successfulLoginShowsUserAsLoggedIn() {
        loginAsValidUser();

        assertTrue(homePage.isUserLoggedIn(),
            "Successful login should show the user as logged in");
    }
}