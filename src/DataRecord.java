import java.time.LocalDateTime;

class DataRecord {

    float value;
    String type;
    String login;
    LocalDateTime date;

    DataRecord (String login, float value, LocalDateTime date, String type) {
        this.value = value;
        this.type = type;
        this.date = date;
        this.login = login;
    }

    @Override
    public String toString() {
        return String.format(
                "Login: %s, Type: %s, Value: %f, Date: %s", login, type, value, date
        );
    }

}
