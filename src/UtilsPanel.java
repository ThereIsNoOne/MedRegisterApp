import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.util.Arrays;

// TODO: Add button to add type, support removing type (when deleting all data at certain type)

public class UtilsPanel extends JPanel {

    private final GridBagConstraints constraints = new GridBagConstraints();

    private JTable table;
    private DataManager dataManager;
    private String activeType;
    private DataTableModel model;

    UtilsPanel(JTable table, DataTableModel model) {
        this.model = model;
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
        setupButtons();

    }

    private void setupButtons() {
        JButton insert = new JButton("Insert");
        insert.addActionListener(e -> insertRow());
        SetUpUtils.setUpButton(this, insert, 0, 1, constraints);

        JButton delete = new JButton("Delete");
        delete.addActionListener(e -> deleteRow());
        SetUpUtils.setUpButton(this, delete, 1, 1, constraints);
    }

    private void insertRow() {
        String[] result = showInsertDialog();
        if (result == null) {
            return;
        }
        System.out.println(Arrays.toString(result));
        LocalDateTime date;
        try {
            date = LocalDateTime.of(
                    Integer.parseInt(result[1]),
                    Integer.parseInt(result[2]),
                    Integer.parseInt(result[3]),
                    Integer.parseInt(result[4]),
                    Integer.parseInt(result[5])
            );
        } catch (NumberFormatException | DateTimeException e) {
            System.out.println(e);
            JOptionPane.showMessageDialog(
                    this,
                    "Your provided wrong data, try again.",
                    "Error",
                    JOptionPane.PLAIN_MESSAGE
            );
            return;
        }

        float value;
        try {
            value = Float.parseFloat(result[0]);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(
                    this,
                    "Your provided wrong data, try again.",
                    "Error",
                    JOptionPane.PLAIN_MESSAGE
            );
            return;
        }

        dataManager.InsertNewRow(activeType, value, date);
        updateTable(dataManager.getRecord(activeType, value, date));

    }

    private void updateTable(DataRecord dataRecord) {
        model.addRow(dataRecord);
        model.fireTableRowsInserted(model.getRowCount() - 1, model.getRowCount() - 1);
    }

    private void deleteRow() {
        // TODO: Add exceptions!
        System.out.println(table.getSelectedRow());
        System.out.println(Arrays.toString(table.getSelectedRows()));
        if (table.getSelectedRow() != -1) {
            int[] rows = table.getSelectedRows();
            for (int row: rows) {
                dataManager.deleteRow(activeType,
                        (float) model.getValueAt(row, 2),
                        (LocalDateTime) model.getValueAt(row, 3));
            }
            model.deleteRow(rows);
            model.fireTableRowsDeleted(rows[0], rows[rows.length -  1]);
        }
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

        return null;
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
        activeType = selectedItem;
        model = new DataTableModel(selectedItem);
        table.setModel(model);
        System.out.println(selectedItem);

    }
}
