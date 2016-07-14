package cz.zoubelu.utils;

import java.sql.Timestamp;

/**
 * Created by t922274 on 14.7.2016.
 */
//TODO: presunnout tridu do jineho package
public class TimeRange {
	private Timestamp startDate;
	private Timestamp endDate;

	public TimeRange(Timestamp startDate, Timestamp endDate) {
		this.startDate = startDate;
		this.endDate = endDate;
	}

	public Timestamp getStartDate() {
		return startDate;
	}

	public void setStartDate(Timestamp startDate) {
		this.startDate = startDate;
	}

	public Timestamp getEndDate() {
		return endDate;
	}

	public void setEndDate(Timestamp endDate) {
		this.endDate = endDate;
	}
}