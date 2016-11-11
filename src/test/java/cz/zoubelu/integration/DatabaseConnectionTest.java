package cz.zoubelu.integration;

import com.google.common.collect.Lists;
import cz.zoubelu.codelist.SystemApp;
import cz.zoubelu.codelist.SystemsList;
import cz.zoubelu.domain.Method;
import cz.zoubelu.service.DataConversion;
import cz.zoubelu.utils.ConversionError;
import cz.zoubelu.utils.TimeRange;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zoubas
 */
public class DatabaseConnectionTest extends AbstractTest {


    @Autowired
    private JdbcTemplate jdbcTemplate;

    private SystemsList systemsList;

    @Autowired
    private DataConversion dataConversion;

    @Before
    public void startUp() {
        systemsList = new SystemsList();
        for (SystemApp system : systemsList.values()) {
            applicationRepo.save(new cz.zoubelu.domain.Application(system.getName(), system.getId(), new ArrayList<Method>()));
        }
    }

    @Test
    public void shouldNotCreateNewEntitiesIfTheyExist() {
        Timestamp start = Timestamp.valueOf("2016-06-01 00:00:00.0");
        Timestamp end = Timestamp.valueOf("2016-06-01 02:00:00.0");
        List<ConversionError> errors = dataConversion.convertData("MESSAGE", new TimeRange(start, end));

        List<cz.zoubelu.domain.Application> apps = Lists.newArrayList(applicationRepo.findAll());
        Assert.assertEquals("The number of applications in integration is not as expected.There were or were not created applications.", 75, apps.size());
        Assert.assertEquals("The newly created application should be only one and that's 29.", "29", systemsList.getSystemByID(29).getName());
        Assert.assertTrue("Shouldn't contain errors.",errors.size()==0);
    }


    @Test
    public void testDataShouldHaveSpecificSize() {
        Assert.assertNotNull(informaRepository);
        Timestamp start = Timestamp.valueOf("2016-06-01 00:00:00.0");
        Timestamp end = Timestamp.valueOf("2016-06-07 23:59:00.0");

        Long dataSize = jdbcTemplate.queryForObject("select count(*) from Message where request_time between ? and ?",Long.class,start,end);
        Assert.assertEquals(8611, dataSize.longValue());
    }

    @Test
    public void testDataShouldBeInSpecifiedTimeRange() {
        Timestamp minDate = jdbcTemplate.queryForObject("select MIN(request_time) from MESSAGE", Timestamp.class);
        Timestamp maxDate = jdbcTemplate.queryForObject("select MAX(request_time) from MESSAGE", Timestamp.class);
        Assert.assertEquals("2016-06-01 00:09:52.245",minDate.toString());
        Assert.assertEquals("2016-06-26 01:00:18.991",maxDate.toString());
    }

    @After
    public void clear() {
        session.purgeDatabase();
        systemsList.values().clear();
    }
}