package com.tutorialsninja.qa.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;
import java.util.stream.Collectors;

public class ProductComparePage {

    private WebDriver driver;

    @FindBy(xpath = "//h1[contains(text(), 'Product Comparison')]")
    private WebElement productComparisonHeader;

    @FindBy(xpath = "//table[@class='table table-bordered']//strong")
    private List<WebElement> comparedProductNames;

    // "Add to Cart" buttons for all products in the comparison table
    @FindBy(xpath = "//input[contains(@value, 'Add to Cart')]")
    private List<WebElement> addToCartButtons;

    // "Remove" links for all products in the comparison table
    @FindBy(xpath = "//a[contains(text(), 'Remove')]")
    private List<WebElement> removeLinks;

    // Success message after adding to cart
    @FindBy(xpath = "//div[contains(@class, 'alert-success')]")
    private WebElement successMessage;

    public ProductComparePage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    // Verify that the Product Comparison page is displayed (ER-3)
    public boolean isProductComparisonPageDisplayed() {
        return productComparisonHeader.isDisplayed();
    }

    // Get the names of the compared products
    public List<String> getComparedProductNames() {
        return comparedProductNames.stream()
                .map(WebElement::getText)
                .collect(Collectors.toList());
    }

    // Helper method to find the index of a product by its name
    private int findProductIndexByName(String productName) {
        return comparedProductNames.stream()
                .filter(product -> product.getText().trim().equalsIgnoreCase(productName))
                .findFirst()
                .map(comparedProductNames::indexOf)
                .orElseThrow(() -> new RuntimeException("Product '" + productName + "' not found in comparison table"));
    }

    // Click "Add to Cart" for a specific product
    public ProductComparePage clickAddToCartForProduct(String productName) {
        int index = findProductIndexByName(productName);
        addToCartButtons.get(index).click();
        return this;
    }

    // Click "Remove" for a specific product
    public ProductComparePage clickRemoveForProduct(String productName) {
        int index = findProductIndexByName(productName);
        removeLinks.get(index).click();
        return this;
    }

    // Verify the success message after adding to cart
    public boolean verifySuccessMessage(String expectedMessage) {
        return successMessage.getText().contains(expectedMessage);
    }

    // Check if a product is still in the comparison table after removal
    public boolean isProductInComparisonTable(String productName) {
        return comparedProductNames.stream()
                .anyMatch(product -> product.getText().trim().equalsIgnoreCase(productName));
    }
}