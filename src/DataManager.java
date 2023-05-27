import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 * Helper class for managing data.
 */
public class DataManager {

    enum DataType {DATA, VALUE}
    private final String login;
    private final DatabaseConnector dbConnector;

    /**
     * Create a DataManager instance.
     * @throws IOException When there is no properties file
     */
    DataManager() throws IOException {
        PropertiesManager propertiesManager = new PropertiesManager("res/config.properties");
        this.login = propertiesManager.getUserId();
        this.dbConnector = new DatabaseConnector();
    }

    /**
     * Get all data from database for the user and type of medical parameter.
     * @param type Type of medical parameter
     * @return List of all data from the database
     * @throws SQLException Thrown if an error occurs while connecting to the database
     */
    ArrayList<DataRecord> getDataRecords(String type) throws SQLException {
        return dbConnector.getDataRecords(login, type);
    }

    /**
     * Get all types of medical parameters for the user.
     * @return List of all types of medical parameters
     */
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

    /**
     * Insert new record into database.
     * @param type Type of medical parameter
     * @param value Value of the record
     * @param date Date of the record
     */
    void InsertNewRow(String type, float value, LocalDateTime date) {
        DataRecord record = getRecord(type, value, date);
        try {
            dbConnector.insertRow(record);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Delete a data record from the database.
     * @param type Type of medical parameter
     * @param value Value of the medical record
     * @param date Date of the medical record
     */
    void deleteRow(String type, float value, LocalDateTime date) {
        DataRecord record = getRecord(type, value, date);
        try {
            dbConnector.deleteRow(record);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Get the medical parameter from the database.
     * @param type Type of the parameter
     * @param value Value of the record
     * @param date Date to get the record
     * @return Instance of the record
     */
    DataRecord getRecord(String type, float value, LocalDateTime date) {
        return new DataRecord(login, value, date, type);
    }

    /**
     * Set new value of the record for certain record.
     * @param type Type of medical parameter
     * @param value Value of the record
     * @param date Date to set the record
     * @param oldValue Old value of the record
     * @throws SQLException Thrown when error occurs during connecting to database
     */
    void setValue(String type, float value, LocalDateTime date, float oldValue) throws SQLException {
        dbConnector.setValue(getRecord(type, value, date), oldValue);
    }
}
