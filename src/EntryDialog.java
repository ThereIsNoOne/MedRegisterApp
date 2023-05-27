import javax.swing.*;
import java.awt.*;
import java.time.LocalDateTime;
import java.util.Arrays;

/**
 * Provides implementation of entry dialog, used to add new data to the table.
 */
public class EntryDialog extends JPanel {

    private final GridBagConstraints constraints = new GridBagConstraints();

    private final JComboBox<Integer>[] date = new JComboBox[5];
    private JTextField value = new JTextField();

    /**
     * Constructor of EntryDialog class, sets layout manager and background.
     */
    EntryDialog() {
        this.setBackground(new Color(0x404040));
        this.setLayout(new GridBagLayout());

        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.insets = new Insets(5, 5, 5, 5);
        constraints.weightx = 1;
        constraints.weighty = 1;

        setupLabels();
        setupEntries();
    }

    /**
     * Draw labels.
     */
    private void setupLabels() {
        JLabel value = new JLabel("Enter value of your medical parameter:");
        SetUpUtils.setUpLabel(this, value, 0, 0, constraints);

        JLabel date = new JLabel("Enter date (YYYY/MM/DD):");
        SetUpUtils.setUpLabel(this, date, 0, 1, constraints);

        JLabel time = new JLabel("Enter time:");
        SetUpUtils.setUpLabel(this, time, 0, 2, constraints);
    }

    /**
     * Draw entries (and combo-boxes).
     */
    private void setupEntries() {
        value = new JTextField();
        constraints.gridwidth = 3;
        SetUpUtils.setUpEntry(this, value, 1, 0, constraints);
        constraints.gridwidth = 1;

        date[0] = new JComboBox<>(getYears());
        SetUpUtils.setUpComboBox(date[0], 1, 1, constraints);
        date[0].addActionListener(e -> updateDays());
        this.add(date[0], constraints);

        date[1] = new JComboBox<>(getMonths());
        SetUpUtils.setUpComboBox(date[1], 2, 1, constraints);
        date[1].addActionListener(e -> updateDays());
        this.add(date[1], constraints);

        date[2] = new JComboBox<>(new Integer[31]);
        updateDays();
        SetUpUtils.setUpComboBox(date[2], 3, 1, constraints);
        this.add(date[2], constraints);

        date[3] = new JComboBox<>(getHours());
        SetUpUtils.setUpComboBox(date[3], 1, 2, constraints);
        this.add(date[3], constraints);

        date[4] = new JComboBox<>(getMinutes());
        SetUpUtils.setUpComboBox(date[4], 2, 2, constraints);
        this.add(date[4], constraints);

    }

    /**
     * Create list of minutes used in the combobox.
     * @return array of minutes
     */
    private Integer[] getMinutes() {
        Integer[] minutes = new Integer[60];
        for (int i=1; i<=60; i++) {
            minutes[i-1] = i;
        }
        return minutes;
    }

    /**
     * Create list of hours used in the combobox.
     * @return array of hours.
     */
    private Integer[] getHours() {
        Integer[] hours = new Integer[24];
        for (int i = 0; i < 24; i++) {
            hours[i] = i;
        }
        return hours;
    }

    /**
     * Create list of days used in the combobox, depends on the chosen month, and adds it to combobox.
     */
    private void updateDays() {
        date[2].removeAllItems();
        int max;
        int month = (int) date[1].getSelectedItem();
        boolean isLeapYear = (int) date[0].getSelectedItem() % 4 == 0;
        Integer[] months31 = {1, 3, 5, 7, 8, 10, 12};
        if (Arrays.asList(months31).contains(month)) {
            max = 31;
        }
        else if (month == 2 && isLeapYear) {
            max = 29;
        }
        else if (month == 2) {
            max = 28;
        }
        else {
            max = 30;
        }

        for (int i=1; i<=max; i++) {
            date[2].addItem(i);
        }
    }

    /**
     * Create list of months, used in combobox.
     * @return array of months
     */
    private Integer[] getMonths() {
        Integer[] months = new Integer[12];
        for (int i = 1; i <= 12; i++) {
            months[i-1] = i;
        }
        return months;
    }

    /**
     * Create list of years, used in combobox.
     * @return array of years
     */
    private Integer[] getYears() {
        Integer[] years = new Integer[100];
        int max = LocalDateTime.now().getYear();
        for (int i = 0; i < 100; i++) {
            years[i] = max - i;
        }
        return years;
    }

    /**
     * Get all data from dialog.
     * @return all data from the dialog (value and date)
     */
    float[] getAll() {
        float[] result = new float[6];
        try {
            result[0] = Float.parseFloat(value.getText());
        } catch (NumberFormatException e) {
            throw new RuntimeException("Invalid value");
        }

        for (int i=0; i<=4; i++) {
            result[i+1] = date[i].getItemAt(date[i].getSelectedIndex());
        }
        return result;
    }

}