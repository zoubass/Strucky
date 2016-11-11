package cz.zoubelu.repository.impl;

import cz.zoubelu.repository.InformaMessageRepository;
import cz.zoubelu.utils.TimeRange;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowCallbackHandler;
import scala.Int;

import java.sql.SQLException;

/**
 * Created by zoubas on 25.6.16.
 */
public class InformaRepositoryImpl implements InformaMessageRepository {

	private JdbcTemplate jdbcTemplate;

	private String tableName;

	public InformaRepositoryImpl(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	public void fetchAndConvertData(String tableName, RowCallbackHandler handler) {
		this.jdbcTemplate.query("select * from " + tableName, handler);
	}

	public void fetchAndConvertData(String tableName, final TimeRange timeRange, RowCallbackHandler handler) {
//		try {
//			jdbcTemplate.getDataSource().getConnection().setAutoCommit(false);
//		} catch (SQLException e) {
//			System.out.println("we|re fu*ked up.");
//		}
//		jdbcTemplate.setFetchSize(Int.MinValue());
		final String sql = "select * from " + tableName + " where request_time between ? and ?";
		this.jdbcTemplate.query(sql, handler, timeRange.getStartDate(), timeRange.getEndDate());
	}
}
