import javax.swing.*;
import java.awt.*;

public class TablePanel extends JPanel {

    private final GridBagConstraints constraints = new GridBagConstraints();

    TablePanel() {
        this.setBackground(new Color(0xff00ff));
        this.setLayout(new GridBagLayout());

        constraints.fill = GridBagConstraints.BOTH;
        constraints.insets = new Insets(5, 5, 5, 5);
        constraints.weightx = 1;
        constraints.weighty = 1;

        setUpTable();
    }

    private void setUpTable() {
        JTable table = new JTable(new DataTableModel());
        constraints.gridx = 0;
        constraints.gridy = 0;
        this.add(new JScrollPane(table), constraints);


    }
}
