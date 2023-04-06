import javax.swing.table.AbstractTableModel;

public class DataTableModel extends AbstractTableModel {
    private String[] columnNames = {"login", "type", "value", "register_time"};
    private Object[][] data = {{"admin", "temperature", 36.6f, "Date"}};

    @Override
    public String getColumnName(int columnIndex) {
        return columnNames[columnIndex];
    }

    @Override
    public int getRowCount() {
        return data.length;
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        return data[rowIndex][columnIndex];
    }
}
