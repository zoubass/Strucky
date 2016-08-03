package cz.zoubelu.utils;

import java.sql.Time;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by zoubas on 14.7.2016.
 */
public class DateUtils {

	public static TimeRange getTimeRange(){
		//TODO: osetrit mozne exceptiony a upravit tak aby to bralo od pulnoci prvniho dne do pulnoci posledniho
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Calendar cal = Calendar.getInstance();
		Timestamp start = new Timestamp(cal.getTime().getTime());
		cal.add(Calendar.DATE, -7);
		Timestamp end = new Timestamp(cal.getTime().getTime());
		return new TimeRange(start,end);
	}
}
