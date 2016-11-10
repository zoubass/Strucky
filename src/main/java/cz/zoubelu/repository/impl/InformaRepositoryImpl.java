package cz.zoubelu.repository.impl;

import cz.zoubelu.repository.InformaMessageRepository;
import cz.zoubelu.utils.TimeRange;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowCallbackHandler;

/**
 * Created by zoubas on 25.6.16.
 */
public class InformaRepositoryImpl implements InformaMessageRepository {

    private JdbcTemplate jdbcTemplate;

    private String tableName;

    public InformaRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    public void fetchAndConvertData(String tableName,RowCallbackHandler handler) {
        this.jdbcTemplate.query("select * from " + tableName,handler);
    }

    public void fetchAndConvertData(String tableName, TimeRange timeRange, RowCallbackHandler handler) {
        this.jdbcTemplate.query("select * from " + tableName + " where request_time between ? and ?",handler, timeRange.getStartDate(), timeRange.getEndDate());
    }

    public void setTableName(String tableName) {
        this.tableName=tableName;
    }

}
