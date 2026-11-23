
package utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigReader {

    private static final Properties PROPERTIES = new Properties();

    static {
        try (InputStream input = ConfigReader.class
                .getClassLoader()
                .getResourceAsStream("config.properties")) {

            if (input == null) {
                throw new IllegalStateException(
                    "config.properties was not found in src/test/resources"
                );
            }

            PROPERTIES.load(input);

        } catch (IOException e) {
            throw new ExceptionInInitializerError(e);
        }
    }

    public static String get(String key) {
        String value = PROPERTIES.getProperty(key);

        if (value == null) {
            throw new IllegalArgumentException(
                "Missing configuration property: " + key
            );
        }

        return value.trim();
    }

    public static int getInt(String key) {
        return Integer.parseInt(get(key));
    }
}
