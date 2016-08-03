package cz.zoubelu.config;

import org.apache.commons.dbcp.BasicDataSource;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.datasource.init.DataSourceInitializer;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;

@Configuration
@Profile("test")
public class DataSourceTestConfig {
    private final Logger log = Logger.getLogger(getClass());


    @Value("classpath:h2structure/drop_schema.sql")
    private Resource dropDb;
    @Value("classpath:h2structure/create_schema.sql")
    private Resource createSchema;
    @Value("classpath:h2structure/insert_201606.sql")
    private Resource insertTestData;

/*
    UNCOMMENT TO INSERT DATA INTO H2 DATABASE

    @Bean
    public DataSourceInitializer dataInit(final BasicDataSource getDataSource, final ResourceDatabasePopulator getDatabasePopulator) {
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
