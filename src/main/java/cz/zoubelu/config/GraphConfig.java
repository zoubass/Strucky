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

    @Bean
    public SessionFactory getSessionFactory() {
        return new SessionFactory("cz.zoubelu.domain");
    }

    @Bean
    public Session getSession() throws Exception {
        return super.getSession();
    }
}
