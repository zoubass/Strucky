package cz.zoubelu.config;

import org.apache.commons.dbcp.BasicDataSource;

/**
 * Created by zoubas on 26.6.16.
 */
public interface DataSource {
    BasicDataSource getDataSource();
}
