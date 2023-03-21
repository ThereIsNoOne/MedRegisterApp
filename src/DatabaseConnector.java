import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;

public class DatabaseConnector {

    private String user;
    private String password;

    DatabaseConnector() throws IOException {
        String pathConfig = "res/config.properties";
        FileInputStream propsInput = new FileInputStream(pathConfig);
        Properties properties = new Properties();
        properties.load(propsInput);
        this.user = properties.getProperty("DB_USER");
        this.password = properties.getProperty("DB_PASSWORD");
    }

    public void importAuthDB() {
        String command = String.format("\"C:\\Program Files\\MySQL\\MySQL Server 8.0\\bin\\mysql.exe\" -h localhost -u"+this.user+" -p%s medreg < SQL/auth.sql", password);
        try {
            Runtime.getRuntime().exec("cmd /c"+command);
        } catch (IOException e) {
            throw new RuntimeException("Check your SQL path, username or password to mySQL server.");
        }
    }

    public void exportAuthDB() {

    }

    public void importRegDB() {
        String command = "\"C:\\Program Files\\MySQL\\MySQL Server 8.0\\bin\\mysql.exe\" -h localhost -u"+"root"+" -p"+this.password+" medreg < SQL/reg.sql";
        try {
            Runtime.getRuntime().exec("cmd /c"+ command);
        } catch (IOException e) {
            throw new RuntimeException("Check your SQL path, username or password to mySQL server.");
        }
    }

    public void exportRegDB() {

    }

    public void getValue() throws SQLException {
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/medreg", this.user, password);

        Statement statement = connection.createStatement();

        ResultSet resultSet = statement.executeQuery("select * from auth");

        while (resultSet.next()) {
            System.out.println(resultSet.getString("login"));
        }
    }

    public void setUser(String value) {
        this.user = value;
    }

    public void setPassword(String value) {
        this.password = value;
    }
}
