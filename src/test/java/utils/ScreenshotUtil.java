
package utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

public class ScreenshotUtil {

    public static void capture(WebDriver driver, String testName) {
        if (driver == null) {
            return;
        }

        try {
            Path directory = Path.of("screenshots");
            Files.createDirectories(directory);

            String timestamp = LocalDateTime.now().format(
                DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss_SSS")
            );

            String safeName = testName.replaceAll(
                "[^a-zA-Z0-9_-]", "_"
            );

            File source = ((TakesScreenshot) driver)
                .getScreenshotAs(OutputType.FILE);

            Path destination = directory.resolve(
                safeName + "_" + timestamp + ".png"
            );

            Files.copy(source.toPath(), destination);

            System.out.println(
                "Screenshot saved: " + destination.toAbsolutePath()
            );

        } catch (IOException | RuntimeException e) {
            System.err.println(
                "Could not capture screenshot: " + e.getMessage()
            );
        }
    }
}
