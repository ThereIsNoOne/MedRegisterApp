import java.io.IOException;
import java.sql.*;

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

    void importAuthDB() {
        String command = generateCommand(authName, authPath, Mode.IMPORT);
        try {
            Runtime.getRuntime().exec("cmd /c"+command);
        } catch (IOException e) {
            throw new RuntimeException("Check your SQL path, username or password to mySQL server.");
        }
    }

    void exportAuthDB() {
        String command = generateCommand(authName, authPath, Mode.EXPORT);
//        System.out.println(command);
        try {
            Runtime.getRuntime().exec("cmd /c start cmd.exe /K "+command);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    void importRegDB() {
        String command = generateCommand(regName, regPath, Mode.IMPORT);
        try {
            Runtime.getRuntime().exec("cmd /c"+ command);
        } catch (IOException e) {
            throw new RuntimeException("Check your SQL path, username or password to mySQL server.");
        }
    }

    void exportRegDB() {
        String command = generateCommand(regName, regPath, Mode.EXPORT);
        try {
            Runtime.getRuntime().exec("cmd /c "+command);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

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

    void insertRow(DataRecord dataRecord) throws SQLException {
        Connection connection = generateConnection();

        // TODO: Add validation for rows (cannot add two identical rows, the same date and so on)
        String sqlStm = "INSERT INTO reg values (?, ?, ?, ?);";

        PreparedStatement prepStmt = connection.prepareStatement(sqlStm);
        prepStmt.setString(1, dataRecord.login);
        prepStmt.setString(2, dataRecord.type);
        prepStmt.setFloat(3, dataRecord.value);
        prepStmt.setTimestamp(4, Timestamp.valueOf(dataRecord.date));
        prepStmt.executeUpdate();
    }

    DataRecord[] getDataRecords(String login, String type) throws SQLException {
        int rowsNumber = getNumberOfRows(login, type);
        int i = 0;
        Connection connection = generateConnection();
        Statement statement;
        try {
            statement = connection.createStatement();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        DataRecord[] data = new DataRecord[rowsNumber];

        ResultSet resultSet = statement.executeQuery("select * from reg where type=\""+type+"\" and login=\""+login+"\";");
        while (resultSet.next()) {
            data[i] = new DataRecord(
                    resultSet.getString(1),
                    resultSet.getFloat(3),
                    resultSet.getTimestamp(4).toLocalDateTime(),
                    resultSet.getString(2)
            );
            i++;
        }
        return data;
    }

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
            if (login.equals(resultSet.getString(1))) {
                return false;
            }
        }
        return true;
    }

    void registerUser(String login, String hash) throws SQLException {
        Connection connection = generateConnection();

        String sqlStatement = "INSERT INTO auth VALUES (?, ?);";
        PreparedStatement prepStmt = connection.prepareStatement(sqlStatement);
        prepStmt.setString(1, login);
        prepStmt.setString(2, hash);
        prepStmt.executeUpdate();
    }

    String getHash (String login) throws SQLException {
        Connection connection = generateConnection();
        Statement statement = connection.createStatement();

        ResultSet resultSet = statement.executeQuery("select * from auth where login=\""+login+"\";");

        resultSet.next();
        return resultSet.getString("hash");
    }

    private Connection generateConnection() {
        try {
            return DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            throw new RuntimeException(("Cannot connect to database, check if it exists and/or your password/login!"));
        }
    }


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
