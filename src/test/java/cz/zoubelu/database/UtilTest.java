package cz.zoubelu.database;

import cz.zoubelu.utils.DateUtils;
import cz.zoubelu.utils.TimeRange;
import org.junit.Assert;
import org.junit.Test;

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
}
