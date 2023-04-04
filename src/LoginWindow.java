import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

public class LoginWindow extends JFrame {

    private final GridBagConstraints constraints = new GridBagConstraints();

    private JTextField loginEntry;
    private JPasswordField passwordEntry;

    LoginWindow( ) {
        this.setTitle("Login");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setSize(700, 420);
        this.setLayout(new GridBagLayout());
        this.getContentPane().setBackground(new Color(0x0));

        constraints.fill = GridBagConstraints.NONE;
        constraints.insets = new Insets(15, 25, 15, 25);
        constraints.weightx = 1;
        drawButtons();
        drawEntries();
        drawLabels();

        this.setVisible(true);
    }

    private void drawLabels() {
        constraints.gridwidth = 2;
        constraints.weightx = 2;

        Label loginLabel = new Label();
        loginLabel.setText("Enter login");
        loginLabel.setFont(new Font("Inter", Font.PLAIN, 16));
        loginLabel.setForeground(new Color(0xffffff));
        constraints.gridy = 0;
        constraints.gridx = 0;
        this.add(loginLabel, constraints);

        Label passwordLabel = new Label();
        passwordLabel.setText("Enter password");
        passwordLabel.setFont(new Font("Inter", Font.PLAIN, 16));
        passwordLabel.setForeground(new Color(0xffffff));
        constraints.gridy = 2;
        constraints.gridx = 0;
        this.add(passwordLabel, constraints);

        constraints.gridwidth = 1;
        constraints.weightx = 1;
    }

    private void drawEntries() {

        constraints.fill = GridBagConstraints.HORIZONTAL;

        loginEntry = new JTextField();
        setUpEntry(loginEntry, 1);

        passwordEntry = new JPasswordField();
        setUpEntry(passwordEntry, 3);

        constraints.weightx = 1;
        constraints.gridwidth = 1;

        constraints.fill = GridBagConstraints.NONE;
    }

    private void setUpEntry(JTextField text, int gridy) {
        text.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 14));
        text.setForeground(new Color(0xffffff));
        text.setBackground(new Color(0x707070));
        text.setCaretColor(new Color(0xffffff));
        text.setBorder(BorderFactory.createEmptyBorder());
        constraints.gridwidth = 2;
        constraints.weightx = 2;
        constraints.gridx = 0;
        constraints.gridy = gridy;
        this.add(text, constraints);
    }

    private void drawButtons() {

        JButton confirmButton = new JButton();
        confirmButton.setFont(new Font("Inter", Font.PLAIN, 12));
        confirmButton.setText("Confirm");
        confirmButton.addActionListener(e -> authorizeUser());
        setUpButtons(confirmButton, 0);

        JButton registerButton = new JButton("Register");
        registerButton.setFont(new Font("Inter", Font.PLAIN, 12));
        registerButton.addActionListener(e -> openRegisterWindow());
        setUpButtons(registerButton, 1);

    }

    private void setUpButtons(JButton button, int gridx) {
        button.setBackground(new Color(0x606060));
        button.setForeground(new Color(0xdddddd));
        button.setFocusable(false);
        constraints.gridy = 4;
        constraints.gridx = gridx;
        this.add(button, constraints);
    }

    private void openRegisterWindow() {
        new RegisterWindow();
        this.dispose();
    }

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
            authorized = manager.authorizeUser(loginEntry.getText(),  readPassword());
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
            propertiesManager.setLoggedIn(true);
            System.out.println("Ok");
            // Here goes main window
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

    private String readPassword() {
        StringBuilder password = new StringBuilder();
        for (char c: passwordEntry.getPassword()) {
            password.append(c);
        }
        return password.toString();

    }
}
