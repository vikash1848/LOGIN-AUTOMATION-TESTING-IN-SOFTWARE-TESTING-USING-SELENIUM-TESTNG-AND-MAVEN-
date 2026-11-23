
package pages;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import utils.ConfigReader;

public class LoginPage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    // Locators
    private final By usernameField = By.id("username");
    private final By passwordField = By.id("password");
    private final By loginButton = By.id("submit");
    private final By errorMessage = By.id("error");
    private final By pageHeading = By.tagName("h1");
    private final By logoutButton = By.linkText("Log out");

    // Constructor
    public LoginPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(
            driver,
            Duration.ofSeconds(ConfigReader.getInt("timeoutSeconds"))
        );
    }

    // Open login page
    public void open() {
        driver.get(ConfigReader.get("baseUrl"));
    }

    // Check username field
    public boolean isUsernameDisplayed() {
        return wait.until(
            ExpectedConditions.visibilityOfElementLocated(usernameField)
        ).isDisplayed();
    }

    // Check password field
    public boolean isPasswordDisplayed() {
        return wait.until(
            ExpectedConditions.visibilityOfElementLocated(passwordField)
        ).isDisplayed();
    }

    // Check login button
    public boolean isLoginButtonDisplayed() {
        return wait.until(
            ExpectedConditions.visibilityOfElementLocated(loginButton)
        ).isDisplayed();
    }

    // Enter username
    public void enterUsername(String username) {
        WebElement field = wait.until(
            ExpectedConditions.visibilityOfElementLocated(usernameField)
        );
        field.clear();
        field.sendKeys(username);
    }

    // Enter password
    public void enterPassword(String password) {
        WebElement field = wait.until(
            ExpectedConditions.visibilityOfElementLocated(passwordField)
        );
        field.clear();
        field.sendKeys(password);
    }

    // Click login button
    public void clickLogin() {
        wait.until(
            ExpectedConditions.elementToBeClickable(loginButton)
        ).click();
    }

    // Login using username and password
    public void login(String username, String password) {
        enterUsername(username);
        enterPassword(password);
        clickLogin();
    }

    // Get error message
    public String getErrorMessage() {
        return wait.until(
            ExpectedConditions.visibilityOfElementLocated(errorMessage)
        ).getText();
    }

    // Get page heading
    public String getHeading() {
        return wait.until(
            ExpectedConditions.visibilityOfElementLocated(pageHeading)
        ).getText();
    }

    // Verify successful login
    public boolean isLoginSuccessful() {
        return driver.getCurrentUrl().contains("logged-in-successfully");
    }

    // Verify login page is displayed
    public boolean isLoginPageDisplayed() {
        return driver.getCurrentUrl().contains("practice-test-login")
            && !isLoginSuccessful();
    }

    // Check logout button
    public boolean isLogoutButtonDisplayed() {
        return !driver.findElements(logoutButton).isEmpty()
            && driver.findElement(logoutButton).isDisplayed();
    }

    // Logout
    public void logout() {
        wait.until(
            ExpectedConditions.elementToBeClickable(logoutButton)
        ).click();
    }
}
