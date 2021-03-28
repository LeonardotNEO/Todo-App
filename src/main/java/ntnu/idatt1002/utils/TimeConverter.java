package ntnu.idatt1002.utils;

import javafx.util.StringConverter;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class TimeConverter extends StringConverter<LocalTime> {
    DateTimeFormatter dateFormatter;

    /**
     * A constructor which sets the format of the DateConverter to (HH:mm)
     */
    public TimeConverter () {
        dateFormatter = DateTimeFormatter.ofPattern("HH:mm");
    }

    /**
     * A constructor which sets the format for the time converter
     * @param format
     */
    // This should be checked to be valid / it throws invalid argument exception if pattern is invalid.
    public TimeConverter(String format) {
        dateFormatter = DateTimeFormatter.ofPattern(format);
    }

    /**
     * A method to turn a date into a string
     * @param date
     * @return
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
     * A method to turn a string into a date
     * @param string
     * @return
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
