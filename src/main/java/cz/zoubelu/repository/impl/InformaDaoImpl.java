package cz.zoubelu.repository.impl;

import cz.zoubelu.repository.InformaDao;
import cz.zoubelu.repository.mapper.InteractionMapper;
import cz.zoubelu.domain.Message;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.Timestamp;
import java.util.List;

/**
 * Created by zoubas on 25.6.16.
 */
public class InformaDaoImpl implements InformaDao {

    private JdbcTemplate jdbcTemplate;

    private static final String TABLE_NAME = "MESSAGE";

    public InformaDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    //TODO: neskládat SQL jako literály

    @Override
    public List<Message> getInteractionData(Timestamp start, Timestamp end) {
        return this.jdbcTemplate.query("select * from " + TABLE_NAME + " where request_time between ? and ?",new InteractionMapper(), start, end);
    }

    @Override
    public List<String> getApplicationsInPlatform() {
        return this.jdbcTemplate.queryForList("select distinct application from " + TABLE_NAME, String.class);
    }

    @Override
    public List<String> getMethodOfApplication(String application) {
        return this.jdbcTemplate.queryForList("select distinct msg_type from MESSAGE where application = ?", String.class, application);
    }
}
