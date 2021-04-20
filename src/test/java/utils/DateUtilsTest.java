package utils;

import ntnu.idatt1002.utils.DateUtils;
import org.junit.jupiter.api.Test;

import java.util.Date;
import java.text.SimpleDateFormat;
import java.time.*;

import static org.junit.jupiter.api.Assertions.*;

public class DateUtilsTest {

    @Test
    public void local_date_get_as_ms() {
        LocalDate date = LocalDate.of(2020, 1, 1);
        long ms = date.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
        assertEquals(ms, DateUtils.getAsMs(date));
    }

    @Test
    public void local_date_time_get_as_ms() {
        LocalDateTime date = LocalDateTime.of(1970, 1, 1, 0, 0, 0);
        long ms = date.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();

        assertEquals(ms, DateUtils.getAsMs(date));
    }

    @Test
    public void get_formatted_full_date() {
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        assertEquals(format.format(Date.from(Instant.ofEpochMilli(0l))), DateUtils.getFormattedFullDate(0l));
    }

    @Test
    public void get_formatted_date() {
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        assertEquals(format.format(Date.from(Instant.ofEpochMilli(0l))), DateUtils.getFormattedDate(0l));
    }

    @Test
    public void get_formatted_time() {
        SimpleDateFormat format = new SimpleDateFormat("HH:mm");
        assertEquals(format.format(Date.from(Instant.ofEpochMilli(0l))), DateUtils.getFormattedTime(0l));
    }
}
