package com.tutorialsninja.qa.testcases;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.tutorialsninja.qa.base.Base;
import com.tutorialsninja.qa.pages.AccountPage;
import com.tutorialsninja.qa.pages.HomePage;
import com.tutorialsninja.qa.pages.LoginPage;
import com.tutorialsninja.qa.pages.ProductComparePage;
import com.tutorialsninja.qa.pages.SearchPage;

import java.util.Arrays;
import java.util.List;

public class TS_006_Product_CompareTest extends Base {
	
	SearchPage searchPage;
	HomePage homePage;
	ProductComparePage productComparePage;
	LoginPage loginPage;
	AccountPage accountPage;
	WebDriver driver;
	
	public TS_006_Product_CompareTest() {
		super();
	}
	
	@BeforeMethod
	public void setUp() {
		driver = initializeBrowserAndOpenApplicationURL(prop.getProperty("browserName"));
		homePage = new HomePage(driver);
		// Navigate to the Login Page and log in
		loginPage = homePage.navigateToLoginPage();
		accountPage = loginPage.login(prop.getProperty("validEmail"), prop.getProperty("validPassword"));
	}
	
	@AfterMethod
	public void tearDown() {
		driver.quit();
	}
	
	// Test Case 1: Click on "product comparison" link from the success message
	@Test(priority = 1)
	public void clickOnComparisonLnkfromtheStatusMsgTest() {
		searchPage = homePage.searchForAProduct("Mac");
		
		Assert.assertTrue(searchPage.verifySearchResultsHeader(),
				"Static text 'Products meeting the search criteria' is not displayed on Search Results page");

		// Click "Compare this Product" for a product (e.g., iMac)
		searchPage.clickCompareThisProductForProduct("iMac");

		// Click the "product comparison" link in the success message
		productComparePage = searchPage.clickProductComparisonLink();

		// Validate ER-3: Product Comparison page is displayed
		Assert.assertTrue(productComparePage.isProductComparisonPageDisplayed(),
				"Product Comparison page is not displayed after clicking 'product comparison' link");
	}

	// Test Case 2: Verify "Compare this Product" from Search Results in List View
	@Test(priority = 2)
	public void verifyCompareProductFromSearchResultsInListView() {
		searchPage = homePage.searchForAProduct("Mac");

		// Verify static text
		Assert.assertTrue(searchPage.verifySearchResultsHeader(),
				"Static text 'Products meeting the search criteria' is not displayed");

		searchPage.switchToListView();

		// Validate ER-1: "Compare this Product" option is displayed
		Assert.assertTrue(searchPage.isCompareThisProductLinkDisplayedForProduct("iMac"),
				"Compare this Product option is not displayed in List view");

		// Validate ER-2: Select "Compare this Product"
		searchPage.clickCompareThisProductForProduct("iMac");

		// Navigate to Product Comparison page
		productComparePage = searchPage.clickProductComparisonLink();

		// Validate ER-3: Product Comparison page is displayed
		Assert.assertTrue(productComparePage.isProductComparisonPageDisplayed(),
				"Product Comparison page is not displayed after clicking Compare this Product");
	}

	// Test Case 3: Verify "Compare this Product" from Search Results in Grid View
	@Test(priority = 3)
	public void verifyCompareProductFromSearchResultsInGridView() {
		searchPage = homePage.searchForAProduct("Mac");

		// Verify static text
		Assert.assertTrue(searchPage.verifySearchResultsHeader(),
				"Static text 'Products meeting the search criteria' is not displayed");

		searchPage.switchToGridView();

		// Validate ER-1: "Compare this Product" option is displayed
		Assert.assertTrue(searchPage.isCompareThisProductLinkDisplayedForProduct("iMac"),
				"Compare this Product option is not displayed in Grid view");

		// Validate ER-2: Select "Compare this Product"
		searchPage.clickCompareThisProductForProduct("iMac");

		// Navigate to Product Comparison page
		productComparePage = searchPage.clickProductComparisonLink();

		// Validate ER-3: Product Comparison page is displayed
		Assert.assertTrue(productComparePage.isProductComparisonPageDisplayed(),
				"Product Comparison page is not displayed after clicking Compare this Product");
	}

