import javax.swing.table.AbstractTableModel;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Provides implementation of basic table model.
 */
public class DataTableModel extends AbstractTableModel {
    private final String[] columnNames = {"login", "type", "value", "register_time"};
    private ArrayList<DataRecord> data;
    private final DataManager manager;

    /**
     * Creates a new DataTableModel for certain type of medical parameter.
     * @param type The type of medical parameter.
     */
    DataTableModel(String type) {
        try {
            this.manager = new DataManager();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        setData(type);
    }

    /**
     * Get the data from the database.
     * @param type Type of medical parameter to be retrieved from database.
     */
    void setData(String type) {
        try {
            data = manager.getDataRecords(type);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Adds a new data record to the table.
     * @param record Record to bo added.
     */
    void addRow(DataRecord record) {
        data.add(record);
    }

    /**
     * Removes a data record from the table.
     * @param rowIndex index of the data record to be removed
     */
    void deleteRow(int rowIndex) {
        data.remove(rowIndex);
    }

    /**
     * Sets value of the certain cell.
     * @param value   value to assign to cell
     * @param rowIndex   row of cell
     * @param columnIndex  column of cell
     */
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

    /**
     * Check if the cell is editable.
     * @param rowIndex  the row being queried
     * @param columIndex the column being queried
     * @return true if the cell is editable, otherwise false.
     */
    @Override
    public boolean isCellEditable(int rowIndex, int columIndex) {
        return (columIndex == 2);
    }

    /**
     * Get name of the column.
     * @param columnIndex  the column being queried
     * @return name of the column.
     */
    @Override
    public String getColumnName(int columnIndex) {
        return columnNames[columnIndex];
    }

    /**
     * Get number of rows in the table.
     * @return number of rows in the table
     */
    @Override
    public int getRowCount() {
        return data.size();
    }

    /**
     * Get number of columns.
     * @return number of columns
     */
    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    /**
     * Get value at certain row and column.
     * @param rowIndex the row whose value is to be queried
     * @param columnIndex the column whose value is to be queried
     * @return value of the column
     */
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

    /**
     * Get class of data stored in column.
     * @param columnIndex  the column being queried
     * @return class of data stored in column.
     */
    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return getValueAt(0, columnIndex)!=null ? getValueAt(0, columnIndex).getClass() : Object.class;
    }

    /**
     * Getter for data.
     * @return data
     */
    public ArrayList<DataRecord> getData() {
        return data;
    }
}
