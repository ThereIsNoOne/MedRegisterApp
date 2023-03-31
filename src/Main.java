import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

// TODO: Add README file! IMPORTANT
// TODO: Add getter for current user data.
// TODO: Finish Configuration window
// TODO: Start working on Login/Register window
// TODO: Start working on main window
// TODO: Create class responsible for drawing plots (in main window)
// TODO: Ask Ola for duck drawing
// REMINDER: Use JTable for GUI table.

public class Main {
    public static void main(String[] args) throws IOException, SQLException, NoSuchAlgorithmException {

        // Temporary test area

        DatabaseConnector connector = new DatabaseConnector();
        AuthorizationManager authManager = new AuthorizationManager();
        PropertiesManager propertiesManager = new PropertiesManager("res/config.properties");
        DataManager dataManager = new DataManager("admin");

//        connector.exportRegDB();
//        connector.exportAuthDB();

//        DataRecord[] data = dataManager.getDataRecords("blood pressure");;


//        authManager.authorizeUser("Szymon Stanislaw Lasota", "mySecurePassword"); // do not change!
        ConfigurationWindow configWindow = new ConfigurationWindow();

    }

}
/*
Temporary passwords for test users
admin admin123
admin2 admin2
tester1 tester1
Szymon Stanislaw Lasota mySecurePassword
 */