	// Test Case 4: Verify "Compare this Product" from Desktops Category in List View
	@Test(priority = 4)
	public void verifyCompareProductFromDesktopsCategoryInListView() {
		searchPage = homePage.clickOnDesktopsMenu().clickOnShowAllDesktops();

		// Verify static text
		Assert.assertTrue(searchPage.verifySearchResultsHeader(),
				"Static text 'Products meeting the search criteria' is not displayed");

		searchPage.switchToListView();

		// Validate ER-1: "Compare this Product" option is displayed
		Assert.assertTrue(searchPage.isCompareThisProductLinkDisplayedForProduct("iMac"),
				"Compare this Product option is not displayed in Desktops category (List view)");

		// Validate ER-2: Select "Compare this Product"
		searchPage.clickCompareThisProductForProduct("iMac");

		// Navigate to Product Comparison page
		productComparePage = searchPage.clickProductComparisonLink();

		// Validate ER-3: Product Comparison page is displayed
		Assert.assertTrue(productComparePage.isProductComparisonPageDisplayed(),
				"Product Comparison page is not displayed after clicking Compare this Product");
	}

	// Test Case 5: Verify "Compare this Product" from Desktops Category in Grid View
	@Test(priority = 5)
	public void verifyCompareProductFromDesktopsCategoryInGridView() {
		searchPage = homePage.clickOnDesktopsMenu().clickOnShowAllDesktops();

		// Verify static text
		Assert.assertTrue(searchPage.verifySearchResultsHeader(),
				"Static text 'Products meeting the search criteria' is not displayed");

		searchPage.switchToGridView();

		// Validate ER-1: "Compare this Product" option is displayed
		Assert.assertTrue(searchPage.isCompareThisProductLinkDisplayedForProduct("iMac"),
				"Compare this Product option is not displayed in Desktops category (Grid view)");

		// Validate ER-2: Select "Compare this Product"
		searchPage.clickCompareThisProductForProduct("iMac");

		// Navigate to Product Comparison page
		productComparePage = searchPage.clickProductComparisonLink();

		// Validate ER-3: Product Comparison page is displayed
		Assert.assertTrue(productComparePage.isProductComparisonPageDisplayed(),
				"Product Comparison page is not displayed after clicking Compare this Product");
	}

	// Test Case 6: Verify "Compare this Product" from Home Page Featured Section
	@Test(priority = 6)
	public void verifyCompareProductFromHomePageFeaturedSection() {
		// Validate ER-1: "Compare this Product" option is displayed on Home Page
		Assert.assertTrue(homePage.isCompareThisProductLinkDisplayedForProduct("MacBook"),
				"Compare this Product option is not displayed in Home Page Featured section");

		// Validate ER-2: Select "Compare this Product"
		homePage.clickCompareThisProductForProduct("MacBook");

		// Navigate to Product Comparison page
		productComparePage = homePage.clickProductComparisonLink();

		// Validate ER-3: Product Comparison page is displayed
		Assert.assertTrue(productComparePage.isProductComparisonPageDisplayed(),
				"Product Comparison page is not displayed after clicking Compare this Product");
	}

	// Test Case 7: Verify Comparing Multiple Products from Search Results
	@Test(priority = 7)
	public void verifyCompareMultipleProducts() {
		searchPage = homePage.searchForAProduct("Mac");

		// Verify static text
		Assert.assertTrue(searchPage.verifySearchResultsHeader(),
				"Static text 'Products meeting the search criteria' is not displayed");

		// Compare two products (e.g., iMac and MacBook)
		productComparePage = searchPage.compareMultipleProducts(Arrays.asList("iMac", "MacBook"));

		// Validate ER-3: Product Comparison page is displayed
		Assert.assertTrue(productComparePage.isProductComparisonPageDisplayed(),
				"Product Comparison page is not displayed after comparing multiple products");

		// Additional validation: Check if both products are in the comparison table
		List<String> comparedProducts = productComparePage.getComparedProductNames();
		Assert.assertTrue(comparedProducts.contains("iMac") && comparedProducts.contains("MacBook"),
				"Compared products iMac and MacBook are not displayed in the comparison table");
	}
}