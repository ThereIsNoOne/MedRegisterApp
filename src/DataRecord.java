import java.time.LocalDateTime;

class DataRecord {

    private float value;
    private String type;
    private String login;
    private LocalDateTime date;

    DataRecord (String login, float value, LocalDateTime date, String type) {
        this.value = value;
        this.type = type;
        this.date = date;
        this.login = login;
    }

    public float getValue() {
        return value;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public String getLogin() {
        return login;
    }

    public String getType() {
        return type;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setValue(float value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return String.format(
                "Login: %s, Type: %s, Value: %f, Date: %s", login, type, value, date
        );
    }

}
