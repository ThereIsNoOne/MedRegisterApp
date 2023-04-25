import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class UtilsPanel extends JPanel {

    private final GridBagConstraints constraints = new GridBagConstraints();

    private JTable table;
    private ArrayList<String> types;
    private DataManager dataManager;
    private String activeType;
    private DataTableModel model;
    private JComboBox<String> typesComboBox;
    MainWindow parent;

    UtilsPanel(JTable table, DataTableModel model, MainWindow parent) {
        this.model = model;
        this.table = table;
        this.parent = parent;
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
        activeType = dataManager.getAllTypes().get(0);

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

        JButton addNewType = new JButton("Add type");
        addNewType.addActionListener(e -> addType());
        SetUpUtils.setUpButton(this, addNewType, 2, 1, constraints);
    }

    private void addType() {
        String answer = JOptionPane.showInputDialog(null,
                "Add new type of medical parameter:",
                "Insert type",
                JOptionPane.PLAIN_MESSAGE);
        if (answer == null || answer.isEmpty()) {
            return;
        }
        if (types.contains(answer)) {
            JOptionPane.showMessageDialog(null,
                    "This type of parameter already exists.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }
        activeType = answer;
        types.add(answer);
        typesComboBox.addItem(answer);
    }

    private void insertRow() {
        String[] result = showInsertDialog();
        if (result == null) {
            return;
        }
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
        if (table.getSelectedRow() != -1) {
            int[] rows = table.getSelectedRows();
            for (int i=rows.length-1; i>=0; i--) {
                int row = rows[i];
                dataManager.deleteRow(activeType,
                        (float) model.getValueAt(row, 2),
                        (LocalDateTime) model.getValueAt(row, 3));
                model.deleteRow(row);
                model.fireTableRowsDeleted(row, row);
            }
            JOptionPane.showMessageDialog(
                    this,
                    "Data deleted successfully",
                    "Operation completed successfully",
                    JOptionPane.INFORMATION_MESSAGE
            );
            checkIfEmpty();
        }
    }

    private void checkIfEmpty() {
        if (model.getRowCount() == 0) {
            typesComboBox.removeItem(activeType);
            types = dataManager.getAllTypes();
            typesComboBox.setSelectedItem(types.get(0));
            selectNewType(types.get(0));
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
        types = dataManager.getAllTypes();
        typesComboBox = new JComboBox<>(types.toArray(new String[0]));
        typesComboBox.addActionListener(e -> selectNewType((String) typesComboBox.getSelectedItem()));
        SetUpUtils.setUpComboBox(typesComboBox, 0, 0, constraints);

        this.add(typesComboBox, constraints);
    }

    private void selectNewType(String selectedItem) {
        activeType = selectedItem;
        model = new DataTableModel(selectedItem);
        parent.setModel(model);
    }

    public void setModel(DataTableModel model) {
        this.model = model;
    }
}
