import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

// TODO: Add method to update current user (userId and loggedIn) in config.properties file.
// TODO: Add getter for current user data.
// TODO: Start working on GUI (finally).

public class Main {
    public static void main(String[] args) throws IOException, SQLException, NoSuchAlgorithmException {

        DatabaseConnector connector = new DatabaseConnector();

        AuthorizationManager authManager = new AuthorizationManager();
        authManager.authorizeUser("Szymon Stanislaw Lasota", "mySecurepassword");
    }

}

/*
admin admin123
admin2 admin2
tester1 tester1
Szymon Stanislaw Lasota mySecurePassword
 */