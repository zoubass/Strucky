package cz.zoubelu.database;


import cz.zoubelu.domain.Message;
import cz.zoubelu.repository.mapper.MessageMapper;
import cz.zoubelu.utils.TimeRange;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.Timestamp;
import java.util.List;

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

        List<Message> messages = informaRepository.getInteractionData(new TimeRange(start,end));
        Assert.assertEquals(69008, messages.size());
    }

    @Test
    public void testDataShouldBeInSpecifiedTimeRange() {
        List<Timestamp> minDate = jdbcTemplate.queryForList("select MIN(request_time) from MESSAGE", Timestamp.class);
        List<Timestamp> maxDate = jdbcTemplate.queryForList("select MAX(request_time) from MESSAGE", Timestamp.class);
        System.out.println(minDate.toString());
        Assert.assertEquals("[2016-06-01 00:00:00.081]",minDate.toString());
        Assert.assertEquals("[2016-06-27 00:35:00.114]",maxDate.toString());
    }

//    @Test
    public void testSomething() {
        List<String> tars = jdbcTemplate.queryForList("select distinct msg_tar_sys from MESSAGE", String.class);
        System.out.println("MSG_TARGET_SYS:"+tars.size());
        for (String app:tars) {
            System.out.println(app);
        }
        List<String> srcs = jdbcTemplate.queryForList("select distinct msg_src_sys from MESSAGE", String.class);
        System.out.println("MSG_SRC_SYS:"+srcs.size());
        for (String app:srcs) {
            System.out.println(app);
        }
        List<Message> ver = jdbcTemplate.query("select * from MESSAGE where msg_src_sys='20'",new MessageMapper());
        System.out.println("MSG_VER:"+ver.size());
    }

}