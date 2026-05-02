package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class CartPage extends BasePage {

    // Complex XPath: cart items on pcx.hu /megrendeles/reszletes-kosar
    private static final By CART_ITEMS = By.xpath(
        "//div[contains(@class,'obox') or contains(@class,'orderProd')]"
    );

    private static final By ITEM_NAMES = By.xpath(
        "//div[contains(@class,'obox')]//a[string-length(normalize-space(text())) > 0]"
    );

    private static final By REMOVE_BUTTONS = By.xpath(
        "//button[contains(@class,'remove') or contains(@class,'delete') or contains(@class,'del')]"
    );

    // Complex XPath: total price on pcx.hu cart page
    private static final By TOTAL_PRICE = By.xpath(
        "//div[contains(@class,'priceSum') or contains(@class,'total')]//span[contains(@class,'amount')] | //span[contains(@class,'amount') and ancestor::*[contains(@class,'sum') or contains(@class,'total')]]"
    );

    private static final By EMPTY_CART_MSG = By.xpath(
        "//p[contains(text(),'üres') or contains(text(),'empty')] | //div[contains(@class,'empty')]"
    );

    private static final By CHECKOUT_BTN = By.xpath(
        "//a[contains(@href,'checkout') or contains(@href,'megrendeles') or contains(text(),'Megrendel') or contains(text(),'ADATOK')] | //button[contains(text(),'Megrendel')]"
    );

    public CartPage(WebDriver driver) {
        super(driver);
    }

    public int getItemCount() {
        return driver.findElements(CART_ITEMS).size();
    }

    public boolean isEmpty() {
        return isElementPresent(EMPTY_CART_MSG) || getItemCount() == 0;
    }

    public boolean containsProduct(String productName) {
        List<WebElement> names = driver.findElements(ITEM_NAMES);
        return names.stream()
            .map(el -> el.getText().toLowerCase())
            .anyMatch(name -> name.contains(productName.toLowerCase()));
    }

    public String getTotalPrice() {
        if (!isElementPresent(TOTAL_PRICE)) return "";
        return waitForVisible(TOTAL_PRICE).getText().trim();
    }

    public boolean isCheckoutButtonVisible() {
        return isElementPresent(CHECKOUT_BTN);
    }

    public CartPage removeFirstItem() {
        List<WebElement> buttons = driver.findElements(REMOVE_BUTTONS);
        if (!buttons.isEmpty()) {
            clickWithJs(buttons.get(0));
        }
        return this;
    }
}