package cz.zoubelu.config;

import cz.zoubelu.repository.InformaDao;
import cz.zoubelu.repository.impl.InformaDaoImpl;
import cz.zoubelu.service.Visualization;
import cz.zoubelu.service.impl.VisualizationImpl;
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
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

/**
 * Created by zoubas
 */
@Configuration
@ComponentScan(basePackages = {"cz.zoubelu.service"})
//TODO: presunout neo4j repositories do graph configurace a zarovne tedy
// udelat jednu konfiguraci, ktera provede funkce jenz maji produkcni a testovaci spolecne...
@EnableNeo4jRepositories(basePackages = "cz.zoubelu.repository")
@EnableTransactionManagement
public class ApplicationConfig {
    private final Logger log = Logger.getLogger(getClass());

    @Autowired
    private DataSource dataSource;

    @Bean
    private static PropertySourcesPlaceholderConfigurer propertyConfigInDev() {
        PropertySourcesPlaceholderConfigurer placeHolder = new PropertySourcesPlaceholderConfigurer();
        placeHolder.setIgnoreResourceNotFound(true);
        placeHolder.setIgnoreUnresolvablePlaceholders(true);
        //TODO: nacitat vsechny properties v lokaci config?
        placeHolder.setLocations(new Resource[]{new ClassPathResource("conf/database.properties"), new ClassPathResource("conf/neo4j.properties")});
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

    @Bean
    public Visualization getVisualization() {
        return new VisualizationImpl();
    }

}
