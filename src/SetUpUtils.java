import javax.swing.*;
import java.awt.*;

public class SetUpUtils {

    static void setUpEntry(JFrame frame, JTextField text, int x, int y, GridBagConstraints constraints) {
        text.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 14));
        text.setForeground(new Color(0xffffff));
        text.setBackground(new Color(0x202020));
        text.setCaretColor(new Color(0xffffff));
        text.setBorder(BorderFactory.createEmptyBorder());
        constraints.gridx = x;
        constraints.gridy = y;
        frame.add(text, constraints);
    }

    static void setUpButton(JFrame frame, JButton button, int x, int y, GridBagConstraints constraints) {
        button.setFont(new Font("Inter", Font.PLAIN, 12));
        button.setBackground(new Color(0x606060));
        button.setForeground(new Color(0xdddddd));
        button.setFocusable(false);
        constraints.gridy = y;
        constraints.gridx = x;
        frame.add(button, constraints);
    }

    static void setUpLabel(JFrame frame, JLabel label, int x, int y, GridBagConstraints constraints) {
        label.setFont(new Font("Inter", Font.PLAIN, 16));
        label.setForeground(new Color(0xbbbbbb));
        constraints.gridy = y;
        constraints.gridx = x;
        frame.add(label, constraints);
    }

    static String readPassword(JPasswordField passwordEntry) {
        StringBuilder password = new StringBuilder();
        for (char c: passwordEntry.getPassword()) {
            password.append(c);
        }
        return password.toString();

    }

    static void SetUpComboBox(JComboBox<String> comboBox, int x, int y, GridBagConstraints constraints) {
        comboBox.setBackground(new Color(0xbbbbbb));
        comboBox.setForeground(new Color(0x000000));
        comboBox.setFont(new Font("Inter", Font.BOLD, 14));
        constraints.gridx = x;
        constraints.gridy = y;
    }

    static void setUpMenu(JMenu menu) {
        menu.setFont(new Font("Inter", Font.BOLD, 12));
        menu.setForeground(new Color(0xffffff));
        menu.setBackground(new Color(0x050505));
    }

    static void setUpMenuItem(JMenuItem item) {
        item.setFont(new Font("Inter", Font.BOLD, 12));
        item.setForeground(new Color(0xffffff));
        item.setBackground(new Color(0x050505));
    }
}
