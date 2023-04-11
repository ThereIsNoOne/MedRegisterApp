import javax.swing.*;
import java.awt.*;

public class EntryDialog extends JPanel {

    private final GridBagConstraints constraints = new GridBagConstraints();

    JTextField[] jTextFields = new JTextField[6];

    EntryDialog() {
        this.setBackground(new Color(0x404040));
        this.setLayout(new GridBagLayout());

        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.insets = new Insets(5, 5, 5, 5);
        constraints.weightx = 1;
        constraints.weighty = 1;

        setupLabels();
        setupEntries();
    }

    private void setupLabels() {
        JLabel value = new JLabel("Enter value of your medical parameter:");
        SetUpUtils.setUpLabel(this, value, 0, 0, constraints);

        JLabel date = new JLabel("Enter date (YYYY/MM/DD):");
        SetUpUtils.setUpLabel(this, date, 0, 1, constraints);

        JLabel time = new JLabel("Enter time:");
        SetUpUtils.setUpLabel(this, time, 0, 2, constraints);
    }

    private void setupEntries() {
        jTextFields[0] = new JTextField();
        constraints.gridwidth = 3;
        SetUpUtils.setUpEntry(this, jTextFields[0], 1, 0, constraints);
        constraints.gridwidth = 1;

        jTextFields[1] = new JTextField(4);
        SetUpUtils.setUpEntry(this, jTextFields[1], 1, 1, constraints);
        jTextFields[2] = new JTextField(2);
        SetUpUtils.setUpEntry(this, jTextFields[2], 2, 1, constraints);
        jTextFields[3] = new JTextField(2);
        SetUpUtils.setUpEntry(this, jTextFields[3], 3, 1, constraints);

        jTextFields[4] = new JTextField(2);
        SetUpUtils.setUpEntry(this, jTextFields[4], 1, 2, constraints);
        jTextFields[5] = new JTextField(2);
        SetUpUtils.setUpEntry(this, jTextFields[5], 2, 2, constraints);
    }

    String[] getAll() {
        // TODO: Validation
        String[] all = new String[6];
        for (int i = 0; i < all.length; i++) {
            all[i] = jTextFields[i].getText();
        }
        return all;
    }
}