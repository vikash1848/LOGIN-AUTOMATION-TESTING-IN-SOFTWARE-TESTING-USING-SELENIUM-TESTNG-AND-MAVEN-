
package utils;

import org.testng.annotations.DataProvider;

public class TestData {

    @DataProvider(name = "invalidCredentials")
    public Object[][] invalidCredentials() {
        return new Object[][] {
            {
                "wronguser",
                "Password123",
                "Your username is invalid!"
            },
            {
                "student",
                "wrongpassword",
                "Your password is invalid!"
            },
            {
                "wronguser",
                "wrongpassword",
                "Your username is invalid!"
            }
        };
    }
}
