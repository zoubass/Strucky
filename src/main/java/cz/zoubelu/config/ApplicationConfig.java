package cz.zoubelu.config;

import cz.zoubelu.codelist.SystemsList;
import cz.zoubelu.repository.InformaMessageRepository;
import cz.zoubelu.repository.impl.InformaRepositoryImpl;
import cz.zoubelu.service.DataConversion;
import cz.zoubelu.service.impl.DataConversionImpl;
import cz.zoubelu.utils.CsvFileUtils;
import cz.zoubelu.validation.Validator;
import cz.zoubelu.validation.impl.MessageValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
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
@ComponentScan(basePackages = {"cz.zoubelu.service", "cz.zoubelu.cache"})
@EnableNeo4jRepositories(basePackages = "cz.zoubelu.repository")
@EnableTransactionManagement
@PropertySource(value = {"classpath:conf/database.properties", "classpath*:neo-config.properties"}, ignoreResourceNotFound = true)
public class ApplicationConfig {

    @Autowired
    private DataSource dataSource;

    @Value("${systems.list.location}")
    private String fileLocation;

    @Bean
    private static PropertySourcesPlaceholderConfigurer propertyConfigInDev() {
        PropertySourcesPlaceholderConfigurer placeHolder = new PropertySourcesPlaceholderConfigurer();
        placeHolder.setIgnoreResourceNotFound(true);
        placeHolder.setIgnoreUnresolvablePlaceholders(true);
        placeHolder.setLocations(new Resource[]{new ClassPathResource("conf/neo-config.properties")});
        return placeHolder;
    }

    @Bean
    public InformaMessageRepository getInformaDao() {
        return new InformaRepositoryImpl(getJdbcTemplate());
    }

    @Bean
    public JdbcTemplate getJdbcTemplate() {
        return new JdbcTemplate(dataSource.getDataSource());
    }

    @Bean
    public Validator getValidator() {
        return new MessageValidator();
    }

    @Bean
    public DataConversion getConvertor() {
        return new DataConversionImpl();
    }

    @Bean
    public SystemsList getSystemList() {
        SystemsList systemsList = new SystemsList();
        systemsList.setFileLocation(fileLocation);
        return systemsList;
    }

}
