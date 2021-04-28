package ntnu.idatt1002.utils;

import java.text.SimpleDateFormat;
import java.time.*;
import java.util.Calendar;

/**
 * The DateUtil class contains utility methods to alter the formatting of time.
 */
public final class DateUtils {
    // Private constructor to stop instantiation and overwrite the default constructor.
    private DateUtils () {
        throw new UnsupportedOperationException();
    }

    /**
     * Returns a long representing time in milliseconds since 1/1/1970 UTC+1.
     * @param localDate Object of the LocalDateClass which refer to a specific date.
     * @return The date given as milli seconds.
     */
    public static long getAsMs(LocalDate localDate) {
        Instant instant = localDate.atStartOfDay(ZoneId.systemDefault()).toInstant();
        return instant.toEpochMilli();
    }

    /**
     * Returns a long representing time in milliseconds since 1/1/1970 UTC+1.
     * @param localDateTime Object of the LocalDateTimeClass which refer to a specific time.
     * @return The time given as milli seconds.
     */
    public static long getAsMs(LocalDateTime localDateTime) {
        Instant instant = localDateTime.atZone(ZoneId.systemDefault()).toInstant();
        return instant.toEpochMilli();
    }

    /**
     * Takes a long and turns it into a date in the format dd/MM/yyyy HH:mm. The ms inserted represents the time since 1/1/1970 UTC+1.
     * @param ms The time given as milli seconds.
     * @return date and time given in the format dd/MM/yyyy HH:mm.
     */
    public static String getFormattedFullDate(long ms) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(ms);

        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        String date1 = format.format(calendar.getTime());
        return date1;
    }

    /**
     * Takes a long and turns it into a date in the format dd/MM/yyyy. The ms inserted represents the time since 1/1/1970 UTC+1.
     * @param ms The time given as milli seconds.
     * @return date given in the format dd/MM/yyyy.
     */
    public static String getFormattedDate(long ms) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(ms);

        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        return format.format(calendar.getTime());
    }

    /**
     * Takes a long and turns it into a date with the and outputs the time that given day. THe ms inserted represents the time since 1/1/1970 UTC+1.
     * @param ms The time given as milli seconds.
     * @return time given in the format HH:mm.
     */
    public static String getFormattedTime(long ms) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(ms);
        SimpleDateFormat format = new SimpleDateFormat("HH:mm");
        return format.format(calendar.getTime());
    }
}
