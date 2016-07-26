    package cz.zoubelu.database;

    import com.google.common.collect.Lists;
    import cz.zoubelu.domain.Application;
    import cz.zoubelu.domain.ConsumeRelationship;
    import cz.zoubelu.domain.Method;
    import cz.zoubelu.repository.ApplicationRepository;
    import cz.zoubelu.repository.RelationshipRepository;
    import cz.zoubelu.repository.MethodRepository;
    import org.junit.After;
    import org.junit.Assert;
    import org.junit.Test;
    import org.springframework.beans.factory.annotation.Autowired;

    import java.util.List;

    /**
     * Created by zoubas on 12.7.16.
     */
    public class CypherQueryTest extends AbstractTest {

        private List<Method> initMethods() {
            return Lists.newArrayList(new Method("ping", 1));
        }

        @Test
        public void shouldReturnProvidedMethod() {
            Application app = new Application("czgearnix", initMethods());
            applicationRepo.save(app);
            Application retrievedApp = applicationRepo.findByName("czgearnix");
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
        public void shouldUpdate() {
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
