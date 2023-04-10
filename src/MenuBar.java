import javax.swing.*;
import java.awt.*;

public class MenuBar extends JMenuBar {

    MenuBar() {
        this.setBackground(new Color(0x050505));
        this.setBorder(BorderFactory.createEmptyBorder());

        this.add(setupFileMenu());
        this.add(setupEditMenu());
    }

    private JMenu setupFileMenu() {
        JMenu fileMenu = new JMenu("File");
        SetUpUtils.setUpMenu(fileMenu);

        JMenuItem logOut = new JMenuItem("Log Out");
        logOut.addActionListener(e -> logOut());
        SetUpUtils.setUpMenuItem(logOut);
        JMenuItem exportDB = new JMenuItem("Export");
        exportDB.addActionListener(e -> exportDB());
        SetUpUtils.setUpMenuItem(exportDB);
        JMenuItem importDB = new JMenuItem("Import");
        importDB.addActionListener(e -> importDB());
        SetUpUtils.setUpMenuItem(importDB);
//        JMenuItem config = new JMenuItem("Config");

        fileMenu.add(logOut);
        fileMenu.add(exportDB);
        fileMenu.add(importDB);
//        fileMenu.add(config);

        return fileMenu;
    }

    private void importDB() {

    }

    private void exportDB() {

    }

    private void logOut() {

    }

    private JMenu setupEditMenu() {
        JMenu editMenu = new JMenu("Edit");
        SetUpUtils.setUpMenu(editMenu);

        JMenuItem addRow = new JMenuItem("Add row");
        addRow.addActionListener(e -> addRow());
        SetUpUtils.setUpMenuItem(addRow);
        JMenuItem addType = new JMenuItem("Add type");
        addType.addActionListener(e -> addType());
        SetUpUtils.setUpMenuItem(addType);
        JMenuItem drawPlot = new JMenuItem("Draw plot");
        drawPlot.addActionListener(e -> drawPlot());
        SetUpUtils.setUpMenuItem(drawPlot);

        editMenu.add(addRow);
        editMenu.add(addType);
        editMenu.add(drawPlot);

        return editMenu;
    }

    private void drawPlot() {

    }

    private void addType() {

    }

    private void addRow() {

    }


}
