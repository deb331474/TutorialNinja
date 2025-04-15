package com.tutorialsninja.qa.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class HomePage {

    private WebDriver driver;

    @FindBy(name = "search")
    private WebElement searchTextBox;

    @FindBy(xpath = "//button[contains(@class, 'btn-default')]")
    private WebElement searchButton;

    @FindBy(xpath = "//a[contains(text(), 'Desktops')]")
    private WebElement desktopsMenu;

    @FindBy(xpath = "//a[contains(text(), 'Show All Desktops')]")
    private WebElement showAllDesktopsOption;

    @FindBy(xpath = "//button[contains(@onclick, 'compare.add')]")
    private List<WebElement> compareThisProductLinks;

    @FindBy(xpath = "//div[@class='caption']//h4/a")
    private List<WebElement> productNames;

    // "My Account" dropdown
    @FindBy(xpath = "//span[contains(text(), 'My Account')]")
    private WebElement myAccountDropdown;

    // "Login" link in the dropdown
    @FindBy(xpath = "//a[contains(text(), 'Login')]")
    private WebElement loginLink;

    public HomePage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public SearchPage searchForAProduct(String productName) {
        searchTextBox.sendKeys(productName);
        searchButton.click();
        return new SearchPage(driver);
    }

    public SearchPage clickOnSearchButton() {
        searchButton.click();
        return new SearchPage(driver);
    }

    public HomePage clickOnDesktopsMenu() {
        desktopsMenu.click();
        return this;
    }

    public SearchPage clickOnShowAllDesktops() {
        showAllDesktopsOption.click();
        return new SearchPage(driver);
    }

    private int findProductIndexByName(String productName) {
        return productNames.stream()
                .filter(product -> product.getText().trim().equalsIgnoreCase(productName))
                .findFirst()
                .map(productNames::indexOf)
                .orElseThrow(() -> new RuntimeException("Product '" + productName + "' not found on Home Page"));
    }

    public boolean isCompareThisProductLinkDisplayedForProduct(String productName) {
        int index = findProductIndexByName(productName);
        return compareThisProductLinks.get(index).isDisplayed();
    }

    public HomePage clickCompareThisProductForProduct(String productName) {
        int index = findProductIndexByName(productName);
        compareThisProductLinks.get(index).click();
        return this;
    }

    public ProductComparePage clickProductComparisonLink() {
        driver.findElement(By.xpath("//a[contains(text(), 'product comparison')]")).click();
        return new ProductComparePage(driver);
    }

    // Navigate to the Login Page
    public LoginPage navigateToLoginPage() {
        myAccountDropdown.click();
        loginLink.click();
        return new LoginPage(driver);
    }
    
 // Add this method to the HomePage class
    public RegisterPage navigateToRegisterPage() {
        myAccountDropdown.click();
        driver.findElement(By.xpath("//a[contains(text(), 'Register')]")).click();
        return new RegisterPage(driver);
    }
    
}