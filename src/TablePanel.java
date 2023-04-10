import javax.swing.*;
import java.awt.*;

public class TablePanel extends JPanel {

    private final GridBagConstraints constraints = new GridBagConstraints();
    JTable table;

    TablePanel(String type) {
        this.setBackground(new Color(0x404040));
        this.setLayout(new GridBagLayout());

        constraints.fill = GridBagConstraints.BOTH;
        constraints.insets = new Insets(5, 5, 5, 5);
        constraints.weightx = 1;
        constraints.weighty = 1;

        setUpTable(type);
    }

    private void setUpTable(String type) {
        this.table = new JTable(new DataTableModel(type));
        constraints.gridx = 0;
        constraints.gridy = 0;
        this.add(new JScrollPane(table), constraints);


    }
}
