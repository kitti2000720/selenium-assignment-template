package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class ProductPage extends BasePage {

    private static final By PRODUCT_NAME    = By.xpath("//h1[contains(@class,'prod-name')]");
    private static final By PRODUCT_PRICE   = By.xpath("(//span[contains(@class,'amount')])[1]");
    private static final By ADD_TO_CART_BTN = By.xpath("//button[contains(@class,'basket-insert') and contains(@class,'prod-page')]");
    private static final By WISHLIST_BTN    = By.xpath("//button[contains(@class,'wishlist') or contains(@class,'favorite') or .//span[contains(text(),'Kedvenc')]]");

    // Complex XPath: breadcrumb items using itemprop and class attributes
    private static final By BREADCRUMB_ITEMS = By.xpath(
        "//div[@itemprop='itemListElement']//a[@itemprop='item'] | //span[contains(@class,'path-name') and not(contains(@class,'path-active'))]"
    );

    // Complex XPath: product specs table rows with label and value
    private static final By SPEC_ROWS = By.xpath(
        "//table[contains(@class,'spec') or contains(@class,'attribute')]//tr[td[string-length(normalize-space(text())) > 0]]"
    );

    public ProductPage(WebDriver driver) {
        super(driver);
    }

    public String getProductName() {
        return waitForVisible(PRODUCT_NAME).getText().trim();
    }

    public String getProductPrice() {
        return waitForVisible(PRODUCT_PRICE).getText().trim();
    }

    private static final By CART_POPUP_DETAIL = By.xpath("//button[contains(@class,'goToBaskets') or contains(text(),'Részletes kosár')]");

    public CartPage addToCart() {
        System.out.println("addToCart: current URL = " + driver.getCurrentUrl());
        acceptCookiesIfPresent();
        // Try to close any popups first
        try {
            org.openqa.selenium.WebElement body = driver.findElement(By.tagName("body"));
            body.sendKeys(org.openqa.selenium.Keys.ESCAPE);
        } catch (Exception ignored) {}
        // Use JavaScript to find and click the button directly
        try {
            ((org.openqa.selenium.JavascriptExecutor) driver).executeScript(
                "document.querySelector('.basket-insert.prod-page').click();"
            );
        } catch (Exception e) {
            // Fallback: wait and try normal click
            WebElement btn = waitForVisible(ADD_TO_CART_BTN);
            scrollToElement(btn);
            clickWithJs(btn);
        }
        try {
            new org.openqa.selenium.support.ui.WebDriverWait(driver, java.time.Duration.ofSeconds(5))
                .until(org.openqa.selenium.support.ui.ExpectedConditions.elementToBeClickable(CART_POPUP_DETAIL));
            driver.findElement(CART_POPUP_DETAIL).click();
        } catch (Exception ignored) {}
        return new CartPage(driver);
    }

    public boolean isAddToCartButtonVisible() {
        acceptCookiesIfPresent();
        return isElementPresent(ADD_TO_CART_BTN);
    }

    public ProductPage addToWishlist() {
        if (isElementPresent(WISHLIST_BTN)) {
            WebElement btn = waitForClickable(WISHLIST_BTN);
            scrollToElement(btn);
            btn.click();
        }
        return this;
    }

    public boolean isProductNameVisible() {
        return isElementPresent(PRODUCT_NAME);
    }

    public int getBreadcrumbDepth() {
        return driver.findElements(BREADCRUMB_ITEMS).size();
    }
}