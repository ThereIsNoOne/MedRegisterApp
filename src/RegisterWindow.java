import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

/**
 * Provides implementation of Register window class, responsible for registering a new user.
 */
public class RegisterWindow extends JFrame {

    private final GridBagConstraints constraints = new GridBagConstraints();

    private JTextField loginEntry;
    private JPasswordField passwordEntry;
    private JPasswordField repeatPassword;

    /**
     * Constructor of register window.
     */
    RegisterWindow() {
        this.setTitle("Register");
        this.setSize(700, 420);
        this.setResizable(false);
        this.setLayout(new GridBagLayout());
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.getContentPane().setBackground(new Color(0x0));

        constraints.fill = GridBagConstraints.NONE;
        constraints.insets = new Insets(15, 75, 15, 75);
        constraints.weightx = 1;
        drawButtons();
        drawEntries();
        drawLabels();

        this.setVisible(true);
    }

    /**
     * Draw buttons.
     */
    private void drawButtons() {
        JButton confirmButton = new JButton();
        confirmButton.setText("Create account");
        confirmButton.addActionListener(e -> confirmRegistration());
        SetUpUtils.setUpButton(this, confirmButton, 0, 6, constraints);

        JButton registerButton = new JButton("Already have an account?");
        registerButton.addActionListener(e -> openLoginButton());
        SetUpUtils.setUpButton(this, registerButton, 1, 6, constraints);

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

        JLabel repeatPassword = new JLabel();
        repeatPassword.setText("Repeat password:");
        SetUpUtils.setUpLabel(this, repeatPassword, 0, 4, constraints);


        constraints.gridwidth = 1;
        constraints.weightx = 1;
    }

    /**
     * Draw entries.
     */
    private void drawEntries() {
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.gridwidth = 2;
        constraints.weightx = 2;

        loginEntry = new JTextField();
        SetUpUtils.setUpEntry(this, loginEntry, 0, 1, constraints);

        passwordEntry = new JPasswordField();
        SetUpUtils.setUpEntry(this, passwordEntry, 0, 3, constraints);

        repeatPassword = new JPasswordField();
        SetUpUtils.setUpEntry(this, repeatPassword, 0, 5, constraints);

        constraints.weightx = 1;
        constraints.gridwidth = 1;
        constraints.fill = GridBagConstraints.NONE;

    }

    /**
     * Confirm registration of new user.
     */
    private void confirmRegistration() {
        AuthorizationManager manager;
        try {
            manager = new AuthorizationManager();
        } catch (NoSuchAlgorithmException | IOException e) {
            JOptionPane.showMessageDialog(
                    this,
                    "Problem when connecting to database.",
                    "Fatal error!",
                    JOptionPane.ERROR_MESSAGE
            );
            return;
        }
        String password = SetUpUtils.readPassword(passwordEntry);
        String repeatedPassword = SetUpUtils.readPassword(repeatPassword);

        if (password.length() == 0) {
            JOptionPane.showMessageDialog(
                    this,
                    "Password field cannot be empty!",
                    "Register error!",
                    JOptionPane.ERROR_MESSAGE
            );
            return;
        }

        if (password.equals(repeatedPassword)) {
            try {
                manager.registerUser(loginEntry.getText(), password);
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(
                        this,
                        "Problem when connecting to database.",
                        "Fatal error!",
                        JOptionPane.ERROR_MESSAGE
                );
                return;
            } catch (IllegalArgumentException e) {
                JOptionPane.showMessageDialog(
                        this,
                        "Login already used, try again with different one.",
                        "Register error",
                        JOptionPane.ERROR_MESSAGE
                );
                return;
            }
            JOptionPane.showMessageDialog(
                    this,
                    "Successfully registered.",
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE
            );
            try {
                new DatabaseConnector().exportAuthDB();
            } catch (IOException e) {
                JOptionPane.showMessageDialog(
                        null,
                        "Cannot connect to database.",
                        "Fatal error",
                        JOptionPane.ERROR_MESSAGE
                );
            }
            new LoginWindow();
            this.dispose();
            return;
        }
        JOptionPane.showMessageDialog(
                this,
                "Password and repeat password fields are different, check if they are the same, then try again.",
                "Register error",
                JOptionPane.ERROR_MESSAGE
        );
    }

    /**
     * Open login window.
     */
    private void openLoginButton() {
        new LoginWindow();
        this.dispose();
    }


}
