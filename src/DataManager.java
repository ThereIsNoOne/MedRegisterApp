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

    DataRecord[] getDataRecords(String type) throws SQLException {
        return dbConnector.getDataRecords(login, type);
    }

    Object[][] getTableRows(String type) {
        DataRecord[] records;
        try {
            records = getDataRecords(type);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        Object[][] result = new Object[records.length][4];
        int i = 0;
        for (DataRecord record : records) {
            result[i][0] = record.login;
            result[i][1] = record.type;
            result[i][2] = record.value;
            result[i][3] = record.date;
            i++;
        }
        return result;
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
        DataRecord record = new DataRecord(login, value, date, type);
        System.out.println(record);
        try {
            dbConnector.insertRow(record);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
