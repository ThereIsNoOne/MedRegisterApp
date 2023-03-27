import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

// TODO: Add getter for current user data.
// TODO: Start working on GUI (finally).
// REMINDER: Use JTable for GUI table.

public class Main {
    public static void main(String[] args) throws IOException, SQLException, NoSuchAlgorithmException {

        DatabaseConnector connector = new DatabaseConnector();

        AuthorizationManager authManager = new AuthorizationManager();
        PropertiesManager propertiesManager = new PropertiesManager("res/config.properties");
//        propertiesManager.setUserId("Szymon Stanislaw Lasota");
        propertiesManager.setLoggedIn(true);
        authManager.authorizeUser("Szymon Stanislaw Lasota", "mySecurePassword");
    }

}

/*
admin admin123
admin2 admin2
tester1 tester1
Szymon Stanislaw Lasota mySecurePassword
 */
