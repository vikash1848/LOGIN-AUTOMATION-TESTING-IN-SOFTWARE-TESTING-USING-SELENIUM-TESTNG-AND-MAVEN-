
package tests;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import pages.LoginPage;
import utils.ScreenshotUtil;

public class LoginTest {

    private WebDriver driver;
    private LoginPage loginPage;

    @BeforeMethod
    public void setUp() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();

        loginPage = new LoginPage(driver);
        loginPage.open();
    }

    // Test 1: Verify login page title
    @Test
    public void pageTitleTest() {
        Assert.assertTrue(
            driver.getTitle().contains("Test Login"),
            "Login page title should be correct"
        );
    }

    // Test 2: Verify username field
    @Test
    public void usernameFieldTest() {
        Assert.assertTrue(loginPage.isUsernameDisplayed());
    }

    // Test 3: Verify password field
    @Test
    public void passwordFieldTest() {
        Assert.assertTrue(loginPage.isPasswordDisplayed());
    }

    // Test 4: Verify login button
    @Test
    public void loginButtonTest() {
        Assert.assertTrue(loginPage.isLoginButtonDisplayed());
    }

    // Test 5: Successful login
    @Test
    public void validLoginTest() {
        loginPage.login("student", "Password123");

        Assert.assertTrue(loginPage.isLoginSuccessful());
        Assert.assertEquals(
            loginPage.getHeading(),
            "Logged In Successfully"
        );
    }

    // Test 6: Invalid username
    @Test
    public void invalidUsernameTest() {
        loginPage.login("wronguser", "Password123");

        Assert.assertTrue(
            loginPage.getErrorMessage().contains(
                "Your username is invalid!"
            )
        );
    }

    // Test 7: Invalid password
    @Test
    public void invalidPasswordTest() {
        loginPage.login("student", "wrongpassword");

        Assert.assertTrue(
            loginPage.getErrorMessage().contains(
                "Your password is invalid!"
            )
        );
    }

    // Test 8: Both credentials invalid
    @Test
    public void invalidBothCredentialsTest() {
        loginPage.login("wronguser", "wrongpassword");

        Assert.assertTrue(
            loginPage.getErrorMessage().contains(
                "Your username is invalid!"
            )
        );
    }

    // Test 9: Empty username
    @Test
    public void emptyUsernameTest() {
        loginPage.login("", "Password123");

        Assert.assertFalse(
            loginPage.isLoginSuccessful(),
            "Empty username must not log in successfully"
        );
    }

    // Test 10: Empty password
    @Test
    public void emptyPasswordTest() {
        loginPage.login("student", "");

        Assert.assertFalse(
            loginPage.isLoginSuccessful(),
            "Empty password must not log in successfully"
        );
    }

    // Test 11: Both fields empty
    @Test
    public void emptyBothFieldsTest() {
        loginPage.login("", "");

        Assert.assertFalse(
            loginPage.isLoginSuccessful(),
            "Empty credentials must not log in successfully"
        );
    }

    // Test 12: Logout after successful login
    @Test
    public void logoutTest() {
        loginPage.login("student", "Password123");

        Assert.assertTrue(
            loginPage.isLoginSuccessful(),
            "Login should succeed before logout"
        );

        loginPage.logout();

        Assert.assertTrue(
            loginPage.isLoginPageDisplayed(),
            "Logout should return to the login page"
        );
    }

    // Capture screenshot only if a test unexpectedly fails
    @AfterMethod(alwaysRun = true)
    public void tearDown(ITestResult result) {
        try {
            if (result.getStatus() == ITestResult.FAILURE
                    && driver != null) {
                ScreenshotUtil.capture(
                    driver,
                    result.getName()
                );
            }
        } finally {
            if (driver != null) {
                driver.quit();
                driver = null;
            }
        }
    }
}
