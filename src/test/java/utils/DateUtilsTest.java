package utils;

import ntnu.idatt1002.utils.DateUtils;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

public class DateUtilsTest {

    @Test
    public void LocalDateGetAsMsTest() {
        LocalDate date = LocalDate.of(2020, 1, 1);
        assertEquals(1577833200000l, DateUtils.getAsMs(date));
    }

    @Test
    public void LocalDateTimeGetAsMsTest() {
        LocalDateTime date = LocalDateTime.of(1970, 1, 1, 1, 0, 0);
        assertEquals(0l, DateUtils.getAsMs(date));
    }

    @Test
    public void getFormattedFullDateTest() {
        LocalDateTime date = LocalDateTime.of(1970, 1, 1, 1, 0, 0);
        assertEquals("01/01/1970 01:00", DateUtils.getFormattedFullDate(0l));
    }

    @Test
    public void getFormattedDateTest() {
        assertEquals("01/01/1970", DateUtils.getFormattedDate(0l));
    }

    @Test
    public void getFormattedTimeTest() {
        assertEquals("01:00", DateUtils.getFormattedTime(0l));
    }
}
