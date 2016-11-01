package cz.zoubelu.config;

import com.mchange.v2.c3p0.ComboPooledDataSource;

/**
 * Created by t922274 on 1.11.2016.
 */
public interface DataSource {

	ComboPooledDataSource getDataSource();
}
