package ntnu.idatt1002.utils;

import javafx.util.StringConverter;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * The class TimeConverter is used to convert the time from/to localDate to/from a String.
 */
public class TimeConverter extends StringConverter<LocalTime> {
    DateTimeFormatter dateFormatter;

    /**
     * A constructor which sets the format of the DateConverter to (HH:mm).
     */
    public TimeConverter () {
        dateFormatter = DateTimeFormatter.ofPattern("HH:mm");
    }

    /**
     * A method to turn a date into a string.
     * @param date LocalTime object.
     * @return String representing the time. or a empty string.
     */
    @Override
    public String toString(LocalTime date) {
        if(date != null) {
            return dateFormatter.format(date);
        } else {
            return "";
        }
    }

    /**
     * A method to turn a string into a date.
     * @param string String representing a local Time.
     * @return a localTime.
     */
    @Override
    public LocalTime fromString(String string) {
        if(string != null && !string.isEmpty()){
            return LocalTime.parse(string, dateFormatter);
        } else {
            return null;
        }
    }
}
