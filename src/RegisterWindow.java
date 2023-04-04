import javax.swing.*;
import java.awt.*;

public class RegisterWindow extends JFrame {

    private final GridBagConstraints constraints = new GridBagConstraints();

    private JTextField loginEntry;
    private JPasswordField passwordEntry;
    private JPasswordField repeatPassword;

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

    private void drawButtons() {
        JButton confirmButton = new JButton();
        confirmButton.setText("Create account");
        confirmButton.addActionListener(e -> confirmRegistration());
        SetUpUtils.setUpButton(this, confirmButton, 0, 6, constraints);

        JButton registerButton = new JButton("Already have an account?");
        registerButton.addActionListener(e -> openLoginButton());
        SetUpUtils.setUpButton(this, registerButton, 1, 6, constraints);

    }

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

    private void confirmRegistration() {
        System.out.printf(
                "Login: %s\nPassword: %s, repeat: %s",
                loginEntry.getText(),
                passwordEntry.getText(),
                repeatPassword.getText()
        );
    }

    private void openLoginButton() {
        new LoginWindow();
        this.dispose();
    }


}
