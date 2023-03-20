import java.sql.*;

public class Main {
    public static void main(String[] args) throws SQLException {

        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/mydb", "root", "x2nmlc10");

        Statement statement = connection.createStatement();

        ResultSet resultSet = statement.executeQuery("select * from employees");

        while (resultSet.next()) {
            System.out.println(resultSet.getString("first_name"));
        }
    }
}