import javax.swing.table.AbstractTableModel;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

public class DataTableModel extends AbstractTableModel {
    private final String[] columnNames = {"login", "type", "value", "register_time"};
    private ArrayList<DataRecord> data;

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
        try {
            data = manager.getDataRecords(type);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    void addRow(DataRecord record) {
        data.add(record);
    }

    void deleteRow(int rowIndex) {
        data.remove(rowIndex);
    }

    @Override
    public String getColumnName(int columnIndex) {
        return columnNames[columnIndex];
    }

    @Override
    public int getRowCount() {
        return data.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        return
                switch (columnIndex) {
            case 0 -> data.get(rowIndex).login;
            case 1 -> data.get(rowIndex).type;
            case 2 -> data.get(rowIndex).value;
            case 3 -> data.get(rowIndex).date;
            default -> "-";
                };
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return getValueAt(0, columnIndex)!=null ? getValueAt(0, columnIndex).getClass() : Object.class;
    }
}
