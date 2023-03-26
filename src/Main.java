import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.time.LocalDateTime;

public class Main {
    public static void main(String[] args) throws IOException, SQLException, NoSuchAlgorithmException {

        DatabaseConnector connector = new DatabaseConnector();

        AuthorizationManager authManager = new AuthorizationManager();
        authManager.authorizeUser("admin", "admin123");
    }

}