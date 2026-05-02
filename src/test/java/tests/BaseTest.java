package tests;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import pages.HomePage;
import pages.LoginPage;
import utils.TestConfig;

public abstract class BaseTest {

    protected WebDriver driver;
    protected HomePage homePage;

    @BeforeAll
    static void setupDriverBinary() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    void initDriver() {
        ChromeOptions options = buildChromeOptions();
        driver = new ChromeDriver(options);
        driver.manage().window().maximize();
        homePage = new HomePage(driver);
    }

    @AfterEach
    void quitDriver() {
        if (driver != null) {
            driver.quit();
        }
    }

    private ChromeOptions buildChromeOptions() {
        ChromeOptions options = new ChromeOptions();
        boolean isCI = System.getenv("CI") != null;
        if (isCI) {
            options.addArguments("--headless=new", "--no-sandbox", "--disable-dev-shm-usage");
        }
        options.addArguments(
            "--window-size=1920,1080",
            "--lang=hu-HU",
            "--disable-blink-features=AutomationControlled",
            "--disable-save-password-bubble",
            "--password-store=basic"
        );
        options.setExperimentalOption("excludeSwitches", new String[]{"enable-automation"});
        options.setExperimentalOption("useAutomationExtension", false);
        options.setExperimentalOption("prefs", java.util.Map.of(
            "credentials_enable_service", false,
            "profile.password_manager_enabled", false
        ));
        return options;
    }

    protected HomePage loginAsValidUser() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.open();
        loginPage.loginWith(TestConfig.VALID_EMAIL, TestConfig.VALID_PASSWORD)
                 .submitValidLogin();
        driver.get(TestConfig.BASE_URL);
        homePage = new HomePage(driver);
        return homePage;
    }
}