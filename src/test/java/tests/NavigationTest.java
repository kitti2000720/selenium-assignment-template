package tests;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pages.ProductPage;
import pages.SearchResultsPage;
import utils.TestConfig;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Navigation tests")
class NavigationTest extends BaseTest {

    @Test
    @DisplayName("The home page title contains 'PCX' text")
    void homePageTitleContainsPcx() {
        homePage.open();

        assertTrue(homePage.getPageTitle().toUpperCase().contains("PCX") ||
                   homePage.getPageTitle().toLowerCase().contains("pcx"),
            "The home page title should contain 'PCX', but it is: " + homePage.getPageTitle());
    }

    @Test
    @DisplayName("The home page loads successfully")
    void homePageLoadsSuccessfully() {
        homePage.open();

        assertTrue(homePage.isLoaded(),
            "The home page should load successfully");
    }

    @Test
    @DisplayName("The browser back button returns to the search results")
    void browserBackFromProductReturnsToSearch() {
        homePage.open();
        SearchResultsPage results = homePage.searchFor(TestConfig.SEARCH_TERM_LAPTOP);
        String searchUrl = results.getCurrentUrl();
        results.openFirstProduct();

        driver.navigate().back();

        assertTrue(driver.getCurrentUrl().contains(searchUrl.substring(0, Math.min(30, searchUrl.length()))),
            "The back button should return to the search results");
    }

    @Test
    @DisplayName("The browser forward button returns to the product page")
    void browserForwardReturnsToProductPage() {
        homePage.open();
        SearchResultsPage results = homePage.searchFor(TestConfig.SEARCH_TERM_LAPTOP);
        ProductPage product = results.openFirstProduct();
        String productUrl = product.getCurrentUrl();

        driver.navigate().back();
        driver.navigate().forward();

        assertEquals(productUrl, driver.getCurrentUrl(),
            "The forward button should return to the product page");
    }

    @Test
    @DisplayName("Multiple category pages load successfully")
    void multipleCategoryPagesLoad() {
        String[] categoryPaths = {
            "/laptop",
            "/monitor",
            "/processzor",
            "/memoria",
            "/videokartya"
        };

        for (String path : categoryPaths) {
            driver.get(TestConfig.BASE_URL + path);
            assertFalse(driver.getTitle().isEmpty(),
                "The page should have a title: " + TestConfig.BASE_URL + path);
        }
    }

    @Test
    @DisplayName("The product page has a breadcrumb with at least 2 levels")
    void productPageBreadcrumbHasMultipleLevels() {
        homePage.open();
        SearchResultsPage results = homePage.searchFor(TestConfig.SEARCH_TERM_LAPTOP);
        ProductPage product = results.openFirstProduct();

        assertTrue(product.getBreadcrumbDepth() >= 2,
            "The breadcrumb should have at least 2 levels");
    }
}