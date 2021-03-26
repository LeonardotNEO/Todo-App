package ntnu.idatt1002.utils;

import javafx.util.StringConverter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * A class which provides methods to convert a date
 */
public class DateConverter extends StringConverter<LocalDate> {
    DateTimeFormatter dateFormatter;

    /**
     * A constructor which sets the format of the DateConverter to (dd/MM/yyyy)
     */
    public DateConverter () {
        dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    }

    /**
     * A constructor which sets the format for the date converter
     * @param format
     */
    // This should be checked to be valid / it throws invalid argument exception if pattern is invalid.
    public DateConverter(String format) {
        dateFormatter = DateTimeFormatter.ofPattern(format);
    }

    /**
     * A method to turn a date into a string
     * @param date
     * @return
     */
    @Override
    public String toString(LocalDate date) {
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
    public LocalDate fromString(String string) {
        if(string != null && !string.isEmpty()){
            return LocalDate.parse(string, dateFormatter);
        } else {
            return null;
        }
    }
}
