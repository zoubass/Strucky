package cz.zoubelu.database;

import com.google.common.collect.Lists;
import cz.zoubelu.domain.Application;
import cz.zoubelu.domain.ConsumeRelationship;
import cz.zoubelu.domain.Method;
import cz.zoubelu.service.GraphService;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by zoubas on 26.6.16.
 */
public class GraphDbTest extends AbstractTest {
    @Autowired
    private GraphService appService;


    private List<Application> getAppNode() {
        Assert.assertNotNull(appService);
        Set<Method> providedMethods = new HashSet<Method>();
        providedMethods.add(new Method("getClientValue",154));
        Application providingApp = new Application("providingApp",providedMethods);
        Application consumingApp = new Application("consumingApp",null);
        ConsumeRelationship consumeRelationship = new ConsumeRelationship();
        consumeRelationship.setApplication(consumingApp);
        consumeRelationship.setMethod(providedMethods.iterator().next());
        consumingApp.setConsumeRelationship(Lists.newArrayList(consumeRelationship));

        return Lists.newArrayList(consumingApp,providingApp);
    }

    @Test
    public void shouldSaveAppAndRetrieveInformation() {
        for (Application app: getAppNode()) {
            appService.save(app);
        }
        List<Application> apps = appService.findAll();

        Assert.assertNotNull(apps.get(0));
        String consumedMethodName = apps.get(0).getConsumeRelationship().get(0).getMethod().getName();

        Assert.assertEquals("consumingApp", apps.get(0).getAppName());
        Assert.assertEquals("getClientValue",consumedMethodName);
    }
}
