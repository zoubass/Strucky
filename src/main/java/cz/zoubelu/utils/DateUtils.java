package cz.zoubelu.utils;

import cz.zoubelu.service.Frequency;
import it.sauronsoftware.cron4j.SchedulingPattern;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by zoubas on 14.7.2016.
 */
public class DateUtils {

    public static TimeRange getTimeRange() {
        return getEdgesForDate(new Date());
    }

    public static TimeRange getWeekTimeRange() {
        Calendar cal = Calendar.getInstance();
        Timestamp start = new Timestamp(cal.getTime().getTime());
        cal.add(Calendar.DATE, -7);
        Timestamp end = new Timestamp(cal.getTime().getTime());
        return new TimeRange(start, end);
    }

    private static TimeRange getEdgesForDate(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        Timestamp start = new Timestamp(cal.getTime().getTime());

        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);
        cal.set(Calendar.MILLISECOND, 999);
        Timestamp end = new Timestamp(cal.getTime().getTime());
        return new TimeRange(start, end);
    }

    public static String getYearMonthSuffix() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        String year = String.valueOf(cal.get(Calendar.YEAR));
        String month = String.valueOf(cal.get(Calendar.MONTH));
        if (month.length() == 1) {
            month = "0" + month;
        }
        if (month.equals("00")) {
            month = "12";
        }
        return year + month;
    }

    public static Timestamp getLastDayOfMonth(Timestamp time) {
        Calendar c = Calendar.getInstance();
        c.setTime(time);
        int lastDay = c.getActualMaximum(Calendar.DAY_OF_MONTH);
        return Timestamp.valueOf("2016-06-" + lastDay + " 23:59:59.9");
    }

    public static Frequency recogniseSchedulerFrequency(String pattern) {
        //do some magic
        String[] parts = parseCron(pattern);
        String minute = parts[0];
        String hour = parts[1];
        String dayOfMonth = parts[2];
        String month = parts[3];
        String dayOfWeek = parts[4];

        if (month.concat(dayOfMonth).concat(dayOfWeek).equals("***")) {
            return Frequency.DAILY;
        }
        if (!dayOfWeek.equals("*") && !(minute.concat(hour).equals("**"))) {
            return Frequency.WEEKLY;
        }
        if (!dayOfMonth.equals("*") && !(minute.concat(hour).equals("**"))) {
            return Frequency.MONTHLY;
        }
        return Frequency.UNKNOWN;
    }

    public static TimeRange getTimeRangeByFrequency(String pattern) {
        Frequency frequency = recogniseSchedulerFrequency(pattern);
        switch (frequency) {
            case DAILY:
                return getTimeRange();
            case WEEKLY:
                return getWeekTimeRange();
            case MONTHLY:
                return null;
            case UNKNOWN:
                throw new RuntimeException("Failed to recognise frequency of scheduler from pattern: " + pattern);
            default:
                throw new RuntimeException("Failed to recognise frequency of scheduler from pattern: " + pattern);
        }
    }

    private static String[] parseCron(String pattern) {
        String[] parts = new String[5];
        parts[0] = pattern.substring(0, pattern.indexOf(" ", 0) + 1);
        pattern = StringUtils.replaceOnce(pattern, parts[0], "");
        parts[0].trim();
        int i = 1;
        int index = 0;
        while (i < 4) {
            parts[i] = pattern.substring(index, pattern.indexOf(" ", index) + 1).trim();
            index = pattern.indexOf(" ", index) + 1;
            i++;
        }
        //last part remains in pattern
        parts[4] = pattern.substring(pattern.lastIndexOf(" "), pattern.length()).trim();
        return parts;
    }

    public static String getActualDateSuffix() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        return sdf.format(new Date());
    }
}
