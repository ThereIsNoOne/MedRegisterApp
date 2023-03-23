import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

class PropertiesManager {

    private final Properties properties;

    PropertiesManager (String configPath) throws IOException {
        FileInputStream propertiesInput = new FileInputStream(configPath);
        properties = new Properties();
        properties.load(propertiesInput);
    }

    String getDBName() {
        return properties.getProperty("DBName");
    }

    String getDBPassword() {
        return properties.getProperty("DBPassword");
    }

    String getDBUser() {
        return  properties.getProperty("DBUser");
    }

    String getDBPath() {
        return properties.getProperty("DBPath");
    }

    String getAuthName() {
        return properties.getProperty("authName");
    }

    String getAuthPath() {
        return properties.getProperty("authPath");
    }

    String getRegName() {
        return properties.getProperty("regName");
    }

    String getRegPath() {
        return properties.getProperty("regPath");
    }

    String getUrl() {
        return properties.getProperty("url") + properties.getProperty("DBName");
    }
}
