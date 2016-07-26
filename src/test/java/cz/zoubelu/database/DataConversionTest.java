package cz.zoubelu.database;

import com.google.common.collect.Lists;
import cz.zoubelu.domain.*;
import cz.zoubelu.service.DataConversion;
import cz.zoubelu.utils.SystemID;
import cz.zoubelu.utils.TimeRange;
import it.sauronsoftware.cron4j.Scheduler;
import org.junit.After;
import org.junit.Test;
import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zoubas on 10.7.16.
 */
public class DataConversionTest extends AbstractTest {

    @Autowired
    private DataConversion dataConversion;

    @Autowired
    private Scheduler scheduler;

    private void createDbStructure() {
        List<String> appsInDb = informaDao.getApplicationsInPlatform();
        List<Application> applications = new ArrayList<Application>();
        for (String appName : appsInDb) {
            List<String> methodsMessages = informaDao.getMethodOfApplication(appName);
            List<Method> methods = new ArrayList<>();
            for (String methodName : methodsMessages) {
                //metody s verzi jinou nez 100 se musi dynamicky vytvorit, coz je soucast testu
                methods.add(new Method(methodName, 100));
            }
            applications.add(new Application(appName, methods));
        }
        applicationRepo.save(applications);
    }

    @Test
    public void shouldConvertFromRDBMToGraph() {
		createDbStructure();
        Assert.assertNotNull(dataConversion);
        Timestamp start = Timestamp.valueOf("2016-06-01 00:00:00.0");
        Timestamp end = Timestamp.valueOf("2016-06-01 02:00:00.0");
        dataConversion.convertData(new TimeRange(start,end));
        //TODO: předělat na result
        Assert.assertEquals("nofail", "nofail");
    }


    @Test
    public void testScheduling() throws Exception{
        scheduler.start();
        Thread.sleep(1000L * 60L * 1L);
        scheduler.stop();
        Assert.assertTrue(true);
    }

    @Test
    public void testConversionFromMessages() {
		clear();
        dataConversion.convertData(createMessages());

        Application agentInfo = applicationRepo.findByName(SystemID.CZGAGENTINFO.name());
        Application hugo = applicationRepo.findByName(SystemID.NHUGO.name());

        ConsumeRelationship agentRel = agentInfo.getConsumeRelationship().get(0);
		List<ConsumeRelationship> hugoRels = hugo.getConsumeRelationship();

		Assert.assertTrue(agentInfo.getConsumeRelationship().size() > 0);
		Assert.assertEquals(SystemID.CZGAGENTINFO.name(), agentRel.getApplication().getName());
		Assert.assertEquals("getPropertySomething", agentRel.getMethod().getName());
		Assert.assertEquals(new Long(1), agentRel.getTotalUsage());

		Assert.assertEquals(1,hugoRels.size());

		ConsumeRelationship hugoRel = hugoRels.get(0);

		Assert.assertEquals(SystemID.NHUGO.name(), hugoRel.getApplication().getName());
		Assert.assertEquals("getClientValue", hugoRel.getMethod().getName());
		Assert.assertEquals(new Long(2), hugoRel.getTotalUsage());

        Assert.assertEquals(4, Lists.newArrayList(applicationRepo.findAll()).size());
        Assert.assertEquals(2, Lists.newArrayList(methodRepo.findAll()).size());
        Assert.assertEquals(2, Lists.newArrayList(relationshipRepo.findAll()).size());
    }

    private List<Message> createMessages() {
        Message m1 = new Message();
        Message m2 = new Message();
        Message m3 = new Message();
        m1.setApplication(SystemID.CZGCALCBM.name());
        m1.setMsg_src_sys(SystemID.NHUGO.getID());
        m1.setMsg_type("getClientValue");
        m1.setMsg_version(100);

        m2.setApplication(SystemID.CZGCALCBM.name());
        m2.setMsg_src_sys(SystemID.NHUGO.getID());
        m2.setMsg_type("getClientValue");
        m2.setMsg_version(100);

        m3.setApplication(SystemID.CZGEARNIX.name());
        m3.setMsg_src_sys(SystemID.CZGAGENTINFO.getID());
        m3.setMsg_type("getPropertySomething");
        m3.setMsg_version(110);
        return Lists.newArrayList(m1,m2,m3);
    }

    @After
    public void clear() {
        session.purgeDatabase();
    }
}
