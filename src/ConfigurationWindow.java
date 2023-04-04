import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.sql.SQLException;

public class ConfigurationWindow extends JFrame {

    private final GridBagConstraints constraints = new GridBagConstraints();

    private JTextField serverPortEntry;
    private JTextField serverUserEntry;
    private JPasswordField serverPasswordEntry;
    private JTextField databaseNameEntry;
    private JTextField databasePathEntry;

    ConfigurationWindow() {
        this.setTitle("Database configuration");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setSize(700, 420);
        this.setLayout(new GridBagLayout());
        this.getContentPane().setBackground(new Color(0x0));

        constraints.fill = GridBagConstraints.NONE;
        constraints.insets = new Insets(5, 75, 5, 75);
        constraints.weightx = 1;

        drawLabels();
        drawButtons();
        drawEntries();
        this.setVisible(true);
    }

    private void drawLabels() {
        constraints.gridwidth = 3;
        constraints.weightx = 2;
        JLabel serverPortLabel = new JLabel();
        serverPortLabel.setText("Enter port for your mysql server:");
        SetUpUtils.setUpLabel(this, serverPortLabel, 0, 0, constraints);

        JLabel serverUserLabel = new JLabel();
        serverUserLabel.setText("Enter username for your mysql server:");
        SetUpUtils.setUpLabel(this, serverUserLabel, 0, 2, constraints);

        JLabel serverPasswordLabel = new JLabel();
        serverPasswordLabel.setText("Enter password for your mysql server:");
        SetUpUtils.setUpLabel(this, serverPasswordLabel, 0, 4, constraints);

        JLabel databaseNameLabel = new JLabel();
        databaseNameLabel.setText("Enter your database name:");
        SetUpUtils.setUpLabel(this, databaseNameLabel, 0, 6, constraints);

        JLabel mysqlPath = new JLabel(); ;
        mysqlPath.setText("Enter path to your mysql bin directory:");
        SetUpUtils.setUpLabel(this, mysqlPath, 0, 8, constraints);

        constraints.gridwidth = 1;
        constraints.weightx = 1;
    }

    private void drawButtons() {
        JButton confirmButton = new JButton();
        confirmButton.setText("Confirm");
        confirmButton.addActionListener(e -> confirmDB());
        SetUpUtils.setUpButton(this, confirmButton, 1, 10, constraints);

        JButton openDBPath = new JButton();
        openDBPath.setText("Open");
        openDBPath.addActionListener(e -> openPathChooser());
        SetUpUtils.setUpButton(this, openDBPath, 2, 9, constraints);
    }

    private void drawEntries() {
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.gridwidth = 3;
        constraints.weightx = 2;

        serverPortEntry = new JTextField();
        SetUpUtils.setUpEntry(this, serverPortEntry, 0, 1, constraints);

        serverUserEntry = new JTextField();
        SetUpUtils.setUpEntry(this, serverUserEntry, 0, 3, constraints);

        serverPasswordEntry = new JPasswordField();
        SetUpUtils.setUpEntry(this, serverPasswordEntry, 0, 5, constraints);

        databaseNameEntry = new JTextField();
        SetUpUtils.setUpEntry(this, databaseNameEntry, 0, 7, constraints);

        constraints.gridwidth = 2;
        databasePathEntry = new JTextField();
        SetUpUtils.setUpEntry(this, databasePathEntry, 0, 9, constraints);

        constraints.weightx = 1;
        constraints.gridwidth = 1;
        constraints.fill = GridBagConstraints.NONE;
    }


    private void confirmDB () {
        // connectToDatabase("3306", "root", "jkl123JKL!@#", "medreg", "C:\Program Files\MySQL\MySQL Server 8.0\bin\");

        PropertiesManager propertiesManager;
        try {
            propertiesManager = new PropertiesManager("res/config.properties");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(
                    this,
                    "Properties file not found, check if it exists in res/ directory",
                    "Fatal error",
                    JOptionPane.ERROR_MESSAGE
            );
            return;
        }
        propertiesManager.setDatabaseInfo(
                serverPortEntry.getText(),
                serverUserEntry.getText(),
                SetUpUtils.readPassword(serverPasswordEntry),
                databaseNameEntry.getText(),
                databasePathEntry.getText()
        );

        DatabaseConnector connector;
        try {
            connector = new DatabaseConnector();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            String testPath = "SQL/test.sql";
            String testName = "test";
            connector.testConnection(testName, testPath);
        } catch (IOException | SQLException | InterruptedException |RuntimeException e) {
            JOptionPane.showMessageDialog(
                    this,
                    "Something went wrong, check all information you provided, if it does not work, then we have big problem :(",
                    "Fatal error",
                    JOptionPane.ERROR_MESSAGE
            );
            propertiesManager.setConfigured(false);
            return;
        }
        propertiesManager.setConfigured(true);
        System.out.println("Everything is ok!");
        new LoginWindow();
        this.dispose();
    }

    private void openPathChooser() {
        JFileChooser chooser = new JFileChooser("c:");
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        int response = chooser.showOpenDialog(this);
        if (response == JFileChooser.APPROVE_OPTION) {
            databasePathEntry.setText(chooser.getSelectedFile().getAbsolutePath());
        }

    }
}
