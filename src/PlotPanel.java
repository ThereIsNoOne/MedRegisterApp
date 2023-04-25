import javax.swing.*;
import java.awt.*;
import java.awt.geom.Line2D;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;

public class PlotPanel extends JPanel {

    private final int margin = 20;
    private Graphics2D graph;
    private int plotWidth;
    private int plotHeight;
    private DataTableModel model;
    private ArrayList<DataRecord> points;
    float[] vertical = new float[2]; // min max vertical values
    LocalDateTime[] horizontal = new LocalDateTime[2]; // min max horizontal values

    PlotPanel(DataTableModel model, MainWindow parent) {
        this.model = model;
        this.setBackground(new Color(0xf0f0f0));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        graph = (Graphics2D) g;

        graph.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        plotWidth = getWidth();
        plotHeight = getHeight();
        points = model.getData();

        drawAxes();
        drawPoints();
    }

    private void drawPoints() {
        vertical = getMinMaxValue();
        horizontal = getMinMaxDate();
        System.out.println(Arrays.toString(vertical));
        System.out.println(Arrays.toString(horizontal));
        System.out.println(points);

    }

    private float[] getMinMaxValue() {
        float[] result = {Float.MAX_VALUE, Float.MIN_VALUE};
        for (DataRecord record : points) {
            if (record.getValue() > result[1]) {
                result[1] = record.getValue();
            }
            if (record.getValue() < result[0]) {
                result[0] = record.getValue();
            }
        }
        return result;
    }

    private LocalDateTime[] getMinMaxDate() {
        LocalDateTime[] result = {LocalDateTime.MAX, LocalDateTime.MIN};
        for (DataRecord record : points) {
            if (record.getDate().isAfter(result[1])) {
                result[1] = record.getDate();
            }
            if (record.getDate().isBefore(result[0])) {
                result[0] = record.getDate();
            }
        }
        return result;
    }

    private void drawAxes() {
        // TODO: Add ticks
        graph.draw(new Line2D.Double(margin, margin, margin, plotHeight-margin));
        graph.draw(new Line2D.Double(margin, plotHeight - margin, plotWidth - margin, plotHeight-margin));
    }

    public void setModel(DataTableModel model) {
        this.model = model;
    }
}
