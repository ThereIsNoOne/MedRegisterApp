import java.io.IOException;
import java.sql.*;

// TODO: Add dump method

public class Main {
    public static void main(String[] args) throws SQLException, IOException {

        DatabaseConnector connector = new DatabaseConnector();

        connector.importAuthDB();

        connector.getValue();
    }

}