import java.io.IOException;
import java.sql.SQLException;

public class DataManager {

    private String login;
    private final DatabaseConnector dbConnector;

    DataManager(String login) throws IOException {
        this.login = login;
        this.dbConnector = new DatabaseConnector();
    }

    DataRecord[] getDataRecords(String type) throws SQLException {

        return dbConnector.getDataRecords(login, type);
    }


}
