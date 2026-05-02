package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class SearchResultsPage extends BasePage {

    // Complex XPath: product name links on pcx.hu search results
    private static final By PRODUCT_BOXES = By.xpath(
        "//a[contains(@class,'prod-name')]"
    );

    // Complex XPath: first product name using position predicate
    private static final By FIRST_PRODUCT_NAME = By.xpath(
        "(//a[contains(@class,'prod-name')])[1]"
    );

    // Complex XPath: price amount, excluding meta elements
    private static final By FIRST_PRODUCT_PRICE = By.xpath(
        "(//span[contains(@class,'amount')][not(ancestor::*[contains(@style,'display:none')])])[1]"
    );

    private static final By NO_RESULTS = By.xpath(
        "//p[contains(text(),'nem található') or contains(text(),'No results') or contains(text(),'0 termék')]"
    );

    public SearchResultsPage(WebDriver driver) {
        super(driver);
    }

    public int getResultCount() {
        return driver.findElements(PRODUCT_BOXES).size();
    }

    public boolean hasResults() {
        return getResultCount() > 0;
    }

    public boolean hasNoResultsMessage() {
        return isElementPresent(NO_RESULTS);
    }

    public String getFirstProductName() {
        return waitForVisible(FIRST_PRODUCT_NAME).getText().trim();
    }

    public String getFirstProductPrice() {
        return waitForVisible(FIRST_PRODUCT_PRICE).getText().trim();
    }

    public ProductPage openFirstProduct() {
        WebElement link = waitForVisible(FIRST_PRODUCT_NAME);
        String href = link.getAttribute("href");
        scrollToElement(link);
        clickWithJs(link);
        // Wait until URL changes to product page
        new org.openqa.selenium.support.ui.WebDriverWait(driver, java.time.Duration.ofSeconds(10))
            .until(d -> !d.getCurrentUrl().contains("/kereses/"));
        return new ProductPage(driver);
    }

    public boolean isOnSearchPage() {
        return driver.getCurrentUrl().contains("/kereses/");
    }
}