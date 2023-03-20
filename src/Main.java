import java.io.IOException;
import java.sql.*;

// TODO: Add dump method

public class Main {
    public static void main(String[] args) throws SQLException, IOException {

        update();

        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/medreg", "root", "jkl123JKL!@#");

        Statement statement = connection.createStatement();

        ResultSet resultSet = statement.executeQuery("select * from auth");

        while (resultSet.next()) {
            System.out.println(resultSet.getString("login"));
        }
    }

    public static void update() throws IOException {
        String command = "\"C:\\Program Files\\MySQL\\MySQL Server 8.0\\bin\\mysql.exe\" -h localhost -uroot -pjkl123JKL!@# medreg < SQL/auth.sql";
        Runtime.getRuntime().exec("cmd /c"+ command);
    }
}