package tests;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pages.ProfilePage;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Profile tests")
class ProfileTest extends BaseTest {

    @Test
    @DisplayName("LoggedIn user can access the profile page")
    void loggedInUserCanAccessProfilePage() {
        loginAsValidUser();
        ProfilePage profile = new ProfilePage(driver);
        profile.open();

        assertTrue(profile.isOnProfilePage(),
            "The profile page should be accessible");
    }

    @Test
    @DisplayName("The profile page shows the logout link")
    void profilePageShowsLogoutLink() {
        loginAsValidUser();
        ProfilePage profile = new ProfilePage(driver);
        profile.open();

        assertTrue(profile.isLogoutLinkPresent(),
            "The logout link should be present");
    }

    @Test
    @DisplayName("The profile page shows the order history link")
    void profilePageShowsOrderHistoryLink() {
        loginAsValidUser();
        ProfilePage profile = new ProfilePage(driver);
        profile.open();

        assertTrue(profile.isOrderHistoryLinkPresent(),
            "The order history link should be present");
    }

    @Test
    @DisplayName("The user is not logged in after logout")
    void logoutRemovesUserSession() {
        loginAsValidUser();
        ProfilePage profile = new ProfilePage(driver);
        profile.open();
        profile.logout();

        assertFalse(homePage.isUserLoggedIn(),
            "The user should not be logged in after logout");
    }

    @Test
    @DisplayName("The profile menu has at least one item")
    void profileMenuHasItems() {
        loginAsValidUser();
        ProfilePage profile = new ProfilePage(driver);
        profile.open();

        assertTrue(profile.getAccountMenuItemCount() > 0,
            "The profile menu should have at least one item");
    }
}