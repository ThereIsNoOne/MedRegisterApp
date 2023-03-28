import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.Arrays;

// TODO: Add getter for current user data.
// TODO: Start working on GUI (finally).
// REMINDER: Use JTable for GUI table.

public class Main {
    public static void main(String[] args) throws IOException, SQLException, NoSuchAlgorithmException {

        // Temporary test area

        DatabaseConnector connector = new DatabaseConnector();
        AuthorizationManager authManager = new AuthorizationManager();
        PropertiesManager propertiesManager = new PropertiesManager("res/config.properties");
        DataManager dataManager = new DataManager("admin");
        /*
         * This is temporary method, to test for different users. You have to use mysql, on localhost and then provide your: port, dbUser, dbPassword, dbName and
         * path to bin dir for yours my sql server (for running some commands from mysql cli.
        */

        DataRecord[] data = dataManager.getDataRecords("blood pressure");;
        System.out.println(Arrays.toString(data));

//        connectToDatabase("3306", "root", "jkl123JKL!@#", "medreg", "C\\:\\Program Files\\MySQL\\MySQL Server 8.0\\bin\\");



//        authManager.authorizeUser("Szymon Stanislaw Lasota", "mySecurePassword"); // do not change!
    }

    public static void connectToDatabase(String port, String dbUser, String dbPassword, String dbName, String dbPath) throws IOException {
        PropertiesManager propertiesManager = new PropertiesManager("res/config.properties");
        propertiesManager.setDatabaseInfo(port, dbUser, dbPassword, dbName, dbPath);
    }

}

/*
admin admin123
admin2 admin2
tester1 tester1
Szymon Stanislaw Lasota mySecurePassword
 */
