package cz.zoubelu.repository.impl;

import cz.zoubelu.repository.InformaMessageRepository;
import cz.zoubelu.utils.TimeRange;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowCallbackHandler;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Created by zoubas on 25.6.16.
 */
public class InformaRepositoryImpl implements InformaMessageRepository {

	private JdbcTemplate jdbcTemplate;

	public InformaRepositoryImpl(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
		this.jdbcTemplate.setFetchSize(40000);
	}

	public void fetchAndConvertData(String tableName, RowCallbackHandler handler) {
		String sql = "select ID,REQUEST_TIME,RESPONSE_TIME,APPLICATION,ENVIRONMENT,NODE,MSG_TYPE,MSG_VERSION,MSG_UID,MSG_ID,MSG_SRC_SYS,MSG_SRC_ENV,MSG_TAR_SYS,MSG_TAR_ENV,MSG_PRIORITY,MSG_TTL,EXCEPTION,EXCEPTION_MESSAGE,IGNORED_EXCEPTION from ";
		this.jdbcTemplate.query(sql + tableName, handler);
	}

	public void fetchAndConvertData(String tableName, final TimeRange timeRange, RowCallbackHandler handler) {
		final String sql = "select ID,REQUEST_TIME,RESPONSE_TIME,APPLICATION,MSG_TYPE,MSG_VERSION,MSG_SRC_SYS,MSG_TAR_SYS from " + tableName + " where request_time between ? and ?";
		this.jdbcTemplate.query(sql, handler, timeRange.getStartDate(), timeRange.getEndDate());
	}
}
