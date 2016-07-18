package cz.zoubelu.database;

import com.google.common.collect.Lists;
import cz.zoubelu.domain.Application;
import cz.zoubelu.domain.ConsumeRelationship;
import cz.zoubelu.domain.Method;
import cz.zoubelu.repository.ApplicationRepository;
import cz.zoubelu.repository.ConsumesRelationshipRepository;
import cz.zoubelu.repository.MethodRepository;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by zoubas on 12.7.16.
 */
public class CypherQueryTest extends AbstractTest {

    @Autowired
    private MethodRepository methodRepo;

    @Autowired
    private ApplicationRepository appRepo;

    @Autowired
    private ConsumesRelationshipRepository consumeRelationshipRepo;

    private List<Method> initMethods() {
        return Lists.newArrayList(new Method("ping", 1));
    }

    @Test
    public void shouldReturnProvidedMethod() {
        Application app = new Application("czgearnix", initMethods());
        appRepo.save(app);
        Application retrievedApp = appRepo.findByName("czgearnix");
        //get created method ID
        Long methodId = retrievedApp.getProvidedMethods().iterator().next().getId();
        Method method = methodRepo.findProvidedMethodOfApplication(app, "ping", 1);

        //Assert the returned method is the same
        Assert.assertEquals(methodId, method.getId());
        Assert.assertEquals("ping", method.getName());
    }

    @Test
    public void shouldReturnExistingConsumeRelationship() {
        Application consumingApp = new Application("ICONSUME", null);
        Application providingApp = new Application("IPROVIDE", initMethods());
        Method consumedMethod = providingApp.getProvidedMethods().iterator().next();
        //creates list of consumeRelationships and assign them to providing app;
        consumingApp.setConsumeRelationship(Lists.newArrayList(new ConsumeRelationship(consumingApp, consumedMethod, new Long(1))));
        providingApp = appRepo.save(providingApp);
        consumingApp = appRepo.save(consumingApp);
        consumedMethod = providingApp.getProvidedMethods().iterator().next();

        // query for the relationship
        ConsumeRelationship relationship = consumeRelationshipRepo.findRelationship(consumingApp, consumedMethod);

        Assert.assertNotNull(relationship);
        Assert.assertEquals(consumingApp.getId(), relationship.getApplication().getId());
        Assert.assertEquals(consumedMethod.getId(), relationship.getMethod().getId());
    }

    @Test
    public void shouldUpdate() {
        appRepo.save(new Application("test", null));
        Application app = appRepo.findByName("test");
        Long initId = app.getId();
        app.setName("modified");
        appRepo.save(app);
        Application modifiedApp = appRepo.findByName("modified");
        Assert.assertEquals(initId, modifiedApp.getId());

    }
    @After
    public void clear() {
        session.purgeDatabase();
    }
}
