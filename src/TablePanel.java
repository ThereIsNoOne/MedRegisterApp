import javax.swing.*;
import java.awt.*;

/**
 * Class representing table panel, responsible for drawing table.
 */
public class TablePanel extends JPanel {

    private final GridBagConstraints constraints = new GridBagConstraints();
    DataTableModel tableModel;
    JTable table;

    /**
     * Construct the table panel
     * @param type type of medical parameter
     * @param parent parent component
     */
    TablePanel(String type, MainWindow parent) {
        this.setBackground(new Color(0x404040));
        this.setLayout(new GridBagLayout());
        this.tableModel = new DataTableModel(type);

        constraints.fill = GridBagConstraints.BOTH;
        constraints.insets = new Insets(5, 5, 5, 5);
        constraints.weightx = 1;
        constraints.weighty = 1;

        setUpTable();
    }

    /**
     * Draw and setup table.
     */
    private void setUpTable() {
        this.table = new JTable(tableModel);
        constraints.gridx = 0;
        constraints.gridy = 0;
        this.add(new JScrollPane(table), constraints);


    }
}
