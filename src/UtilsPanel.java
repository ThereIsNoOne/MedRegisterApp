import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class UtilsPanel extends JPanel {

    private final GridBagConstraints constraints = new GridBagConstraints();

    private JTable table;

    UtilsPanel(JTable table) {
        this.table = table;

        this.setBackground(new Color(0x404040));
        this.setLayout(new GridBagLayout());

        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.insets = new Insets(5, 5, 5, 5);
        constraints.weightx = 1;
        constraints.weighty = 1;

        setupComboBox();

    }

    private void setupComboBox() {
        String[] types = new String[0];
        try {
            types = new DataManager().getAllTypes();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(
                    this,
                    e.getMessage(),
                    "Fatal error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
        if (types.length == 0) {
            JOptionPane.showMessageDialog(
                    this,
                    "There are no types",
                    "Fatal error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
        JComboBox<String> typesComboBox = new JComboBox<>(types);
        typesComboBox.addActionListener(e -> selectNewType((String) typesComboBox.getSelectedItem()));
        SetUpUtils.SetUpComboBox(typesComboBox, 0, 0, constraints);

        this.add(typesComboBox, constraints);
    }

    private void selectNewType(String selectedItem) {
        table.setModel(new DataTableModel(selectedItem));
        System.out.println(selectedItem);

    }
}
