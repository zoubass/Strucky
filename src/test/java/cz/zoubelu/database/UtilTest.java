package cz.zoubelu.database;

import cz.zoubelu.utils.DateUtils;
import cz.zoubelu.utils.TimeRange;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by t922274 on 14.7.2016.
 */
public class UtilTest extends AbstractTest {

	@Test
	public void testTimeRangeCreation() {
		TimeRange range = DateUtils.getTimeRange();

		System.out.println(range.getStartDate());
		System.out.println(range.getEndDate());

		Assert.assertNotNull(range.getStartDate());
		Assert.assertNotNull(range.getEndDate());
	}
}
