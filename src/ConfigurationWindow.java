import javax.swing.*;
import java.awt.*;

public class ConfigurationWindow extends JFrame {

    private JButton confirmButton;

    ConfigurationWindow() {
        this.setTitle("Database configuration");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(true);
        this.setSize(582, 360);
        this.setLayout(null);

        this.getContentPane().setBackground(new Color(0x0));
        drawLabels();
        drawButton();

        this.setVisible(true);
    }

    private void drawLabels() {
        Label serverPortLabel = new Label();
        serverPortLabel.setText("Enter port for your mysql server:");
        serverPortLabel.setForeground(new Color(0xaaaaaa));
        serverPortLabel.setFont(new Font("Inter", Font.PLAIN, 16));
        serverPortLabel.setBounds(10, 10, 350, 25);
        this.add(serverPortLabel);

        Label serverUserLabel = new Label();
        serverUserLabel.setText("Enter username for your mysql server:");
        serverUserLabel.setForeground(new Color(0xaaaaaa));
        serverUserLabel.setFont(new Font("Inter", Font.PLAIN,16));
        serverUserLabel.setBounds(10, 60, 350, 25);
        this.add(serverUserLabel);

        Label serverPasswordLabel = new Label();
        serverPasswordLabel.setText("Enter password for your mysql server:");
        serverPasswordLabel.setForeground(new Color(0xaaaaaa));
        serverPasswordLabel.setFont(new Font("Inter", Font.PLAIN, 16));
        serverPasswordLabel.setBounds(10, 110, 350, 25);
        this.add(serverPasswordLabel);

        Label databaseNameLabel = new Label();
        databaseNameLabel.setText("Enter your database name:");
        databaseNameLabel.setForeground(new Color(0xaaaaaa));
        databaseNameLabel.setFont(new Font("Inter", Font.PLAIN, 16));
        databaseNameLabel.setBounds(10, 160, 350, 25);
        this.add(databaseNameLabel);

        Label mysqlPath = new Label();
        mysqlPath.setText("Enter your database name:");
        mysqlPath.setForeground(new Color(0xaaaaaa));
        mysqlPath.setFont(new Font("Inter", Font.PLAIN, 16));
        mysqlPath.setBounds(10, 210, 350, 25);
        this.add(mysqlPath);
    }

    private void drawButton () {

        confirmButton = new JButton();
        confirmButton.setFont(new Font("Inter", Font.PLAIN, 12));
        confirmButton.setText("Confirm");
        confirmButton.setBackground(new Color(0x606060));
        confirmButton.setForeground(new Color(0xdddddd));
        confirmButton.setBounds(450, 260, 60, 40);
        confirmButton.setBorder(BorderFactory.createEmptyBorder());
        confirmButton.addActionListener(e -> confirmDB());
        confirmButton.setFocusable(false);
        this.add(confirmButton);

    }

    private void confirmDB () {
        System.out.println("Confirmed");
    }
}
