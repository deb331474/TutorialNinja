package com.tutorialsninja.qa.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class SearchPage {

    private WebDriver driver;

    @FindBy(linkText = "HP LP3065")
    private WebElement validHPProduct;

    @FindBy(xpath = "//div[@id='content']/h2/following-sibling::p")
    private WebElement noProductMessage;

    @FindBy(css = "#list-view")
    private WebElement listViewBtn;

    @FindBy(id = "grid-view")
    private WebElement gridViewBtn;

    @FindBy(id = "category_id")
    private WebElement categoriesDrpd;

    @FindBy(xpath = "//label[@class='checkbox-inline']/child::input[@name='description']")
    private WebElement inProdDescChkbox;

    @FindBy(xpath = "//label[@class='checkbox-inline']/child::input[@name='sub_category']")
    private WebElement inSubcategoryChkbox;

    @FindBy(css = "#button-search")
    private WebElement searchBtn;

    @FindBy(css = "#input-sort")
    private WebElement sortByDrpd;

    @FindBy(css = "#input-limit")
    private WebElement showDrpd;

    // Locate all "Add to Cart" links for all products
    @FindBy(xpath = "//button[contains(@onclick, 'cart.add')]")
    private List<WebElement> addToCartLinks;

    // Locate all "Add to Wishlist" links for all products
    @FindBy(xpath = "//button[contains(@onclick, 'wishlist.add')]")
    private List<WebElement> addToWishListLinks;

    // Locate all "Compare this Product" links for all products
    @FindBy(xpath = "//button[contains(@onclick, 'compare.add')]")
    private List<WebElement> compareThisProductLinks;

    // Locate all product names (to identify the product we want to interact with)
    @FindBy(xpath = "//div[@class='caption']//h4/a")
    private List<WebElement> productNames;

    // Locate all product image links
    @FindBy(xpath = "//div[@class='image']//a")
    private List<WebElement> productImageLinks;

    // Static text for "Products meeting the search criteria"
    @FindBy(xpath = "//div[@id='content']/h2")
    private WebElement searchResultsHeader;

    // "product comparison" link in the success message
    @FindBy(xpath = "//a[contains(text(), 'product comparison')]")
    private WebElement productComparisonLink;

    public SearchPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    // Verify static text "Products meeting the search criteria"
    public boolean verifySearchResultsHeader() {
        String expectedHeader = "Products meeting the search criteria";
        return searchResultsHeader.getText().equals(expectedHeader);
    }

    // Existing methods
    public String retrieveNoProductMessageText() {
        return noProductMessage.getText();
    }

    public boolean displayStatusOfHPValidProduct() {
        return validHPProduct.isDisplayed();
    }

    public SearchPage switchToListView() {
        listViewBtn.click();
        return this;
    }

    public SearchPage switchToGridView() {
        gridViewBtn.click();
        return this;
    }

    // Helper method to find the index of a product by its name
    private int findProductIndexByName(String productName) {
        return productNames.stream()
                .filter(product -> product.getText().trim().equalsIgnoreCase(productName))
                .findFirst()
                .map(productNames::indexOf)
                .orElseThrow(() -> new RuntimeException("Product '" + productName + "' not found in search results"));
    }

    // Click "Add to Cart" for a specific product
    public SearchPage clickAddToCartForProduct(String productName) {
        int index = findProductIndexByName(productName);
        addToCartLinks.get(index).click();
        return this;
    }

    // Click "Add to Wishlist" for a specific product
    public SearchPage clickAddToWishListForProduct(String productName) {
        int index = findProductIndexByName(productName);
        addToWishListLinks.get(index).click();
        return this;
    }

    // Click "Compare this Product" for a specific product
    public SearchPage clickCompareThisProductForProduct(String productName) {
        int index = findProductIndexByName(productName);
        compareThisProductLinks.get(index).click();
        return this;
    }

    // Click the product image link for a specific product
    public ProductDetailsPage clickProductImageForProduct(String productName) {
        int index = findProductIndexByName(productName);
        productImageLinks.get(index).click();
        return new ProductDetailsPage(driver);
    }

    // Check if "Compare this Product" link is displayed for a specific product
    public boolean isCompareThisProductLinkDisplayedForProduct(String productName) {
        int index = findProductIndexByName(productName);
        return compareThisProductLinks.get(index).isDisplayed();
    }

    // Click the "product comparison" link in the success message
    public ProductComparePage clickProductComparisonLink() {
        productComparisonLink.click();
        return new ProductComparePage(driver);
    }

    // Get all product names
    public List<String> getAllProductNames() {
        return productNames.stream()
                .map(WebElement::getText)
                .collect(Collectors.toList());
    }

    // Compare multiple products
    public ProductComparePage compareMultipleProducts(List<String> productNamesToCompare) {
        productNamesToCompare.forEach(this::clickCompareThisProductForProduct);
        return clickProductComparisonLink();
    }
}