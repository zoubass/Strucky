package cz.zoubelu.database;

import cz.zoubelu.config.ApplicationConfig;
import cz.zoubelu.config.DataSourceTestConfig;
import cz.zoubelu.config.GraphTestConfig;
import cz.zoubelu.config.SchedulerConfig;
import cz.zoubelu.repository.InformaDao;
import cz.zoubelu.service.GraphService;
import org.junit.runner.RunWith;
import org.neo4j.ogm.session.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by zoubas on 4.6.16.
 * This is test abstraction with all the configuration needed such as runner, application context and the profile
 */

@RunWith(SpringJUnit4ClassRunner.class)
//TODO: Graph config je navic, v pripade kdy poustim jen test pro oracle db, ale ot je prkotina
@ContextConfiguration(classes = {ApplicationConfig.class, DataSourceTestConfig.class, GraphTestConfig.class,
		SchedulerConfig.class})
@ActiveProfiles("test")
public abstract class AbstractTest {
	@Autowired
	protected GraphService graphService;

	@Autowired
	protected InformaDao informaDao;
	@Autowired
	protected Session session;
}
