package cz.zoubelu.integration;

import com.google.common.collect.Lists;
import cz.zoubelu.domain.Application;
import cz.zoubelu.domain.ConsumeRelationship;
import cz.zoubelu.domain.Method;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

/**
 * Created by zoubas on 12.7.16.
 */
public class CypherQueryTest extends AbstractTest {
    private List<Method> methods = null;

    @Before
    public void startUp() {
        methods = Lists.newArrayList(new Method("ping", 1));
    }

    @Test
    public void shouldReturnProvidedMethod() {
        Application app = new Application("czgearnix", methods);
        applicationRepo.save(app);
        Application retrievedApp = applicationRepo.findByName("czgearnix");
        //get created method ID
        Long methodId = retrievedApp.getProvidedMethods().iterator().next().getId();
        Method method = methodRepo.findProvidedMethod(app, "ping", 1);

        //Assert the returned method is the same
        Assert.assertEquals(methodId, method.getId());
        Assert.assertEquals("ping", method.getName());
    }

    @Test
    public void shouldReturnExistingConsumeRelationship() {
        Application consumingApp = new Application("ICONSUME", null);
        Application providingApp = new Application("IPROVIDE", methods);
        Method consumedMethod = providingApp.getProvidedMethods().iterator().next();
        //creates list of consumeRelationships and assign them to providing app;
        consumingApp.setConsumeRelationship(Lists.newArrayList(new ConsumeRelationship(consumingApp, consumedMethod)));
        providingApp = applicationRepo.save(providingApp);
        consumingApp = applicationRepo.save(consumingApp);
        consumedMethod = providingApp.getProvidedMethods().iterator().next();

        // query for the relationship
        ConsumeRelationship relationship = relationshipRepo.findRelationship(consumingApp, consumedMethod);

        Assert.assertNotNull(relationship);
        Assert.assertEquals(consumingApp.getId(), relationship.getApplication().getId());
        Assert.assertEquals(consumedMethod.getId(), relationship.getMethod().getId());
    }

    @Test
    public void shouldUpdateApplicationInformation() {
        applicationRepo.save(new Application("test", null));
        Application app = applicationRepo.findByName("test");
        Long initId = app.getId();
        app.setName("modified");
        applicationRepo.save(app);
        Application modifiedApp = applicationRepo.findByName("modified");
        Assert.assertEquals(initId, modifiedApp.getId());
    }

    @After
    public void clear() {
        session.purgeDatabase();
    }
}
