import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

/**
 * Class responsible for managing connection to database.
 */
class DatabaseConnector {

    private enum Mode {EXPORT, IMPORT}

    private final String user;
    private final String password;
    private final String dbPath;
    private final String dbName;
    private final String authName;
    private final String authPath;
    private final String regName;
    private final String regPath;
    private final String url;

    /**
     * Construct the database connector.
     * @throws IOException Thrown when cannot find properties files
     */
    DatabaseConnector() throws IOException {
        PropertiesManager propManager = new PropertiesManager("res/config.properties");
        this.user = propManager.getDBUser();
        this.password = propManager.getDBPassword();
        this.dbPath = propManager.getDBPath();
        this.dbName = propManager.getDBName();
        this.authName = propManager.getAuthName();
        this.authPath = propManager.getAuthPath();
        this.regName = propManager.getRegName();
        this.regPath = propManager.getRegPath();
        this.url = propManager.getUrl();
    }

    /**
     * Import database with authorization credentials, form backup file.
     */
    void importAuthDB() {
        String command = generateCommand(authName, authPath, Mode.IMPORT);
        try {
            Runtime.getRuntime().exec("cmd /c"+command);
        } catch (IOException e) {
            throw new RuntimeException("Check your SQL path, username or password to mySQL server.");
        }
    }

