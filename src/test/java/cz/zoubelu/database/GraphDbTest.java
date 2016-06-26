package cz.zoubelu.database;

import cz.zoubelu.config.GraphConfig;
import cz.zoubelu.dao.GraphDao;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

/**
 * Created by zoubas on 26.6.16.
 */
@ContextConfiguration(classes = GraphConfig.class)
public class GraphDbTest extends AbstractTest {
    @Autowired
    private GraphDao graphDao;

    @Test
    public void testConnection() {
        Assert.assertNotNull(graphDao);
    }
}
