import javax.swing.*;
import java.awt.*;

public class TablePanel extends JPanel {

    private final GridBagConstraints constraints = new GridBagConstraints();
    DataTableModel tableModel;
    JTable table;

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

    private void setUpTable() {
        this.table = new JTable(tableModel);
        constraints.gridx = 0;
        constraints.gridy = 0;
        this.add(new JScrollPane(table), constraints);


    }
}