    /**
     * Export the database with the authorization credentials, to a backup file.
     */
    void exportAuthDB() {
        String command = generateCommand(authName, authPath, Mode.EXPORT);
        try {
            Runtime.getRuntime().exec("cmd /c "+command);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Import database with users information, form backup file.
     */
    void importRegDB() {
        String command = generateCommand(regName, regPath, Mode.IMPORT);
        try {
            Runtime.getRuntime().exec("cmd /c"+ command);
        } catch (IOException e) {
            throw new RuntimeException("Check your SQL path, username or password to mySQL server.");
        }
    }

    /**
     * Export the database with users information, to a backup file.
     */
    void exportRegDB() {
        String command = generateCommand(regName, regPath, Mode.EXPORT);
        try {
            Runtime.getRuntime().exec("cmd /c "+command);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Export the test database, to a backup file.
     */
    void exportTest(String testName, String testPath) throws IOException {
        String command = generateCommand(testName, testPath, Mode.EXPORT);
        Runtime.getRuntime().exec("cmd /c "+command);

    }

    /**
     * Import test database, form backup file.
     */
    void importTest(String testName, String testPath) throws IOException {
        String command = generateCommand(testName, testPath, Mode.IMPORT);
        Runtime.getRuntime().exec("cmd /c "+command);
    }

    /**
     * Get values from test database.
     * @return number of rows in test database
     * @throws SQLException Thrown when an error occurs while reading data from database
     */
    private int getTestValues() throws SQLException {
        Connection connection = generateConnection();
        Statement statement;
        try {
            statement = connection.createStatement();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        ResultSet resultSet = statement.executeQuery("select count(*) from test;");
        resultSet.next();
        return resultSet.getInt(1);
    }

    /**
     * Write test values ot test database.
     * @throws SQLException Thrown when an error occurs while writing to the database
     */
    private void writeTestValues() throws SQLException {
        Connection connection = generateConnection();

        String sqlStmt = "insert into test values (?, ?)";

        PreparedStatement prepStmt = connection.prepareStatement(sqlStmt);
        prepStmt.setString(1, "test");
        prepStmt.setString(2, "pass");
        prepStmt.executeUpdate();
    }

    /**
     * Imports all databases from their backups files.
     * @param testName test database name
     * @param testPath path to test database
     * @throws IOException When there is no test database backup file
     */
    void importAll(String testName, String testPath) throws IOException {
        importAuthDB();
        importRegDB();
        importTest(testName, testPath);
    }

    /**
     * Test connection to database server.
     * @param testName test database name
     * @param testPath path to test database
     * @throws IOException Thrown when there is no test
     * @throws SQLException Thrown when error occurs during connecting to database server
     * @throws InterruptedException Thrown when user exits the process during runtime
     */
    void testConnection (String testName, String testPath) throws IOException, SQLException, InterruptedException {
        importAll(testName, testPath);
        TimeUnit.SECONDS.sleep(2); // TEMP!
        exportTest(testName, testPath);
        TimeUnit.SECONDS.sleep(1);
        int beforeImport = getTestValues();
        writeTestValues();
        System.out.println(beforeImport);
        importTest(testName, testPath);
        TimeUnit.SECONDS.sleep(1); // Time needed to update a database (I think it will have to stay this way) :(
        System.out.println(getTestValues());
        if (getTestValues() != beforeImport) {
            throw new IOException("Table was not updated!");
        }
    }

    /**
     * Get number of data records for given user and type.
     * @param login user's login
     * @param type type of medical parameter to look for
     * @return number of records
     * @throws SQLException Thrown when database malfunction
     */
    int getNumberOfRows(String login, String type) throws SQLException {
        Connection connection = generateConnection();
        Statement statement;
        try {
            statement = connection.createStatement();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        ResultSet resultSet = statement.executeQuery("select count(*) from reg where type=\""+type+"\" and login=\""+login+"\";");
        resultSet.next();
        return resultSet.getInt(1);
    }

    /**
     * Insert new record to the database.
     * @param dataRecord Record to be inserted into the database
     * @throws SQLException Thrown when it is not possible to insert the record into the database
     */
    void insertRow(DataRecord dataRecord) throws SQLException {
        Connection connection = generateConnection();

        String sqlStm = "INSERT INTO reg values (?, ?, ?, ?);";

        PreparedStatement prepStmt = connection.prepareStatement(sqlStm);
        prepStmt.setString(1, dataRecord.getLogin());
        prepStmt.setString(2, dataRecord.getType());
        prepStmt.setFloat(3, dataRecord.getValue());
        prepStmt.setTimestamp(4, Timestamp.valueOf(dataRecord.getDate()));
        prepStmt.executeUpdate();
    }

    /**
     * Delete data record from the database.
     * @param dataRecord Record to be deleted from the database
     * @throws SQLException Thrown if an error occurs during deleting record from the database
     */
    void deleteRow(DataRecord dataRecord) throws SQLException {
        Connection connection = generateConnection();

        String sqlStm = "DELETE FROM reg WHERE login=? and type=? and value=? and register_time=?;";
        PreparedStatement prepStmt = connection.prepareStatement(sqlStm);
        prepStmt.setString(1, dataRecord.getLogin());
        prepStmt.setString(2, dataRecord.getType());
        prepStmt.setFloat(3, dataRecord.getValue());
        prepStmt.setTimestamp(4, Timestamp.valueOf(dataRecord.getDate()));
        prepStmt.executeUpdate();
    }

    /**
     * Get all records from the database for given user and type.
     * @param login user's login
     * @param type type of medical parameter to retrieve from the database
     * @return List of data records retrieved from the database
     * @throws SQLException Thrown if an error occurs during retrieving data from database
     */
    ArrayList<DataRecord> getDataRecords(String login, String type) throws SQLException {
        int rowsNumber = getNumberOfRows(login, type);
        Connection connection = generateConnection();
        Statement statement;
        try {
            statement = connection.createStatement();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        ArrayList<DataRecord> data = new ArrayList<>();

        ResultSet resultSet = statement.executeQuery("select * from reg where type=\""+type+"\" and login=\""+login+"\";");
        while (resultSet.next()) {
            data.add(
                    new DataRecord(
                    resultSet.getString(1),
                    resultSet.getFloat(3),
                    resultSet.getTimestamp(4).toLocalDateTime(),
                    resultSet.getString(2)
                    )
            );
        }
        return data;
    }

    /**
     * Checks whether login is already used.
     * @param login Login to be checked
     * @return True if login is not used, otherwise false
     * @throws SQLException Thrown when error occurs when connecting to database
     */
    boolean validateRegistration(String login) throws SQLException {
        Connection connection = generateConnection();
        Statement statement;
        try {
            statement = connection.createStatement();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        ResultSet resultSet = statement.executeQuery("select distinct(login) from auth;");

        while (resultSet.next()) {
            if (login.equals(resultSet.getString(1)) || login.equals("null")) {
                return false;
            }
        }
        return true;
    }

    /**
     * Change the value of the certain record.
     * @param dataRecord Record with new medical parameter value
     * @param oldValue old value of the medical parameter
     * @throws SQLException Thrown when error occurs during connecting to database
     */
    void setValue(DataRecord dataRecord, float oldValue) throws SQLException {
        Connection connection = generateConnection();
        String statement = "UPDATE reg SET value=? WHERE login=? AND type=? AND register_time=? AND value=?;";
        PreparedStatement preparedStatement = connection.prepareStatement(statement);
        preparedStatement.setString(2, dataRecord.getLogin());
        preparedStatement.setString(3, dataRecord.getType());
        preparedStatement.setFloat(5, oldValue);
        preparedStatement.setFloat(1, dataRecord.getValue());
        preparedStatement.setTimestamp(4, Timestamp.valueOf(dataRecord.getDate()));

        preparedStatement.executeUpdate();
    }

    /**
     * Get all types of medical parameters for certain user.
     * @param login user's login
     * @return list of all types of medical parameters
     * @throws SQLException Thrown when error occurs during connection to database
     */
    ArrayList<String> getTypes(String login) throws SQLException {
        Connection connection = generateConnection();
        Statement statement;
        try {
            statement = connection.createStatement();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        ArrayList<String> result = new ArrayList<>();
        ResultSet resultSet = statement.executeQuery("select distinct(type) from reg where login=\""+login+"\";");
        while (resultSet.next()) {
            result.add(resultSet.getString(1));
        }
        return result;
    }

    /**
     * Register a new user and writes login and hashed password to the database.
     * @param login Login of the new user
     * @param hash Hash of the password of the new user
     * @throws SQLException Thrown if an error occurs while connecting to the database
     */
    void registerUser(String login, String hash) throws SQLException {
        Connection connection = generateConnection();

        String sqlStatement = "INSERT INTO auth VALUES (?, ?);";
        PreparedStatement prepStmt = connection.prepareStatement(sqlStatement);
        prepStmt.setString(1, login);
        prepStmt.setString(2, hash);
        prepStmt.executeUpdate();
    }

    /**
     * Get hashed password for provided user.
     * @param login User's login
     * @return Hashed password
     * @throws SQLException Thrown if an error occurs while connecting to the database
     * @throws AuthorizationException When an error occurs during authorization
     */
    String getHash (String login) throws SQLException, AuthorizationException {
        Connection connection = generateConnection();
        Statement statement = connection.createStatement();

        ResultSet resultSet = statement.executeQuery("select * from auth where login=\""+login+"\";");
        resultSet.next();
        try {
            return resultSet.getString("hash");
        }
        catch (SQLException e) {
            throw new AuthorizationException(e);
        }
    }

    /**
     * Connects to database server.
     * @return Connection to database server
     */
    private Connection generateConnection() {
        try {
            return DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            throw new RuntimeException(("Cannot connect to database, check if it exists and/or your password/login!"));
        }
    }

    /**
     * Generate command to import or export database.
     * @param tableName Date of table to export/import
     * @param tablePath Path to a backup file of the table
     * @param mode Export or import mode
     * @return Command to import or export database
     */
    private String generateCommand(String tableName, String tablePath, Mode mode) {
        switch (mode) {
            case EXPORT -> {
                return dbPath+"mysqldump.exe\" -h localhost -u"+user+" -p"+password+" "+dbName+" --complete-insert --result-file="+tablePath+" "+tableName;
            }
            case IMPORT -> {
                return dbPath+"mysql.exe\" -h localhost -u"+user+" -p"+password+" "+dbName+" < "+tablePath;
            }
            default -> throw new IllegalStateException("Unknown mode");
        }
    }

}
