import javax.swing.table.AbstractTableModel;
import java.io.IOException;

public class DataTableModel extends AbstractTableModel {
    private final String[] columnNames = {"login", "type", "value", "register_time"};
    private Object[][] data;

    DataTableModel(String type) {
        setData(type);
    }

    void setData(String type) {
        DataManager manager;
        try {
            manager = new DataManager();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        data = manager.getTableRows(type);
    }



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
