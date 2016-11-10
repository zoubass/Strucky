package cz.zoubelu.integration;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.Timestamp;

/**
 * Created by zoubas
 */
public class DatabaseConnectionTest extends AbstractTest {


    @Autowired
    private JdbcTemplate jdbcTemplate;

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

}