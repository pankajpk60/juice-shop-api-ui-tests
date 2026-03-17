package juiceshop.utilsobjects;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class DataReader {

    private static Properties properties;

    static {
        try {
            FileInputStream fis = new FileInputStream(System.getProperty("user.dir") + "/src/test/resources/configdata.properties");
            properties = new Properties();
            properties.load(fis);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load test data", e);
        }

    }

    public static String get(String key) {
        return properties.getProperty(key , "Key not found");
    }


}
