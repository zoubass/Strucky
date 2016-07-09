package cz.zoubelu.config;

import org.apache.log4j.Logger;
import org.neo4j.ogm.session.Session;
import org.neo4j.ogm.session.SessionFactory;
import org.springframework.context.annotation.*;
import org.springframework.data.neo4j.config.Neo4jConfiguration;

/**
 * Created by zoubas on 9.7.16.
 */
@Configuration
@Profile("test")
public class GraphTestConfig extends Neo4jConfiguration {
    private final Logger log = Logger.getLogger(getClass());

    @Bean
    public SessionFactory getSessionFactory() {
        return new SessionFactory("cz.zoubelu.domain");
    }

    @Bean
    public Session getSession() throws Exception {
        return super.getSession();
    }

}
