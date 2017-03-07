package cz.zoubelu.utils;

import cz.zoubelu.service.Frequency;
import it.sauronsoftware.cron4j.SchedulingPattern;
import org.apache.commons.lang3.StringUtils;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by zoubas on 14.7.2016.
 */
public class DateUtils {

    public static TimeRange getTimeRange() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Calendar cal = Calendar.getInstance();
        Timestamp start = new Timestamp(cal.getTime().getTime());
        cal.add(Calendar.DATE, -7);
        Timestamp end = new Timestamp(cal.getTime().getTime());
        return new TimeRange(start, end);
    }

    //FIXME or REMOVE IF NOT NEEDED: 28.2.2017 to pri testu vratilo suffix 201700
    public static String getYearMonthSuffix() {
        Calendar cal = Calendar.getInstance();
        String year = String.valueOf(cal.get(Calendar.YEAR));
        String month = String.valueOf(cal.get(Calendar.MONTH) - 1);
        if (month.length() == 1) {
            month = "0" + month;
        }
        return year + month;
    }

    public static TimeRange getFirstHalfOfMonth(String tableName) {
        String year = tableName.substring(tableName.length() - 6, tableName.length() - 2);
        String month = tableName.substring(tableName.length() - 2, tableName.length());

        Timestamp start = Timestamp.valueOf(year + "-" + month + "-" + "01 00:00:00.0");
        Timestamp end = Timestamp.valueOf(year + "-" + month + "-" + "15 23:59:59.9");
        return new TimeRange(start, end);
    }

    public static List<TimeRange> getWeeks() {
        List<TimeRange> weeks = new ArrayList<TimeRange>();
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        return null;
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

    private static String[] parseCron(String pattern) {
        String[] parts = new String[5];
        parts[0] = pattern.substring(0, pattern.indexOf(" ", 0) + 1);
        pattern = StringUtils.replaceOnce(pattern,parts[0],"");
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
}
