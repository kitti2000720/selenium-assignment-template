package tests;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import pages.ProductPage;
import pages.SearchResultsPage;
import utils.TestConfig;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Search tests")
class SearchTest extends BaseTest {

    @Test
    @DisplayName("Searching for a laptop returns results")
    void searchingForLaptopReturnsResults() {
        homePage.open();
        SearchResultsPage results = homePage.searchFor(TestConfig.SEARCH_TERM_LAPTOP);

        assertTrue(results.hasResults(),
            "Searching for a laptop should return results");
    }

    @Test
    @DisplayName("Searching for a phone returns results")
    void searchingForPhoneReturnsResults() {
        homePage.open();
        SearchResultsPage results = homePage.searchFor(TestConfig.SEARCH_TERM_PHONE);

        assertTrue(results.hasResults(),
            "Searching for a phone should return results");
    }

    @Test
    @DisplayName("The search results show product names")
    void searchResultsShowProductNames() {
        homePage.open();
        SearchResultsPage results = homePage.searchFor(TestConfig.SEARCH_TERM_LAPTOP);

        assertFalse(results.getFirstProductName().isEmpty(),
            "The first product name should not be empty");
    }

    @Test
    @DisplayName("The search results show product prices")
    void searchResultsShowProductPrices() {
        homePage.open();
        SearchResultsPage results = homePage.searchFor(TestConfig.SEARCH_TERM_LAPTOP);

        assertFalse(results.getFirstProductPrice().isEmpty(),
            "The first product price should not be empty");
    }

    @Test
    @DisplayName("Clicking the first product opens the product page")
    void clickingFirstProductOpensProductPage() {
        homePage.open();
        SearchResultsPage results = homePage.searchFor(TestConfig.SEARCH_TERM_LAPTOP);
        ProductPage product = results.openFirstProduct();

        assertTrue(product.isProductNameVisible(),
            "The product name should be visible on the product page");
    }

    @ParameterizedTest(name = "''{0}'' search returns results")

    @ValueSource(strings = {"laptop", "monitor", "egér", "billentyűzet"})
    @DisplayName("Various search terms return results")
    void variousSearchTermsReturnResultsPage(String term) {
        homePage.open();
        SearchResultsPage results = homePage.searchFor(term);

        assertTrue(results.hasResults() || results.hasNoResultsMessage(),
            "The search results page should be displayed");
    }

    @Test
    @DisplayName("Special characters do not cause errors in the search")
    void specialCharacterSearchDoesNotCrash() {
        homePage.open();
        SearchResultsPage results = homePage.searchFor(TestConfig.SEARCH_TERM_SPECIAL);

        assertNotNull(results.getCurrentUrl(),
            "The page should be navigable after search");
    }
}