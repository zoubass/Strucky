package cz.zoubelu.config;

import org.apache.log4j.Logger;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;
import org.neo4j.ogm.session.Neo4jSession;
import org.neo4j.ogm.session.Session;
import org.neo4j.ogm.session.SessionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.data.neo4j.config.Neo4jConfiguration;
import org.springframework.data.neo4j.repository.config.EnableNeo4jRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.io.File;

/**
 * Created by zoubas
 */
@Configuration
@EnableNeo4jRepositories(basePackages = "cz.zoubelu.dao")
@EnableTransactionManagement
public class GraphConfig extends Neo4jConfiguration {
	private final Logger log = Logger.getLogger(getClass());

	//    @Value("classpath:conf/neo4j.conf")
	//    private Resource neo4jConfig;

	@Bean public GraphDatabaseService graphDatabaseService() throws Exception {
		String path = "graphDatabase.db";
		log.debug("Creating new embedded database with file: " + path);
		GraphDatabaseService graphDb = new GraphDatabaseFactory().newEmbeddedDatabaseBuilder(new File(path))
				.newGraphDatabase();
		return graphDb;
	}

	@Override public SessionFactory getSessionFactory() {
		return new SessionFactory("cz.zoubelu.model");
	}
}
