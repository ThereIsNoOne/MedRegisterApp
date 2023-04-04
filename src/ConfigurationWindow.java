import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
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
        drawLabels();
        drawButtons();
        drawEntries();
        this.setVisible(true);
        constraints.insets = new Insets(5, 10, 5, 10);
    }

    private void drawLabels() {
        constraints.weightx = 1.0;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        Label serverPortLabel = new Label();
        serverPortLabel.setText("Enter port for your mysql server:");
        serverPortLabel.setForeground(new Color(0xaaaaaa));
        serverPortLabel.setFont(new Font("Inter", Font.PLAIN, 16));
        constraints.gridy = 0;
        constraints.gridx = 0;
        this.add(serverPortLabel, constraints);

        Label serverUserLabel = new Label();
        serverUserLabel.setText("Enter username for your mysql server:");
        serverUserLabel.setForeground(new Color(0xaaaaaa));
        serverUserLabel.setFont(new Font("Inter", Font.PLAIN,16));
        constraints.gridy = 2;
        this.add(serverUserLabel, constraints);

        Label serverPasswordLabel = new Label();
        serverPasswordLabel.setText("Enter password for your mysql server:");
        serverPasswordLabel.setForeground(new Color(0xaaaaaa));
        serverPasswordLabel.setFont(new Font("Inter", Font.PLAIN, 16));
        constraints.gridy = 4;
        this.add(serverPasswordLabel, constraints);

        Label databaseNameLabel = new Label();
        databaseNameLabel.setText("Enter your database name:");
        databaseNameLabel.setForeground(new Color(0xaaaaaa));
        databaseNameLabel.setFont(new Font("Inter", Font.PLAIN, 16));
        constraints.gridy = 6;
        this.add(databaseNameLabel, constraints);

        Label mysqlPath = new Label(); ;
        mysqlPath.setText("Enter path to your mysql bin directory:");
        mysqlPath.setForeground(new Color(0xaaaaaa));
        mysqlPath.setFont(new Font("Inter", Font.PLAIN, 16));
        constraints.gridy = 8;
        this.add(mysqlPath, constraints);
    }

    private void drawButtons() {
        JButton confirmButton = new JButton();
        confirmButton.setFont(new Font("Inter", Font.PLAIN, 12));
        confirmButton.setText("Confirm");
        confirmButton.setBackground(new Color(0x606060));
        confirmButton.setForeground(new Color(0xdddddd));
        confirmButton.addActionListener(e -> confirmDB());
        confirmButton.setFocusable(false);
        constraints.gridy = 10;
        constraints.gridx = 1;
        this.add(confirmButton, constraints);

        JButton openDBPath = new JButton();
        openDBPath.setFont(new Font("Inter", Font.PLAIN, 12));
        openDBPath.setText("Open");
        openDBPath.setBackground(new Color(0x606060));
        openDBPath.setForeground(new Color(0xdddddd));
        openDBPath.addActionListener(e -> openPathChooser());
        openDBPath.setFocusable(false);
        constraints.gridy = 9;
        constraints.gridx = 1;
        this.add(openDBPath, constraints);
    }

    private void drawEntries() {
        serverPortEntry = new JTextField();
        setupEntry(serverPortEntry, 1);

        serverUserEntry = new JTextField();
        setupEntry(serverUserEntry, 3);

        serverPasswordEntry = new JPasswordField();
        setupEntry(serverPasswordEntry, 5);

        databaseNameEntry = new JTextField();
        setupEntry(databaseNameEntry, 7);

        databasePathEntry = new JTextField();
        setupEntry(databasePathEntry, 9);

    }

    private void setupEntry(JTextField text, int gridy) {
        text.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 14));
        text.setForeground(new Color(0xffffff));
        text.setBackground(new Color(0x707070));
        text.setCaretColor(new Color(0xffffff));
        text.setBorder(BorderFactory.createEmptyBorder());
        constraints.gridx = 0;
        constraints.gridy = gridy;
        this.add(text, constraints);
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
                serverPasswordEntry.getText(),
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
            return;
        }
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
