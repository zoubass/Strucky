package cz.zoubelu.config;

import org.apache.log4j.Logger;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;
import org.neo4j.ogm.session.SessionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.neo4j.config.Neo4jConfiguration;
import org.springframework.data.neo4j.repository.config.EnableNeo4jRepositories;

import java.io.File;

/**
 * Created by zoubas
 */
@Configuration
@EnableNeo4jRepositories(basePackages = "cz.zoubelu.dao")
public class GraphConfig extends Neo4jConfiguration {
    private final Logger log = Logger.getLogger(getClass());

    @Override
    public SessionFactory getSessionFactory() {
        return null;
    }


    @Bean
    public GraphDatabaseService graphDatabaseService() {
        String path = "test.db";
        log.debug("Creating new embedded database with file: " + path);
        return new GraphDatabaseFactory().newEmbeddedDatabase(new File(path));
    }
}
