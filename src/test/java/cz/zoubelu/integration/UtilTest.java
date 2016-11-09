package cz.zoubelu.integration;

import cz.zoubelu.utils.DateUtils;
import cz.zoubelu.utils.TimeRange;
import org.junit.Assert;
import org.junit.Test;

import java.sql.Timestamp;

/**
 * Created by zoubas on 14.7.2016.
 */
public class UtilTest extends AbstractTest {

	@Test
	public void testTimeRangeCreation() {
		TimeRange range = DateUtils.getTimeRange();
		Assert.assertNotNull(range.getStartDate());
		Assert.assertNotNull(range.getEndDate());
	}

	@Test
	public void testTableSuffixCreation() {
		String tableName = DateUtils.getTableSuffix("A_MESSAGE_");
		Assert.assertTrue(tableName.contains("A_MESSAGE_"));
		Assert.assertTrue(tableName.length()==16);
	}

	@Test
	public void testLastDayOfMonthUtil() {
		Timestamp time = DateUtils.getLastDayOfMonth(Timestamp.valueOf("2016-06-01 00:00:00"));
		Assert.assertEquals(Timestamp.valueOf("2016-06-30 23:59:59.9"),time);
	}
}
