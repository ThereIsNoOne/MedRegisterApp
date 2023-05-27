import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Provides implementation of properties manager, responsible for managing config file.
 */
class PropertiesManager {

    private FileInputStream propertiesInput;
    private FileOutputStream propertiesOutput;
    private final Properties properties;
    private final String configPath;

    /**
     * Create a new PropertiesManager.
     * @param configPath path to config file
     * @throws IOException Thrown if an error occurs during connection to the config file.
     */
    PropertiesManager (String configPath) throws IOException {
        this.configPath = configPath;
        this.propertiesInput = new FileInputStream(configPath);
        this.properties = new Properties();
        this.properties.load(propertiesInput);
    }

    /**
     * Open an input stream.
     * @throws IOException Thrown if an error occurs during connection to the config file.
     */
    private void openInputStream() throws IOException {
        propertiesInput = new FileInputStream(configPath);
        properties.load(propertiesInput);
    }

    /**
     * Close input stream.
     * @throws IOException Thrown if an error occurs during connection to the config file.
     */
    private void closeInputStream() throws IOException {
        propertiesInput.close();
    }

    /**
     * Open output stream.
     * @throws IOException Thrown if an error occurs during connection to the config file.
     */
    private void openOutputStream() throws IOException {
        propertiesOutput = new FileOutputStream(configPath);
    }

    /**
     * Close output stream.
     * @throws IOException Thrown if an error occurs during connection to the config file.
     */
    private void closeOutputStream() throws IOException {
        propertiesOutput.close();
    }

    /**
     * Get name of database.
     * @return database name
     */
    String getDBName() {
        return properties.getProperty("DBName");
    }

    /**
     * Get password for mysql server.
     * @return password
     */
    String getDBPassword() {
        return properties.getProperty("DBPassword");
    }

    /**
     * Get name of database server user.
     * @return server username
     */
    String getDBUser() {
        return  properties.getProperty("DBUser");
    }

    /**
     * Get path to database server CLI.
     * @return path to database server CLI
     */
    String getDBPath() {
        return properties.getProperty("DBPath") + "\\";
    }

    /**
     * Get name of table containing authorization credentials name.
     * @return authorization table nam.
     */
    String getAuthName() {
        return properties.getProperty("authName");
    }

    /**
     * Get path to table containing authorization credentials backup file.
     * @return path to authorization table backup file.
     */
    String getAuthPath() {
        return properties.getProperty("authPath");
    }

    /**
     * Get name of table containing user data.
     * @return name of user's table
     */
    String getRegName() {
        return properties.getProperty("regName");
    }

    /**
     * Get path to the backup file, for user's data table.
     * @return path to the backup file
     */
    String getRegPath() {
        return properties.getProperty("regPath");
    }

    /**
     * Get url to database server.
     * @return url to database server
     */
    String getUrl() {
        return properties.getProperty("url") + properties.getProperty("DBName");
    }

    /**
     * Check whether connection to database server is configured.
     * @return true if connection to database server is configured, otherwise false
     */
    boolean ifConfigured() {
        return Boolean.parseBoolean(properties.getProperty("configured"));
    }

    /**
     * Check whether user is logged in.
     * @return true if user is logged in, otherwise false
     */
    boolean ifLoggedIn() {
        return Boolean.parseBoolean(properties.getProperty("loggedIn"));
    }

    /**
     * Get user login (if logged in).
     * @return user's login
     */
    String getUserId() {
        return properties.getProperty("userId");
    }

    /**
     * Set user login (if logged in).
     * @param userId user's login
     */
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

    /**
     * Set information if connection to the database configured.
     * @param configured info if connection is configured
     */
    void setConfigured(boolean configured) {
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
        properties.setProperty("configured", Boolean.toString(configured));
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

    /**
     * Set information if user is logged in.
     * @param loggedIn info if user is logged in
     */
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
        properties.setProperty("loggedIn", Boolean.toString(loggedIn));
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

    /**
     * Sets information about database connection.
     * @param port port used to connect to database
     * @param dbUser name of database user
     * @param dbPassword password to database server
     * @param dbName name of database
     * @param dbPath path to database server CLI
     */
    void setDatabaseInfo(String port, String dbUser, String dbPassword, String dbName, String dbPath) {
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
        properties.setProperty("url", "jdbc:mysql://localhost:"+port+"/");
        properties.setProperty("DBName", dbName);
        properties.setProperty("DBPassword", dbPassword);
        properties.setProperty("DBPath","\""+dbPath);
        properties.setProperty("DBUser", dbUser);
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
