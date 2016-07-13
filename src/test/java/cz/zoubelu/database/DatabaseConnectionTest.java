package cz.zoubelu.database;


import cz.zoubelu.repository.InformaDao;
import cz.zoubelu.domain.Message;
import cz.zoubelu.repository.mapper.InteractionMapper;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

/**
 * Created by zoubas
 */
public class DatabaseConnectionTest extends AbstractTest {
    @Autowired
    private InformaDao dao;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    public void testConnection() {
        Assert.assertNotNull(dao);
        Timestamp start = Timestamp.valueOf("2016-06-01 00:00:00.0");
        Timestamp end = Timestamp.valueOf("2016-06-07 23:59:00.0");

        List<Message> messages = dao.getInteractionData(start,end);
        Assert.assertEquals(25968, messages.size());
    }

    @Test
    public void testSqlStatement() {
        List<Timestamp> minDate = jdbcTemplate.queryForList("select MIN(request_time) from MESSAGE", Timestamp.class);
        List<Timestamp> maxDate = jdbcTemplate.queryForList("select MAX(request_time) from MESSAGE", Timestamp.class);
        Assert.assertNotNull(minDate);
        Assert.assertNotNull(maxDate);
    }

}