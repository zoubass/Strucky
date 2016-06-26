package cz.zoubelu.database;

import cz.zoubelu.config.ApplicationConfig;
import cz.zoubelu.config.TestConfig;
import org.junit.runner.RunWith;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by zoubas on 4.6.16.
 * This is test abstraction with all the configuration needed such as runner, application context and the profile
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {ApplicationConfig.class, TestConfig.class})
@ActiveProfiles("test")
public abstract class AbstractTest {
}
