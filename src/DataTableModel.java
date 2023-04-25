import javax.swing.table.AbstractTableModel;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

public class DataTableModel extends AbstractTableModel {
    private final String[] columnNames = {"login", "type", "value", "register_time"};
    private ArrayList<DataRecord> data;
    private DataManager manager;

    DataTableModel(String type) {
        try {
            this.manager = new DataManager();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        setData(type);
    }

    void setData(String type) {
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
    public void setValueAt(Object value, int rowIndex, int columnIndex) {
        DataRecord row = data.get(rowIndex);
        float oldValue = (float) getValueAt(rowIndex, columnIndex);
        row.setValue((float) value);
        try {
            manager.setValue(row.getType(), row.getValue(), row.getDate(), oldValue);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columIndex) {
        return (columIndex == 2);
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
            case 0 -> data.get(rowIndex).getLogin();
            case 1 -> data.get(rowIndex).getType();
            case 2 -> data.get(rowIndex).getValue();
            case 3 -> data.get(rowIndex).getDate();
            default -> "-";
                };
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return getValueAt(0, columnIndex)!=null ? getValueAt(0, columnIndex).getClass() : Object.class;
    }

    public ArrayList<DataRecord> getData() {
        return data;
    }
}
