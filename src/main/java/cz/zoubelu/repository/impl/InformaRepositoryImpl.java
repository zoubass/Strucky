package cz.zoubelu.repository.impl;

import cz.zoubelu.repository.InformaMessageRepository;
import cz.zoubelu.repository.mapper.MessageMapper;
import cz.zoubelu.domain.Message;
import cz.zoubelu.utils.TimeRange;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

/**
 * Created by zoubas on 25.6.16.
 */
public class InformaRepositoryImpl implements InformaMessageRepository {

    private JdbcTemplate jdbcTemplate;

    public InformaRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Message> getInteractionData(TimeRange timeRange) {
        String tableName = "MESSAGE"; /*getTableName();*/
        return this.jdbcTemplate.query("select * from " + tableName + " where request_time between ? and ?",new MessageMapper(), timeRange.getStartDate(), timeRange.getEndDate());
    }

    @Override public List<Message> getInteractionData(String tableName) {
        return null;
    }
}
