import java.io.IOException;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DatabaseConnector {

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

    public void importAuthDB() {
        String command = generateCommand(authName, authPath, Mode.IMPORT);
        try {
            Runtime.getRuntime().exec("cmd /c"+command);
        } catch (IOException e) {
            throw new RuntimeException("Check your SQL path, username or password to mySQL server.");
        }
    }

    public void exportAuthDB() {
        String command = generateCommand(authName, authPath, Mode.EXPORT);
        try {
            Runtime.getRuntime().exec("cmd /c "+command);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void importRegDB() {
        String command = generateCommand(regName, regPath, Mode.IMPORT);
        try {
            Runtime.getRuntime().exec("cmd /c"+ command);
        } catch (IOException e) {
            throw new RuntimeException("Check your SQL path, username or password to mySQL server.");
        }
    }

    public void exportRegDB() {
        String command = generateCommand(regName, regPath, Mode.EXPORT);
        try {
            Runtime.getRuntime().exec("cmd /c "+command);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void insertRow(int userId, float value, LocalDateTime date, String type) throws SQLException {
        Connection connection;
        try {
            connection = generateConnection();
        } catch (SQLException e) {
            throw new RuntimeException("Cannot connect to database, check if it exists and/or your password/login!");
        }
        // TODO: Add validation for rows (cannot add two identical rows, the same date and so on)
        String sqlStm = "INSERT INTO reg values (?, ?, ?, ?);";

        PreparedStatement prepStmt = connection.prepareStatement(sqlStm);
        prepStmt.setInt(1, userId);
        prepStmt.setString(2, type);
        prepStmt.setFloat(3, value);
        prepStmt.setTimestamp(4, Timestamp.valueOf(date));
        prepStmt.executeUpdate();
    }

    private Connection generateConnection() throws SQLException {

        return DriverManager.getConnection(url, user, password);
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
