import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 * Class representing utils panel, responsible for handling various user actions.
 */
public class UtilsPanel extends JPanel {

    private final GridBagConstraints constraints = new GridBagConstraints();

    private JTable table;
    private ArrayList<String> types;
    private DataManager dataManager;
    private String activeType;
    private DataTableModel model;
    private JComboBox<String> typesComboBox;
    MainWindow parent;

    /**
     * Constructor of the UtilsPanel.
     * @param table window table
     * @param model data model
     * @param parent parent component
     */
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

    /**
     * Draw the buttons.
     */
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

    /**
     * Add new type of medical parameter to choose.
     */
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

    /**
     * Insert new data to table.
     */
    private void insertRow() {
        float[] result = showInsertDialog();
        if (result == null) {
            return;
        }

        LocalDateTime date = LocalDateTime.of(
                (int) result[1],
                (int) result[2],
                (int) result[3],
                (int) result[4],
                (int) result[5]
        );
        if (date.isAfter(LocalDateTime.now())) {
            JOptionPane.showMessageDialog(
                    this,
                    "Date you have chosen is in the future.",
                    "Error!",
                    JOptionPane.ERROR_MESSAGE
            );
            return;
        }
        float value = result[0];

        dataManager.InsertNewRow(activeType, value, date);
        updateTable(dataManager.getRecord(activeType, value, date));

    }

    /**
     * Update table.
     * @param dataRecord record to be added
     */
    private void updateTable(DataRecord dataRecord) {
        model.addRow(dataRecord);
        model.fireTableRowsInserted(model.getRowCount() - 1, model.getRowCount() - 1);
        parent.repaint();
    }

    /**
     * Delete selected row from table.
     */
    private void deleteRow() {
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
            parent.repaint();
            checkIfEmpty();
        }
    }

    /**
     * Checks if the table is empty.
     */
    private void checkIfEmpty() {
        if (model.getRowCount() == 0) {
            typesComboBox.removeItem(activeType);
            types = dataManager.getAllTypes();
            typesComboBox.setSelectedItem(types.get(0));
            selectNewType(types.get(0));
        }
    }

    /**
     * Show insert dialog.
     * @return values to add to table
     */
    private float[] showInsertDialog() {
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

    /**
     * Set up combo box, responsible for choosing type of medical parameter to be displayed.
     */
    private void setupComboBox() {
        types = dataManager.getAllTypes();
        typesComboBox = new JComboBox<>(types.toArray(new String[0]));
        typesComboBox.addActionListener(e -> selectNewType((String) typesComboBox.getSelectedItem()));
        SetUpUtils.setUpComboBox(typesComboBox, 0, 0, constraints);

        this.add(typesComboBox, constraints);
    }

    /**
     * Select new type of medical parameter to be displayed.
     * @param selectedItem selected type of parameter to be displayed
     */
    private void selectNewType(String selectedItem) {
        activeType = selectedItem;
        model = new DataTableModel(selectedItem);
        parent.setModel(model);
        parent.repaint();
    }

    /**
     * Set new model of parameter.
     * @param model model to be used
     */
    public void setModel(DataTableModel model) {
        this.model = model;
    }
}
