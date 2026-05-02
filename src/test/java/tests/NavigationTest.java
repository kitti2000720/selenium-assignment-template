package tests;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import pages.ProductPage;
import pages.SearchResultsPage;
import utils.TestConfig;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Navigation tests")
class NavigationTest extends BaseTest {

    @Test
    @DisplayName("The main page title contains 'PCX'")
    void homePageTitleContainsPcx() {
        homePage.open();

        assertTrue(homePage.getPageTitle().toUpperCase().contains("PCX") ||
                   homePage.getPageTitle().toLowerCase().contains("pcx"),
            "The main page title should contain 'PCX', but it is: " + homePage.getPageTitle());
    }

    @Test
    @DisplayName("The main page loads successfully")
    void homePageLoadsSuccessfully() {
        homePage.open();

        assertTrue(homePage.isLoaded(),
            "The main page should load successfully");
    }

    @Test
    @DisplayName("The back button returns to the search results")
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
    @DisplayName("The forward button returns to the product page")
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

    @Test
    @DisplayName("Hovering over a category menu shows subcategories")
    void hoveringOverCategoryMenuShowsSubcategories() {
        homePage.open();

        // Open the category menu first (hamburger button)
        By hamburgerBtn = By.xpath("//a[contains(@class,'ctrl-desktop-menu')]");
        homePage.waitForVisiblePublic(hamburgerBtn).click();

        // Hover over a top-level category that has subcategories
        By categoryLink = By.xpath(
            "(//li[contains(@class,'catl-level-x')]//a[@data-pcat-deep='0'])[1]"
        );
        WebElement menuItem = homePage.waitForVisiblePublic(categoryLink);
        new Actions(driver).moveToElement(menuItem).perform();

        // Verify subcategory list becomes visible
        By subcategoryList = By.xpath(
            "//li[contains(@class,'catl-level-x') and contains(@class,'open')]//ul"
        );
        assertTrue(homePage.isElementPresentPublic(subcategoryList),
            "The subcategory list should become visible on hover");
    }
}