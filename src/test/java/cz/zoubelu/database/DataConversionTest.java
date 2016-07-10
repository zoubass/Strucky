package cz.zoubelu.database;

import cz.zoubelu.domain.Application;
import cz.zoubelu.domain.Message;
import cz.zoubelu.domain.Method;
import cz.zoubelu.repository.InformaDao;
import cz.zoubelu.service.ApplicationService;
import cz.zoubelu.service.DataConversionService;
import org.junit.Before;
import org.junit.Test;
import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by zoubas on 10.7.16.
 */
public class DataConversionTest extends AbstractTest {

    @Autowired
    private DataConversionService dataConversion;

    @Autowired
    private ApplicationService applicationService;

    @Autowired
    private InformaDao informaDao;

    @Before
    public void createDbStructure() {
        List<String> appsInDb = informaDao.getApplicationsInPlatform();
        List<Application> applications = new ArrayList<Application>();
        for (String appName : appsInDb) {
            List<String> methodsMessages = informaDao.getMethodOfApplication(appName);
            Set<Method> methods = new HashSet<>();
            for (String methodName : methodsMessages) {
                methods.add(new Method(methodName,0));
            }
            applications.add(new Application(appName, methods));
        }
        applicationService.save(applications);
    }

    @Test
    public void shouldConvertFromRDBMToGraph() {
        Assert.assertNotNull(dataConversion);
        dataConversion.convertToGraphData();
        //TODO: otestovat
    }
}
