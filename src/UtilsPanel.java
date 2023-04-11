import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Arrays;

public class UtilsPanel extends JPanel {

    private final GridBagConstraints constraints = new GridBagConstraints();

    private JTable table;
    private DataManager dataManager;
    private String activeType;

    UtilsPanel(JTable table) {
        this.table = table;
        try {
            this.dataManager = new DataManager();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(
                    this,
                    "Unable to initialize window",
                    "Fatal error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
        activeType = dataManager.getAllTypes()[0];

        this.setBackground(new Color(0x404040));
        this.setLayout(new GridBagLayout());

        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.insets = new Insets(5, 5, 5, 5);
        constraints.weightx = 1;
        constraints.weighty = 1;

        setupComboBox();
        setupInsertData();

    }

    private void setupInsertData() {
        JButton insert = new JButton("Insert");
        insert.addActionListener(e -> insertRow());
        SetUpUtils.setUpButton(this, insert, 0, 1, constraints);
    }

    private void insertRow() {
        String[] result = showInsertDialog();
        System.out.println(Arrays.toString(result));
        LocalDateTime date = LocalDateTime.of(
                Integer.parseInt(result[1]),
                Integer.parseInt(result[2]),
                Integer.parseInt(result[3]),
                Integer.parseInt(result[4]),
                Integer.parseInt(result[5])
        );
        dataManager.InsertNewRow(activeType, Float.parseFloat(result[0]), date);
    }

    private String[] showInsertDialog() {
        EntryDialog entryDialog = new EntryDialog();
        int answer = JOptionPane.showConfirmDialog(
                this,
                entryDialog,
                "Enter your data:",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE
        );
        if (answer == JOptionPane.OK_OPTION) {
            try {
                return entryDialog.getAll();
            } catch (RuntimeException e) {
                JOptionPane.showMessageDialog(
                        this,
                        e.getMessage(),
                        "Error",
                        JOptionPane.ERROR_MESSAGE
                );
            }
        }

        return new String[0];
    }

    private void setupComboBox() {
        String[] types;
        types = dataManager.getAllTypes();
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
        SetUpUtils.setUpComboBox(typesComboBox, 0, 0, constraints);

        this.add(typesComboBox, constraints);
    }

    private void selectNewType(String selectedItem) {
        table.setModel(new DataTableModel(selectedItem));
        System.out.println(selectedItem);

    }
}