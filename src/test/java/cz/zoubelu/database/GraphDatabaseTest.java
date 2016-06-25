package cz.zoubelu.database;

import cz.zoubelu.dao.repository.IApplicationRepo;
import junit.framework.Assert;
import org.junit.Test;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

/**
 * Created by zoubas on 4.6.16.
 */
public class GraphDatabaseTest extends AbstractTest {
//    @Autowired
//    private GraphDatabaseFactory factory;
//    @Autowired
//    private IApplicationRepo appRepository;

    @Test
    public void testGraphDb() {
//        GraphDatabaseService service = factory.newEmbeddedDatabase("test_database.db");
//        Assert.assertNotNull(appRepository);
    }
}
