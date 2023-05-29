import java.io.IOException;

/**
 * Main class, run the whole application.
 */
public class Main {
    public static void main(String[] args) {
        run();

    }

    /**
     * Run the whole application.
     */
    private static void run() {
        PropertiesManager propertiesManager;
        try {
            propertiesManager = new PropertiesManager("res/config.properties");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        if (!propertiesManager.ifConfigured()) {
            new ConfigurationWindow();
        }
        else if (propertiesManager.ifLoggedIn()){
            new MainWindow();
        }
        else {
            new LoginWindow();
        }



    }

}
/*
Temporary passwords for test users, feel free to use
admin admin123
admin2 admin2
tester1 tester1
Szymon Stanislaw Lasota mySecurePassword
 */
