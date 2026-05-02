package tests;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pages.CartPage;
import pages.ProductPage;
import pages.SearchResultsPage;
import utils.TestConfig;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Cart tests")
class CartTest extends BaseTest {

    @Test
    @DisplayName("The cart page is accessible after login")
    void cartPageIsAccessible() {
        loginAsValidUser();
        driver.get("https://www.pcx.hu/megrendeles/reszletes-kosar");
        CartPage cart = new CartPage(driver);

        assertTrue(driver.getCurrentUrl().contains("kosar"),
            "The cart page should be accessible");
    }

    private static final String PRODUCT_URL = "https://www.pcx.hu/sbox-ar-65w-acer-laptopokhoz-tolto-adapter-sbox-ar-65w-668149";

    @Test
    @DisplayName("The product page shows the 'Add to Cart' button")
    void productPageShowsAddToCartButton() {
        loginAsValidUser();
        driver.get(PRODUCT_URL);
        ProductPage product = new ProductPage(driver);

        assertTrue(product.isAddToCartButtonVisible(),
            "The 'Add to Cart' button should be visible on the product page");
    }

    @Test
    @DisplayName("Termék hozzáadása után a kosár nem üres")
    void addingProductMakesCartNonEmpty() {
        loginAsValidUser();
        driver.get(PRODUCT_URL);
        ProductPage product = new ProductPage(driver);
        CartPage cart = product.addToCart();

        assertFalse(cart.isEmpty(),
            "The cart should not be empty after adding a product");
    }

    @Test
    @DisplayName("The cart shows the total price")
    void cartShowsTotalPrice() {
        loginAsValidUser();
        driver.get(PRODUCT_URL);
        ProductPage product = new ProductPage(driver);
        CartPage cart = product.addToCart();

        assertFalse(cart.getTotalPrice().isEmpty(),
            "The total price should be displayed in the cart");
    }

    @Test
    @DisplayName("The checkout button is visible in a non-empty cart")
    void checkoutButtonVisibleInNonEmptyCart() {
        loginAsValidUser();
        driver.get(PRODUCT_URL);
        ProductPage product = new ProductPage(driver);
        CartPage cart = product.addToCart();

        assertTrue(cart.isCheckoutButtonVisible(),
            "The checkout button should be visible in a non-empty cart");
    }
}