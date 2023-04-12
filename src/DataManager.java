import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class DataManager {

    private String login;
    private final DatabaseConnector dbConnector;

    DataManager() throws IOException {
        PropertiesManager propertiesManager = new PropertiesManager("res/config.properties");
        this.login = propertiesManager.getUserId();
        this.dbConnector = new DatabaseConnector();
    }

    ArrayList<DataRecord> getDataRecords(String type) throws SQLException {
        return dbConnector.getDataRecords(login, type);
    }

    ArrayList<DataRecord> getTableRows(String type) {
        ArrayList<DataRecord> records = new ArrayList<DataRecord>();
        try {
            records = getDataRecords(type);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return records;
    }

    String[] getAllTypes() {
        ArrayList<String> types;
        try {
            types = dbConnector.getTypes();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return types.toArray(new String[0]);
    }

    void InsertNewRow(String type, float value, LocalDateTime date) {
        DataRecord record = getRecord(type, value, date);
        System.out.println(record);
        try {
            dbConnector.insertRow(record);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    DataRecord getRecord(String type, float value, LocalDateTime date) {
        return new DataRecord(login, value, date, type);
    }
}
