package cz.zoubelu.config.datasource;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import cz.zoubelu.config.DataSource;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.Resource;

@Configuration
@Profile("test")
public class DataSourceTest implements DataSource {
    private final Logger log = Logger.getLogger(getClass());

    @Value("${url}")
    private String url;

    @Value("${driver}")
    private String driver;

    @Value("${username}")
    private String username;

    @Value("${password}")
    private String password;

    @Value("${acquireIncrement}")
    private Integer acquireIncrement;

    @Value("${maxIdleTime}")
    private Integer maxIdleTime;

    @Value("${maxPoolSize}")
    private Integer maxPoolSize;

    @Value("${minPoolSize}")
    private Integer minPoolSize;

    @Value("classpath:h2structure/drop_schema.sql")
    private Resource dropDb;
    @Value("classpath:h2structure/create_schema.sql")
    private Resource createSchema;
    @Value("classpath:h2structure/insert_201606.sql")
    private Resource insertTestData;

    @Bean
    public ComboPooledDataSource getDataSource() {
        ComboPooledDataSource cpds = new ComboPooledDataSource();
        try {
            cpds.setDriverClass(driver);
            cpds.setJdbcUrl(url);
            cpds.setUser(username);
            cpds.setPassword(password);
            cpds.setMinPoolSize(1);
            cpds.setMaxIdleTime(280);
            cpds.setMaxPoolSize(3);
        } catch (Exception e) {
            log.error("Failed to initialize datasource. Exception while setting driver.", e);
        }
        return cpds;
    }
/*
    @Bean
    public DataSourceInitializer dataInit(final ComboPooledDataSource getDataSource, final ResourceDatabasePopulator getDatabasePopulator) {
        DataSourceInitializer dataSourceInitializer = new DataSourceInitializer();
        dataSourceInitializer.setDatabasePopulator(getDatabasePopulator);
        dataSourceInitializer.setDataSource(getDataSource);
        return dataSourceInitializer;
    }

    @Bean
    public ResourceDatabasePopulator getDatabasePopulator() {
        log.debug("running schema creation queries.");
        ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
        populator.addScript(dropDb);
        populator.addScript(createSchema);
        populator.addScript(insertTestData);
        return populator;
    }
*/
}
