package cz.zoubelu.config;

import cz.zoubelu.repository.InformaRepository;
import cz.zoubelu.repository.impl.InformaRepositoryImpl;
import cz.zoubelu.service.Visualization;
import cz.zoubelu.service.impl.VisualizationImpl;
import cz.zoubelu.validation.Validator;
import cz.zoubelu.validation.impl.MessageValidator;
import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.data.neo4j.repository.config.EnableNeo4jRepositories;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Created by zoubas
 */
@Configuration
@ComponentScan(basePackages = {"cz.zoubelu.service"})
@EnableNeo4jRepositories(basePackages = "cz.zoubelu.repository")
@EnableTransactionManagement
public class ApplicationConfig {

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


    @Bean
    public BasicDataSource getDataSource() {
        BasicDataSource bd = new BasicDataSource();
        bd.setDriverClassName(driver);
        bd.setUrl(url);
        bd.setUsername(username);
        bd.setPassword(password);
        bd.setInitialSize(5);
        bd.setMaxIdle(maxIdleTime);
        bd.setMaxActive(10);
//        bd.setPoolPreparedStatements(true);
        return bd;
    }

    @Bean
    private static PropertySourcesPlaceholderConfigurer propertyConfigInDev() {
        PropertySourcesPlaceholderConfigurer placeHolder = new PropertySourcesPlaceholderConfigurer();
        placeHolder.setIgnoreResourceNotFound(true);
        placeHolder.setIgnoreUnresolvablePlaceholders(true);
        placeHolder.setLocations(new Resource[]{new ClassPathResource("conf/database.properties"), new ClassPathResource("conf/neo4j.properties"),new ClassPathResource("conf/config.properties")});
        return placeHolder;
    }

    @Bean
    public InformaRepository getInformaDao() {
        return new InformaRepositoryImpl(getJdbcTemplate());
    }

    @Bean
    public JdbcTemplate getJdbcTemplate() {
        return new JdbcTemplate(getDataSource());
    }

    @Bean
    public Validator getValidator() {
        return new MessageValidator();
    }

}
