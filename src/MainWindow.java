import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class MainWindow extends JFrame {

    private final GridBagConstraints constraints = new GridBagConstraints();

    MainWindow() {
        this.setTitle("Medical parameters register");
        this.setSize(1080, 720);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(true);
        this.setVisible(true);
        this.setJMenuBar(new MenuBar());
        this.setLayout(new GridBagLayout());
        this.getContentPane().setBackground(new Color(0x0));

        constraints.fill = GridBagConstraints.BOTH;
        constraints.insets = new Insets(5, 5, 5, 5);
        constraints.weightx = 1;
        constraints.weighty = 1;

        try {
            setPanels(new DataManager().getAllTypes()[0]);
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

    private void setPanels(String type) {
        TablePanel tablePanel = paintTable(type);
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.gridheight = 2;
        this.add(tablePanel, constraints);
        constraints.gridheight = 1;

        PlotPanel plotPanel = new PlotPanel();
        constraints.gridy = 0;
        constraints.gridx = 1;
        this.add(plotPanel, constraints);

        UtilsPanel utilsPanel = new UtilsPanel(tablePanel.table);
        constraints.gridy = 1;
        constraints.gridx = 1;
        this.add(utilsPanel, constraints);
    }

    TablePanel paintTable(String type) {
        return new TablePanel(type);
    }
}
