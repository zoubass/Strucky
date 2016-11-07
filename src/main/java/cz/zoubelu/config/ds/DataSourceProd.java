package cz.zoubelu.config.ds;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import cz.zoubelu.config.DataSource;
import org.apache.log4j.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

/**
 * Created by t922274 on 1.11.2016.
 */
@Configuration
@Profile("production")
public class DataSourceProd implements DataSource{
	private final Logger log = Logger.getLogger(getClass());

	@Bean
	public ComboPooledDataSource getDataSource() {
		ComboPooledDataSource cpds = new ComboPooledDataSource();
		try {
			cpds.setDriverClass("org.h2.Driver");
			cpds.setJdbcUrl("jdbc:h2:~/test;MODE=Oracle");
			cpds.setUser("test");
			cpds.setPassword("test");
			cpds.setMinPoolSize(1);
			cpds.setMaxIdleTime(280);
			cpds.setMaxPoolSize(3);
		} catch (Exception e) {
			log.error("Failed to initialize datasource. Exception while setting driver.",e);
		}
		return cpds;
	}

}
