import javax.swing.*;
import java.awt.*;
import java.io.IOException;

/**
 * Provides implementation of MainWindow class, containing utils panel, plot panel and table panel.
 */
public class MainWindow extends JFrame {

    private final GridBagConstraints constraints = new GridBagConstraints();
    private DataTableModel model;
    private TablePanel tablePanel;
    private PlotPanel plotPanel;
    private UtilsPanel utilsPanel;

    /**
     * Create new MainWindow instance.
     */
    MainWindow() {
        this.setTitle("Medical parameters register");
        this.setSize(1080, 720);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(true);
        this.setVisible(true);
        this.setJMenuBar(new MenuBar(this));
        this.setLayout(new GridBagLayout());
        this.getContentPane().setBackground(new Color(0x0));

        constraints.fill = GridBagConstraints.BOTH;
        constraints.insets = new Insets(5, 5, 5, 5);
        constraints.weightx = 1;
        constraints.weighty = 1;

        try {
            setPanels(new DataManager().getAllTypes().get(0));
        } catch (IOException e) {
            JOptionPane.showMessageDialog(
                    this,
                    e.getMessage(),
                    "Fatal error",
                    JOptionPane.ERROR_MESSAGE
            );
        }

        this.setVisible(true);
    }

    /**
     * Set model of the table.
     * @param model table model
     */
    public void setModel(DataTableModel model) {
        this.model = model;
        tablePanel.table.setModel(model);
        plotPanel.setModel(model);

    }

    /**
     * Set all panels.
     * @param type type of medical parameter to be displayed
     */
    private void setPanels(String type) {
        tablePanel = new TablePanel(type, this);
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.gridheight = 2;
        this.add(tablePanel, constraints);
        constraints.gridheight = 1;

        plotPanel = new PlotPanel(tablePanel.tableModel);
        constraints.gridy = 0;
        constraints.gridx = 1;
        this.add(plotPanel, constraints);

        utilsPanel = new UtilsPanel(tablePanel.table, tablePanel.tableModel, this);
        constraints.gridy = 1;
        constraints.gridx = 1;
        this.add(utilsPanel, constraints);
    }


}
