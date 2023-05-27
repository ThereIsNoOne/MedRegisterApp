import java.time.LocalDateTime;

/**
 * Data record class, representing one row of a table.
 */
class DataRecord {

    private float value;
    private String type;
    private String login;
    private LocalDateTime date;

    /**
     * Construct an instance of a DataRecord.
     * @param login User's login
     * @param value Value of the record
     * @param date Date of the record
     * @param type Type of the medical parameter
     */
    DataRecord (String login, float value, LocalDateTime date, String type) {
        this.value = value;
        this.type = type;
        this.date = date;
        this.login = login;
    }

    /**
     * Getter for the value of the record.
     * @return Value
     */
    public float getValue() {
        return value;
    }

    /**
     * Getter for the date of the record.
     * @return Date
     */
    public LocalDateTime getDate() {
        return date;
    }

    /**
     * Getter for the login.
     * @return login
     */
    public String getLogin() {
        return login;
    }

    /**
     * Getter for the type of the medical parameter.
     * @return type of medical parameter
     */
    public String getType() {
        return type;
    }

    /**
     * Setter for the date of the record.
     * @param date Record date
     */
    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    /**
     * Setter for the login.
     * @param login User's login
     */
    public void setLogin(String login) {
        this.login = login;
    }

    /**
     * Setter for the type of medical parameter.
     * @param type Type of medical parameter
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * Setter for the value.
     * @param value value of medical parameter
     */
    public void setValue(float value) {
        this.value = value;
    }

    /**
     * String representation of the record.
     * @return String representation of the record
     */
    @Override
    public String toString() {
        return String.format(
                "Login: %s, Type: %s, Value: %f, Date: %s", login, type, value, date
        );
    }

}
