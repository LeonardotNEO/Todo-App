package ntnu.idatt1002.utils;

import java.text.SimpleDateFormat;
import java.time.*;
import java.util.Calendar;

public final class DateUtils {

    // Private constructor to stop instantiation and overwrite the default constructor.
    private DateUtils () {
        throw new UnsupportedOperationException();
    }

    /**
     * Returns a long representing time in milliseconds since 1/1/1970
     * @param localdate
     * @return
     */
    public static long getAsMs(LocalDate localdate) {
        Instant instant = localdate.atStartOfDay(ZoneId.systemDefault()).toInstant();
        return instant.toEpochMilli();
    }

    /**
     * Returns a long representing time in milliseconds since 1/1/1970
     * @param localDateTime
     * @return
     */
    public static long getAsMs(LocalDateTime localDateTime) {
        Instant instant = localDateTime.atZone(ZoneId.systemDefault()).toInstant();
        return instant.toEpochMilli();
    }

    /**
     * Takes a long and turns it into a date in the format dd/MM/yyyy HH:mm. The ms inserted represents the time since 1/1/1970
     * @param ms
     * @return
     */
    public static String getFormattedFullDate(long ms) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(ms);

        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        String date1 = format.format(calendar.getTime());
        return date1;
    }

    /**
     * Takes a long and turns it into a date in the format dd/MM/yyyy. The ms inserted represents the time since 1/1/1970
     * @param ms
     * @return
     */
    public static String getFormattedDate(long ms) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(ms);

        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        String date1 = format.format(calendar.getTime());

        return date1;
    }

    /**
     * Takes a long and turns it into a date with the and outputs the time that given day. THe ms inserted represents the time since 1/1/1970
     * @param ms
     * @return
     */
    public static LocalTime getFormattedTime(long ms) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(ms);
        SimpleDateFormat format = new SimpleDateFormat("hh:mm");
        String date1 = format.format(calendar.getTime());
        return Instant.ofEpochMilli(ms).atZone(ZoneId.systemDefault()).toLocalTime();
    }
}
