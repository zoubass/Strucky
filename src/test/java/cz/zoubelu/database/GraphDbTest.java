package cz.zoubelu.database;

import com.google.common.collect.Lists;

import cz.zoubelu.domain.Application;
import cz.zoubelu.domain.ConsumeRelationship;
import cz.zoubelu.domain.Method;
import cz.zoubelu.service.Visualization;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by zoubas on 26.6.16.
 */
public class GraphDbTest extends AbstractTest {

    @Autowired
    private Visualization visualization;

    private List<Application> getAppNode() {
        Assert.assertNotNull(graphService);
        List<Method> providedMethods = new ArrayList<>();
        providedMethods.add(new Method("getClientValue", 100));
        Application providingApp = new Application("providingApp", providedMethods);
        Application consumingApp = new Application("consumingApp", null);
        ConsumeRelationship consumeRelationship = new ConsumeRelationship();
        consumeRelationship.setApplication(consumingApp);
        consumeRelationship.setMethod(providedMethods.iterator().next());
        consumingApp.setConsumeRelationship(Lists.newArrayList(consumeRelationship));

        return Lists.newArrayList(consumingApp, providingApp);
    }

    @Test
    public void shouldSaveAppAndRetrieveInformation() {
        //TODO: zmenit vytvoreni dat na @Before a nebrat si to z metody getAppNode
        graphService.saveApps(getAppNode());
        List<Application> apps = graphService.findAll();

        Assert.assertNotNull(apps.get(0));
        String consumedMethodName = apps.get(0).getConsumeRelationship().get(0).getMethod().getName();

        Assert.assertEquals("consumingApp", apps.get(0).getName());
        Assert.assertEquals("getClientValue", consumedMethodName);
    }

    @Test
    public void testVisualizeJson() {
        //TODO: Stejny jak vyse
        graphService.saveApps(getAppNode());
        visualization.visualizeGraph();
    }

    @After
    public void clear() {
        session.purgeDatabase();
    }
}
