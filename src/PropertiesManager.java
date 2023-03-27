import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

class PropertiesManager {

    private FileInputStream propertiesInput;
    private FileOutputStream propertiesOutput;
    private final Properties properties;
    private final String configPath;

    PropertiesManager (String configPath) throws IOException {
        this.configPath = configPath;
        this.propertiesInput = new FileInputStream(configPath);
        this.properties = new Properties();
        this.properties.load(propertiesInput);
    }

    private void openInputStream() throws IOException {
        propertiesInput = new FileInputStream(configPath);
        properties.load(propertiesInput);
    }

    private void closeInputStream() throws IOException {
        propertiesInput.close();
    }

    private void openOutputStream() throws IOException {
        propertiesOutput = new FileOutputStream(configPath);
    }

    private void closeOutputStream() throws IOException {
        propertiesOutput.close();
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

    boolean IfLoggedIn() {
        return Boolean.parseBoolean(properties.getProperty("loggedIn"));
    }

    String userId() {
        return properties.getProperty("userId");
    }

    void setUserId(String userId) {
        try {
            closeInputStream();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            openOutputStream();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        properties.setProperty("userId", userId);
        try {
            properties.store(propertiesOutput, null);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            openInputStream();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    void setLoggedIn(boolean loggedIn) {
        try {
            closeInputStream();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            openOutputStream();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        if (loggedIn) {
            properties.setProperty("loggedIn", "true");
        }
        else {
            properties.setProperty("loggedIn", "false");
        }
        try {
            properties.store(propertiesOutput, null);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            openInputStream();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
