import javax.swing.*;
import java.awt.*;
import java.awt.geom.Line2D;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

/**
 * Provides implementation of plot panel, responsible for drawing the plots.
 */
public class PlotPanel extends JPanel {

    private final int margin = 40;
    private final int offset = margin* 2/3;
    private Graphics2D graph;
    private int plotWidth;
    private int plotHeight;
    private DataTableModel model;
    private ArrayList<DataRecord> points;
    float[] vertical = new float[2]; // min max vertical values
    LocalDateTime[] horizontal = new LocalDateTime[2]; // min max horizontal values

    /**
     * Creates a new plot panel.
     * @param model model of table containing data for the plot
     */
    PlotPanel(DataTableModel model) {
        this.model = model;
        this.setBackground(new Color(0xf0f0f0));
    }

    /**
     * Paints components.
     * @param g the <code>Graphics</code> object to protect
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        graph = (Graphics2D) g;

        graph.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        plotWidth = getWidth();
        plotHeight = getHeight();
        points = model.getData();

        vertical = getMinMaxValue();
        horizontal = getMinMaxDate();

        drawAxes();
        drawPoints();
    }

    /**
     * Draw points on the graph.
     */
    private void drawPoints() {
        long horizontalAxisLength = getHorizontalAxisLength();
        long verticalAxisLength = getVerticalAxisLength();
        long horizontalDistance = horizontal[0].until(horizontal[1], ChronoUnit.SECONDS);
        float verticalDistance = vertical[1] - vertical[0];
        if (verticalDistance == 0) {
            verticalDistance = verticalAxisLength;
        }
        if (horizontalDistance == 0) {
            horizontalDistance = horizontalAxisLength;
        }
        int start = getWidth() - margin - offset;
        for (DataRecord record : points) {
            int x = start - (int) (horizontalAxisLength * ((double) record.getDate().until(horizontal[1], ChronoUnit.SECONDS)/horizontalDistance));
            int y = margin + offset + (int) (verticalAxisLength * (double)((vertical[1] - record.getValue())/verticalDistance));
            drawCircle(x, y);
        }
    }

    /**
     * Calculates length of vertical axis.
     * @return length of vertical axis.
     */
    private long getVerticalAxisLength() {
        return getHeight()-margin-offset - (margin + offset);
    }

    /**
     * Draw circle of certain coordinates.
     * @param x x coordinate
     * @param y y coordinate
     */
    private void drawCircle(int x, int y) {
        int radius = 6;
        x = x - (radius/2);
        y = y - (radius/2);
        graph.fillOval(x, y, radius, radius);
    }

    /**
     * Calculates horizontal axis length.
     * @return horizontal axis length
     */
    private long getHorizontalAxisLength() {
        return getWidth() - margin - offset - (margin + offset);
    }

    /**
     * Get min and max values of provided data.
     * @return min and max values
     */
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

    /**
     * Get the earliest and latest date of provided data.
     * @return earliest and latest date
     */
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

    /**
     * Draw axes and labels.
     */
    private void drawAxes() {
        graph.draw(new Line2D.Double(margin, margin, margin, plotHeight-margin)); // y axis
        int tickLength = 5;
        graph.draw(new Line2D.Double(margin- tickLength, plotHeight-margin-offset, margin+ tickLength, plotHeight-margin-offset));
        graph.draw(new Line2D.Double(margin- tickLength, margin+offset, margin+ tickLength, margin+offset));
        graph.drawString(String.valueOf(vertical[0]), tickLength, plotHeight-margin-offset);
        graph.drawString(String.valueOf(vertical[1]), tickLength, margin+offset);

        graph.draw(new Line2D.Double(margin, plotHeight - margin, plotWidth - margin, plotHeight-margin)); // x axis
        graph.draw(new Line2D.Double(margin+offset, plotHeight-margin+ tickLength, margin+offset, plotHeight-margin- tickLength));
        graph.draw(new Line2D.Double(plotWidth-margin-offset, plotHeight-margin+ tickLength, plotWidth-margin-offset, plotHeight-margin- tickLength));
        graph.drawString(formatDate(horizontal[0]), margin+offset, plotHeight-offset);
        graph.drawString(formatDate(horizontal[1]), plotWidth-margin-offset, plotHeight-offset);
    }

    /**
     * Format date to pattern yyyy-MM-dd.
     * @param date date to format
     * @return formatted date
     */
    private String formatDate(LocalDateTime date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return date.format(formatter);
    }

    /**
     * Set new model of data.
     * @param model new table model
     */
    public void setModel(DataTableModel model) {
        this.model = model;
    }
}
