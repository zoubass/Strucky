package cz.zoubelu.utils;

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

	public static String getYearMonthSuffix() {
		Calendar cal = Calendar.getInstance();
		String year = String.valueOf(cal.get(Calendar.YEAR));
		String month = String.valueOf(cal.get(Calendar.MONTH)-1);
		if (month.length()==1){
			month = "0"+month;
		}
		return year + month;
	}

	public static TimeRange getFirstHalfOfMonth(String tableName){
		String year = tableName.substring(tableName.length()-6,tableName.length()-2);
		String month = tableName.substring(tableName.length()-2,tableName.length());

		Timestamp start = Timestamp.valueOf(year+"-"+month+"-"+"01 00:00:00.0");
		Timestamp end = Timestamp.valueOf(year+"-"+month+"-"+"15 23:59:59.9");
		return new TimeRange(start,end);
	}

	public static List<TimeRange> getWeeks(){
		List<TimeRange> weeks = new ArrayList<TimeRange>();
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		return null;
	}

	public static Timestamp getLastDayOfMonth(Timestamp time){
		Calendar c = Calendar.getInstance();
		c.setTime(time);
		int lastDay = c.getActualMaximum(Calendar.DAY_OF_MONTH);
		return Timestamp.valueOf("2016-06-"+lastDay+" 23:59:59.9");
	}
}
