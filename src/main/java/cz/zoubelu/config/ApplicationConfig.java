package cz.zoubelu.config;

import cz.zoubelu.repository.InformaDao;
import cz.zoubelu.repository.impl.InformaDaoImpl;
import cz.zoubelu.service.ApplicationService;
import cz.zoubelu.service.ApplicationServiceImpl;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
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
@ComponentScan(basePackages = {"cz.zoubelu.controller", "cz.zoubelu.service"})
//TODO: presunout neo4j repositories do graph configurace a zarovne tedy
// udelat jednu konfiguraci, ktera provede funkce jenz maji produkcni a testovaci spolecne...
@EnableNeo4jRepositories(basePackages = "cz.zoubelu.repository")
@EnableTransactionManagement
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
        //TODO: nejde nacitat vsechny properties v lokaci config?
        placeHolder.setLocations(new Resource[]{new ClassPathResource("conf/database.properties"), new ClassPathResource("conf/neo4j.properties")});
        return placeHolder;
    }

    @Bean
    public InformaDao getInformaDao() {
        return new InformaDaoImpl(getJdbcTemplate());
    }

//    @Bean
//    public ApplicationService getAppService() {
//        return new ApplicationServiceImpl();
//    }

    @Bean
    public JdbcTemplate getJdbcTemplate() {
        return new JdbcTemplate(dataSource.getDataSource());
    }

}
