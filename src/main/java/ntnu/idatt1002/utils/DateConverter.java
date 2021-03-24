package ntnu.idatt1002.utils;

import javafx.util.StringConverter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DateConverter extends StringConverter<LocalDate> {
    DateTimeFormatter dateFormatter;

    public DateConverter () {
        dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    }

    // This should be checked to be valid
    public DateConverter(String format) {
        dateFormatter = DateTimeFormatter.ofPattern(format);
    }

    @Override
    public String toString(LocalDate date) {
        if(date != null) {
            return dateFormatter.format(date);
        } else {
            return "";
        }
    }

    @Override
    public LocalDate fromString(String string) {
        if(string != null && !string.isEmpty()){
            return LocalDate.parse(string, dateFormatter);
        } else {
            return null;
        }
    }
}
