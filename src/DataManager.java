import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class DataManager {

    enum DataType {DATA, VALUE}
    private final String login;
    private final DatabaseConnector dbConnector;

    DataManager() throws IOException {
        PropertiesManager propertiesManager = new PropertiesManager("res/config.properties");
        this.login = propertiesManager.getUserId();
        this.dbConnector = new DatabaseConnector();
    }

    ArrayList<DataRecord> getDataRecords(String type) throws SQLException {
        return dbConnector.getDataRecords(login, type);
    }

    ArrayList<String> getAllTypes() {
        ArrayList<String> types;
        try {
            types = dbConnector.getTypes(login);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        if (types.size() == 0) {
            types.add("temperature");
        }
        return types;
    }

    void InsertNewRow(String type, float value, LocalDateTime date) {
        DataRecord record = getRecord(type, value, date);
        try {
            dbConnector.insertRow(record);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    void deleteRow(String type, float value, LocalDateTime date) {
        DataRecord record = getRecord(type, value, date);
        try {
            dbConnector.deleteRow(record);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    DataRecord getRecord(String type, float value, LocalDateTime date) {
        return new DataRecord(login, value, date, type);
    }

    void setValue(String type, float value, LocalDateTime date, float oldValue) throws SQLException {
        dbConnector.setValue(getRecord(type, value, date), oldValue);
    }
}
