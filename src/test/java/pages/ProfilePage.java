package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import utils.TestConfig;

public class ProfilePage extends BasePage {

    // Complex XPath: account menu links with non-empty text
    private static final By ACCOUNT_MENU_ITEMS = By.xpath(
        "//a[contains(@href,'Adataim') or contains(@href,'Rendeleseim') or contains(@href,'Kedvencek') or contains(text(),'Adataim') or contains(text(),'Rendeléseim') or contains(text(),'Kedvencek')]"
    );

    private static final By LOGOUT_LINK = By.xpath(
        "//a[contains(@href,'action=logOut') or contains(text(),'Kilépés')]"
    );

    private static final By ORDER_HISTORY_LINK = By.xpath(
        "//a[contains(text(),'Rendeléseim') or contains(text(),'Korábbi Rendeléseim') or contains(@href,'order') or contains(@href,'rendeles')]"
    );

    private static final By PROFILE_HEADING = By.xpath(
        "//h1 | //h2[contains(@class,'page') or contains(@class,'title')]"
    );

    private static final String ACCOUNT_URL = TestConfig.BASE_URL + "/Adataim";

    public ProfilePage(WebDriver driver) {
        super(driver);
    }

    public ProfilePage open() {
        driver.get(ACCOUNT_URL);
        return this;
    }

    public boolean isOnProfilePage() {
        String url = driver.getCurrentUrl();
        return url.contains("Adataim") || url.contains("account") || url.contains("profil");
    }

    public boolean isLogoutLinkPresent() {
        return isElementPresent(LOGOUT_LINK);
    }

    public boolean isOrderHistoryLinkPresent() {
        return isElementPresent(ORDER_HISTORY_LINK);
    }

    public int getAccountMenuItemCount() {
        return driver.findElements(ACCOUNT_MENU_ITEMS).size();
    }

    private static final By ACCOUNT_TOGGLE = By.xpath("//a[contains(@class,'logOut') and @title='Saját fiók']");

    public HomePage logout() {
        clickWithJs(driver.findElement(ACCOUNT_TOGGLE));
        waitForClickable(LOGOUT_LINK).click();
        driver.get("https://www.pcx.hu");
        return new HomePage(driver);
    }
}