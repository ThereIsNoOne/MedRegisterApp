import javax.swing.*;
import java.awt.*;

public class MainWindow extends JFrame {

    private final GridBagConstraints constraints = new GridBagConstraints();

    MainWindow() {
        this.setTitle("Medical parameters register");
        this.setSize(1080, 720);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(true);
        this.setVisible(true);
        this.setLayout(new GridBagLayout());
        this.getContentPane().setBackground(new Color(0x0));

        constraints.fill = GridBagConstraints.BOTH;
        constraints.insets = new Insets(5, 5, 5, 5);
        constraints.weightx = 1;
        constraints.weighty = 1;

        setPanels();

        this.setVisible(true);
    }

    private void setPanels() {
        TablePanel tablePanel = new TablePanel();
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.gridheight = 2;
        this.add(tablePanel, constraints);
        constraints.gridheight = 1;

        PlotPanel plotPanel = new PlotPanel();
        constraints.gridy = 0;
        constraints.gridx = 1;
        this.add(plotPanel, constraints);

        UtilsPanel utilsPanel = new UtilsPanel();
        utilsPanel.setBackground(new Color(0x0000ff)); // temp
        constraints.gridy = 1;
        constraints.gridx = 1;
        this.add(utilsPanel, constraints);
    }
}
