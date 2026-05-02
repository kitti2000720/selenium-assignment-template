package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import utils.TestConfig;

public class LoginPage extends BasePage {

    private static final By EMAIL_INPUT    = By.xpath("//div[@id='popup']//input[@name='email' or @data-form-elem='email']");
    private static final By PASSWORD_INPUT = By.xpath("//div[@id='popup']//input[@name='password' or @data-form-elem='password']");
    private static final By SUBMIT_BUTTON  = By.xpath("//div[@id='popup']//button[@type='submit' or contains(@class,'submit') or contains(text(),'BEJELENTKEZÉS') or contains(text(),'Bejelentkezés')]");
    private static final By ERROR_MESSAGE  = By.xpath("//div[@id='popup']//*[contains(@class,'error') or contains(@class,'alert') or contains(@class,'message')]");
    private static final By REGISTER_LINK  = By.xpath("//div[@id='popup']//a[contains(text(),'Regisztráció') or contains(@href,'register')]");

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    private static final By LOGIN_POPUP = By.xpath("//div[@id='popup' and contains(@class,'popup-window')]");
    private static final By LOGIN_BUTTON_HEADER = By.xpath("//a[contains(@class,'logIn') or @title='Belépés']");

    public LoginPage open() {
        driver.get(TestConfig.BASE_URL);
        waitForClickable(LOGIN_BUTTON_HEADER).click();
        waitForVisible(LOGIN_POPUP);
        waitForVisible(EMAIL_INPUT);
        return this;
    }

    public LoginPage enterEmail(String email) {
        WebElement field = waitForVisible(EMAIL_INPUT);
        field.clear();
        field.sendKeys(email);
        return this;
    }

    public LoginPage enterPassword(String password) {
        WebElement field = waitForVisible(PASSWORD_INPUT);
        field.clear();
        field.sendKeys(password);
        return this;
    }

    public LoginPage loginWith(String email, String password) {
        enterEmail(email);
        enterPassword(password);
        return this;
    }

    public HomePage submitValidLogin() {
        waitForClickable(SUBMIT_BUTTON).click();
        return new HomePage(driver);
    }

    public LoginPage submitInvalidLogin() {
        waitForClickable(SUBMIT_BUTTON).click();
        return this;
    }

    public boolean isLoginFormVisible() {
        return isElementPresent(EMAIL_INPUT) && isElementPresent(PASSWORD_INPUT);
    }

    public boolean isPasswordFieldMasked() {
        return "password".equals(waitForVisible(PASSWORD_INPUT).getAttribute("type"));
    }

    public boolean isErrorDisplayed() {
        return isElementPresent(ERROR_MESSAGE);
    }

    public String getErrorMessage() {
        return waitForVisible(ERROR_MESSAGE).getText().trim();
    }

    public boolean isRegisterLinkPresent() {
        return isElementPresent(REGISTER_LINK);
    }
}