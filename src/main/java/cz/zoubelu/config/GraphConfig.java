package cz.zoubelu.config;

import org.neo4j.ogm.session.Session;
import org.neo4j.ogm.session.SessionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.neo4j.config.Neo4jConfiguration;

/**
 * Created by zoubas
 */
@Configuration
@Profile("production")
public class GraphConfig extends Neo4jConfiguration {
/*
    @Value("${neo4j.db.url}")
    private String url;

    @Value("${neo4j.db.driver")
    private String driver;

    @Bean
    public org.neo4j.ogm.config.Configuration getConfiguration() {
        org.neo4j.ogm.config.Configuration config = new org.neo4j.ogm.config.Configuration();
        config
                .driverConfiguration()
                .setDriverClassName(driver)
                .setURI(url);
        return config;
    }
    @Bean
    public SessionFactory getSessionFactory() {
        return new SessionFactory(getConfiguration(), "cz.zoubelu.domain");
    }

    @Bean
    @Scope(value = "session", proxyMode = ScopedProxyMode.TARGET_CLASS)
    public Session getSession() throws Exception {
        return super.getSession();
    }
    */
    @Bean
    public SessionFactory getSessionFactory() {
        return new SessionFactory("cz.zoubelu.domain");
    }

    @Bean
    public Session getSession() throws Exception {
        return super.getSession();
    }



}
