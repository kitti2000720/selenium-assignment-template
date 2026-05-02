package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import utils.TestConfig;

public class HomePage extends BasePage {

    private static final By SEARCH_INPUT   = By.xpath("//input[@name='search']");
    private static final By SEARCH_BUTTON  = By.xpath("//button[contains(@class,'search') or ancestor::*[contains(@class,'search-bar') or contains(@class,'finder')]]");
    private static final By LOGIN_LINK     = By.xpath("//a[contains(@class,'logIn') or @title='Belépés']");
    private static final By LOGGED_IN_USER = By.xpath("//a[@title='Saját fiók' or .//i[contains(@class,'icon-user-check')] or .//span[contains(text(),'Fiókom')]]");
    private static final By CART_ICON      = By.xpath("//a[@title='Kosár']");
    private static final By CART_COUNT     = By.xpath("//a[@title='Kosár']//span[contains(@class,'count') or contains(@class,'qty') or contains(@class,'num')]");

    public HomePage(WebDriver driver) {
        super(driver);
    }

    public HomePage open() {
        driver.get(TestConfig.BASE_URL);
        acceptCookiesIfPresent();
        return this;
    }

    public LoginPage openLoginPage() {
        waitForClickable(LOGIN_LINK).click();
        return new LoginPage(driver);
    }

    public SearchResultsPage searchFor(String keyword) {
        WebElement input = waitForVisible(SEARCH_INPUT);
        input.clear();
        input.sendKeys(keyword);
        input.sendKeys(org.openqa.selenium.Keys.ENTER);
        return new SearchResultsPage(driver);
    }

    public CartPage openCart() {
        waitForClickable(CART_ICON).click();
        return new CartPage(driver);
    }

    private static final By LOGIN_BUTTON_CHECK = By.xpath("//a[contains(@class,'logIn') and @title='Belépés']");

    public boolean isUserLoggedIn() {
        // If login button is present, user is NOT logged in
        // If "Fiókom" text is present, user IS logged in
        return !isElementPresent(LOGIN_BUTTON_CHECK) &&
               isElementPresent(LOGGED_IN_USER);
    }

    public boolean isLoaded() {
        return driver.getCurrentUrl().contains("pcx.hu");
    }

    public int getCartCount() {
        if (!isElementPresent(CART_COUNT)) return 0;
        try {
            String text = waitForVisible(CART_COUNT).getText().replaceAll("[^0-9]", "");
            return text.isEmpty() ? 0 : Integer.parseInt(text);
        } catch (Exception e) {
            return 0;
        }
    }
}