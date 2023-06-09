import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;

/**
 * Provides implementation of login window, used to log in to application.
 */
public class LoginWindow extends JFrame {

    private final GridBagConstraints constraints = new GridBagConstraints();

    private JTextField loginEntry;
    private JPasswordField passwordEntry;
    private JCheckBox rememberMe;

    /**
     * Creates a new LoginWindow.
     */
    LoginWindow( ) {
        this.setTitle("Login");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setSize(700, 420);
        this.setLayout(new GridBagLayout());
        this.getContentPane().setBackground(new Color(0x0));

        constraints.fill = GridBagConstraints.NONE;
        constraints.insets = new Insets(15, 75, 15, 75);
        constraints.weightx = 1;
        drawButtons();
        drawEntries();
        drawLabels();
        drawCheckbox();

        this.setVisible(true);
    }

    /**
     * Draw checkbox.
     */
    private void drawCheckbox() {
        rememberMe = new JCheckBox("Remember me");
        constraints.gridwidth = 2;
        SetUpUtils.setUpCheckBox(this, rememberMe, 0, 4, constraints);
        constraints.gridwidth = 1;
    }

    /**
     * Draw labels.
     */
    private void drawLabels() {
        constraints.gridwidth = 2;
        constraints.weightx = 2;

        JLabel loginLabel = new JLabel();
        loginLabel.setText("Enter login");
        SetUpUtils.setUpLabel(this, loginLabel, 0, 0, constraints);

        JLabel passwordLabel = new JLabel();
        passwordLabel.setText("Enter password");
        SetUpUtils.setUpLabel(this, passwordLabel, 0, 2, constraints);

        constraints.gridwidth = 1;
        constraints.weightx = 1;
    }

    /**
     * Draw entries
     */
    private void drawEntries() {

        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.gridwidth = 2;
        constraints.weightx = 2;

        loginEntry = new JTextField();
        SetUpUtils.setUpEntry(this, loginEntry, 0, 1, constraints);

        passwordEntry = new JPasswordField();
        SetUpUtils.setUpEntry(this, passwordEntry, 0, 3, constraints);

        constraints.weightx = 1;
        constraints.gridwidth = 1;
        constraints.fill = GridBagConstraints.NONE;
    }

    /**
     * Draw buttons.
     */
    private void drawButtons() {

        JButton confirmButton = new JButton();
        confirmButton.setText("Login");
        confirmButton.addActionListener(e -> authorizeUser());
        SetUpUtils.setUpButton(this, confirmButton, 0, 5, constraints);

        JButton registerButton = new JButton("Don't have an account?");
        registerButton.addActionListener(e -> openRegisterWindow());
        SetUpUtils.setUpButton(this, registerButton, 1, 5, constraints);

    }

    /**
     * Open registration window.
     */
    private void openRegisterWindow() {
        new RegisterWindow();
        this.dispose();
    }

    /**
     * Authorize user.
     */
    private void authorizeUser() {

        AuthorizationManager manager = null;
        try {
            manager = new AuthorizationManager();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(
                    this,
                    e.getMessage(),
                    "Fatal Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }

        assert manager != null;

        boolean authorized;
        try {
            authorized = manager.authorizeUser(loginEntry.getText(),  SetUpUtils.readPassword(passwordEntry));
        } catch (AuthorizationException e) {
            authorized = false;
        }

        PropertiesManager propertiesManager = null;
        try {
            propertiesManager = new PropertiesManager("res/config.properties");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(
                    this,
                    e.getMessage(),
                    "Fatal Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }

        assert propertiesManager != null;
        if (authorized) {
            propertiesManager.setUserId(loginEntry.getText());
            propertiesManager.setLoggedIn(rememberMe.isSelected());
            System.out.println("Ok");
            new MainWindow();
            this.dispose();
            return;
        }

        propertiesManager.setUserId("null");
        propertiesManager.setLoggedIn(false);
        JOptionPane.showMessageDialog(
                this,
                "Cannot authorize, check password and login, then try again.",
                "Authorization Failed",
                JOptionPane.ERROR_MESSAGE
        );
    }

}
