package cz.zoubelu.database;

import com.google.common.collect.Lists;
import cz.zoubelu.domain.Application;
import cz.zoubelu.domain.Method;
import cz.zoubelu.repository.ApplicationRepository;
import cz.zoubelu.service.ApplicationService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.ogm.session.Session;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by zoubas on 26.6.16.
 */
public class GraphDbTest extends AbstractTest {
    @Autowired
    private ApplicationService appService;

    @Autowired
    private Session session;

    private Application getAppNode() {
        Assert.assertNotNull(appService);
        Method m = new Method();
        Set<Method> methods = new HashSet<Method>();
        m.setVersion(42);
        methods.add(m);
        Application app = new Application();
        app.setAppName("testApp");
        app.setMethods(methods);
        return app;
    }

    @Test
    public void testConnection() {
        appService.save(getAppNode());
        appService.save(new Application("druha", null));
        List<Application> apps = appService.findAll();
        Assert.assertEquals("testApp", apps.get(0).getAppName());
    }
}
