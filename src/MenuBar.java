import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class MenuBar extends JMenuBar {

    private final MainWindow frame;
    private DatabaseConnector dbConnector;

    MenuBar(MainWindow frame) {
        this.frame = frame;
        try {
            this.dbConnector = new DatabaseConnector();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(
                    null,
                    "Could not connect to database.",
                    "Fatal error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
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
        JMenuItem config = new JMenuItem("Reconfigure");
        config.addActionListener(e -> configWindow());
        SetUpUtils.setUpMenuItem(config);


        fileMenu.add(logOut);
        fileMenu.add(exportDB);
        fileMenu.add(importDB);
        fileMenu.add(config);

        return fileMenu;
    }

    private void configWindow() {
        new ConfigurationWindow();
        frame.dispose();
    }

    private void importDB() {
        dbConnector.importRegDB();
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        new MainWindow();
        frame.dispose();
    }

    private void exportDB() {
        dbConnector.exportRegDB();
    }

    private void logOut() {
        PropertiesManager propertiesManager = null;
        try {
            propertiesManager = new PropertiesManager("res/config.properties");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(
                    null,
                    "Could not find config file!",
                    "Fatal error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
        assert propertiesManager != null;
        propertiesManager.setLoggedIn(false);
        new LoginWindow();
        frame.dispose();

    }

    private JMenu setupEditMenu() {
        JMenu editMenu = new JMenu("Edit");
        SetUpUtils.setUpMenu(editMenu);

        JMenuItem drawPlot = new JMenuItem("Draw plot");
        drawPlot.addActionListener(e -> drawPlot());
        SetUpUtils.setUpMenuItem(drawPlot);


        editMenu.add(drawPlot);

        return editMenu;
    }

    private void drawPlot() {
        getParent().repaint();
    }

    private void addType() {

    }

    private void addRow() {

    }


}
