import javax.swing.*;
import java.awt.*;

/**
 * Provides sets of useful methods to set up components.
 */
public class SetUpUtils {

    /**
     * Set up entry.
     * @param frame parent frame
     * @param text text field to be added
     * @param x x coordinate
     * @param y coordinate
     * @param constraints constraints to be applied
     */
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

    /**
     * Set up entry.
     * @param panel parent frame
     * @param text text field to be added
     * @param x x coordinate
     * @param y coordinate
     * @param constraints constraints to be applied
     */
    static void setUpEntry(JPanel panel, JTextField text, int x, int y, GridBagConstraints constraints) {
        text.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 14));
        text.setForeground(new Color(0xffffff));
        text.setBackground(new Color(0x202020));
        text.setCaretColor(new Color(0xffffff));
        text.setBorder(BorderFactory.createEmptyBorder());
        constraints.gridx = x;
        constraints.gridy = y;
        panel.add(text, constraints);
    }

    /**
     * Set up button.
     * @param frame parent frame
     * @param button button to be added
     * @param x x coordinate
     * @param y coordinate
     * @param constraints constraints to be applied
     */
    static void setUpButton(JFrame frame, JButton button, int x, int y, GridBagConstraints constraints) {
        button.setFont(new Font("Inter", Font.PLAIN, 12));
        button.setBackground(new Color(0x606060));
        button.setForeground(new Color(0xdddddd));
        button.setFocusable(false);
        constraints.gridy = y;
        constraints.gridx = x;
        frame.add(button, constraints);
    }

    /**
     * Set up button.
     * @param panel parent frame
     * @param button button to be added
     * @param x x coordinate
     * @param y coordinate
     * @param constraints constraints to be applied
     */
    static void setUpButton(JPanel panel, JButton button, int x, int y, GridBagConstraints constraints) {
        button.setFont(new Font("Inter", Font.PLAIN, 12));
        button.setBackground(new Color(0x606060));
        button.setForeground(new Color(0xdddddd));
        button.setFocusable(false);
        constraints.gridy = y;
        constraints.gridx = x;
        panel.add(button, constraints);
    }

    /**
     * Sets the label
     * @param frame parent component
     * @param label text to be displayed
     * @param x x coordinate
     * @param y y coordinate
     * @param constraints constraints to be applied
     */
    static void setUpLabel(JFrame frame, JLabel label, int x, int y, GridBagConstraints constraints) {
        label.setFont(new Font("Inter", Font.PLAIN, 16));
        label.setForeground(new Color(0xbbbbbb));
        constraints.gridy = y;
        constraints.gridx = x;
        frame.add(label, constraints);
    }

        /**
     * Sets the label
     * @param panel parent component
     * @param label text to be displayed
     * @param x x coordinate
     * @param y y coordinate
     * @param constraints constraints to be applied
     */
    static void setUpLabel(JPanel panel, JLabel label, int x, int y, GridBagConstraints constraints) {
        label.setFont(new Font("Inter", Font.PLAIN, 16));
        label.setForeground(new Color(0xbbbbbb));
        constraints.gridy = y;
        constraints.gridx = x;
        panel.add(label, constraints);
    }

    /**
     * Read password from password entry field.
     * @param passwordEntry password entry to be read
     * @return password
     */
    static String readPassword(JPasswordField passwordEntry) {
        StringBuilder password = new StringBuilder();
        for (char c: passwordEntry.getPassword()) {
            password.append(c);
        }
        return password.toString();

    }

    /**
     * Set up checkbox component.
     * @param frame parent frame
     * @param checkBox checkbox component to be added
     * @param x x coordinate
     * @param y y coordinate
     * @param constraints constraints to be applied
     */
    static void setUpCheckBox(JFrame frame, JCheckBox checkBox, int x, int y, GridBagConstraints constraints) {
        checkBox.setFont(new Font("Inter", Font.PLAIN, 12));
        checkBox.setBackground(new Color(0x606060));
        checkBox.setForeground(new Color(0xdddddd));
        checkBox.setFocusable(false);
        constraints.gridy = y;
        constraints.gridx = x;
        frame.add(checkBox, constraints);
    }

    /**
     * Set up combo box
     * @param comboBox combo box to be added
     * @param x x coordinate
     * @param y y coordinate
     * @param constraints constraints to be applied
     */
    static void setUpComboBox(JComboBox<?> comboBox, int x, int y, GridBagConstraints constraints) {
        comboBox.setBackground(new Color(0xbbbbbb));
        comboBox.setForeground(new Color(0x000000));
        comboBox.setFont(new Font("Inter", Font.BOLD, 14));
        constraints.gridx = x;
        constraints.gridy = y;
    }

    /**
     * Set up menu.
     * @param menu menu to be added
     */
    static void setUpMenu(JMenu menu) {
        menu.setFont(new Font("Inter", Font.BOLD, 12));
        menu.setForeground(new Color(0xffffff));
        menu.setBackground(new Color(0x050505));
    }

    /**
     * Set up menu item
     * @param item item to be added
     */
    static void setUpMenuItem(JMenuItem item) {
        item.setFont(new Font("Inter", Font.BOLD, 12));
        item.setForeground(new Color(0xffffff));
        item.setBackground(new Color(0x050505));
    }
}
