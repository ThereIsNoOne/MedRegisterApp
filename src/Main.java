import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;

public class Main {
    public static void main(String[] args) throws IOException, SQLException {

        DatabaseConnector connector = new DatabaseConnector();
        connector.insertRow(1, 36.7f, LocalDateTime.now(), "temperature");

    }

}