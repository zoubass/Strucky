package cz.zoubelu.config;

import cz.zoubelu.dao.InformaDao;
import cz.zoubelu.dao.impl.InformaDaoImpl;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * Created by zoubas
 */
@Configuration
//@ComponentScan(basePackages = "cz.zoubelu.controller")
//@PropertySource("classpath:database.properties")
public class ApplicationConfig {
    private final Logger log = Logger.getLogger(getClass());

    @Autowired
    private DataSource dataSource;

    @Bean
    private static PropertySourcesPlaceholderConfigurer propertyConfigInDev() {
        PropertySourcesPlaceholderConfigurer placeHolder = new PropertySourcesPlaceholderConfigurer();
        placeHolder.setIgnoreResourceNotFound(true);
        placeHolder.setIgnoreUnresolvablePlaceholders(true);
        placeHolder.setLocation(new ClassPathResource("conf/database.properties"));
        return placeHolder;
    }

    @Bean
    public InformaDao getInformaDao() {
        return new InformaDaoImpl(getJdbcTemplate());
    }

    @Bean
    public JdbcTemplate getJdbcTemplate() {
        return new JdbcTemplate(dataSource.getDataSource());
    }

}
