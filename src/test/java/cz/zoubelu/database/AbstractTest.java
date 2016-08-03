package cz.zoubelu.database;

import cz.zoubelu.config.ApplicationConfig;
import cz.zoubelu.config.DataSourceTestConfig;
import cz.zoubelu.config.GraphTestConfig;
import cz.zoubelu.config.SchedulerConfig;
import cz.zoubelu.repository.ApplicationRepository;
import cz.zoubelu.repository.InformaDao;
import cz.zoubelu.repository.MethodRepository;
import cz.zoubelu.repository.RelationshipRepository;
import org.junit.runner.RunWith;
import org.neo4j.ogm.session.Session;
import org.neo4j.shell.App;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by zoubas on 4.6.16.
 * This is test abstraction with all the configuration needed such as runner, application context and the profile
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {ApplicationConfig.class, DataSourceTestConfig.class, GraphTestConfig.class,
		SchedulerConfig.class})
@ActiveProfiles("test")
public abstract class AbstractTest {
	@Autowired
	protected InformaDao informaDao;
	@Autowired
	protected Session session;
	@Autowired
	protected ApplicationRepository applicationRepo;

	@Autowired
	protected MethodRepository methodRepo;

	@Autowired
	protected RelationshipRepository relationshipRepo;

}
