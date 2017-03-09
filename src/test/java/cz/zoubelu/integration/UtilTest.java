package cz.zoubelu.integration;

import cz.zoubelu.codelist.SystemApp;
import cz.zoubelu.service.Frequency;
import cz.zoubelu.utils.CsvFileUtils;
import cz.zoubelu.utils.DateUtils;
import cz.zoubelu.utils.TimeRange;
import it.sauronsoftware.cron4j.SchedulingPattern;
import org.junit.Assert;
import org.junit.Test;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zoubas on 14.7.2016.
 */
public class UtilTest extends AbstractTest {

    @Test
    public void testTimeRangeCreation() {
        TimeRange range = DateUtils.getTimeRange();
        Assert.assertEquals("00:00:00.0", range.getStartDate().toString().substring(11, 21));
        Assert.assertEquals("23:59:59.9", range.getEndDate().toString().substring(11, 21));
    }

    @Test
    public void testTableSuffixCreation() {
        String tableName = DateUtils.getYearMonthSuffix();
        Assert.assertTrue(tableName.length() == 6);
    }

    @Test
    public void testSystemListSave() {
        List<SystemApp> systemApps = new ArrayList<SystemApp>();
        systemApps.add(new SystemApp("ONE", 1));
        systemApps.add(new SystemApp("TWO", 2));
        systemApps.add(new SystemApp("THREE", 3));
        CsvFileUtils.save(systemApps, "SYSTEMS_LIST_test.csv");
    }

    @Test
    public void testSystemListLoad() {
        testSystemListSave();
        List<SystemApp> systemApps = CsvFileUtils.load("SYSTEMS_LIST_test.csv");
        Assert.assertEquals(systemApps.size(), 3);
    }

    @Test
    public void shouldRecogniseTypeOfSchedule() {
        SchedulingPattern dailyPattern = new SchedulingPattern("59 11 * * *");
        SchedulingPattern weeklyPattern = new SchedulingPattern("45 8 * * Sun");
        SchedulingPattern monthlyPattern = new SchedulingPattern("00 00 7 * *");

        Frequency daily = DateUtils.recogniseSchedulerFrequency(dailyPattern.toString());
        Frequency weekly = DateUtils.recogniseSchedulerFrequency(weeklyPattern.toString());
        Frequency monthly = DateUtils.recogniseSchedulerFrequency(monthlyPattern.toString());

        Assert.assertEquals(Frequency.DAILY, daily);
        Assert.assertEquals(Frequency.WEEKLY, weekly);
        Assert.assertEquals(Frequency.MONTHLY, monthly);
    }

}
