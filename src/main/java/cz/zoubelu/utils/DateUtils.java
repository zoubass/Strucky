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
		//TODO: osetrit mozne exceptiony a upravit tak aby to bralo od pulnoci prvniho dne do pulnoci posledniho
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Calendar cal = Calendar.getInstance();
		Timestamp start = new Timestamp(cal.getTime().getTime());
		cal.add(Calendar.DATE, -7);
		Timestamp end = new Timestamp(cal.getTime().getTime());
		return new TimeRange(start, end);
	}

	public static String getTableSuffix(String tablePrefix) {
		Calendar cal = Calendar.getInstance();
		String year = String.valueOf(cal.get(Calendar.YEAR));
		String month = String.valueOf(cal.get(Calendar.MONTH)-1);
		if (month.length()==1){
			month = "0"+month;
		}
		return tablePrefix + year + month;
	}

	public static List<TimeRange> getWeeks(){
		List<TimeRange> weeks = new ArrayList<>();
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
