package cz.zoubelu.database;

import com.google.common.collect.Lists;
import cz.zoubelu.domain.Application;
import cz.zoubelu.domain.ConsumeRelationship;
import cz.zoubelu.domain.Message;
import cz.zoubelu.domain.Method;
import cz.zoubelu.service.DataConversion;
import cz.zoubelu.service.Visualization;
import cz.zoubelu.utils.SystemID;
import cz.zoubelu.utils.TimeRange;
import it.sauronsoftware.cron4j.Scheduler;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by zoubas on 10.7.16.
 */
public class DataConversionTest extends AbstractTest {

    @Autowired
    private DataConversion dataConversion;

    @Autowired
    private Scheduler scheduler;

    @Autowired
    private Visualization visualization;

    @Before
    public void createSchemaFromEnum() {
        for (SystemID sysId : SystemID.values()) {
            applicationRepo.save(new Application(sysId.name(), sysId.getID(), new ArrayList<Method>()));
        }
    }

    @Test
    public void testConversionOnExistingSchema() {
        Assert.assertNotNull(dataConversion);
        Timestamp start = Timestamp.valueOf("2016-06-01 00:00:00.0");
        Timestamp end = Timestamp.valueOf("2016-06-01 02:00:00.0");
        dataConversion.convertData(new TimeRange(start, end));

        List<Application> apps = Lists.newArrayList(applicationRepo.findAll());
        for (Application app : apps) {
            System.out.println(app.getName());
            Assert.assertNotNull(app.getSystemId());
        }
        Assert.assertEquals(69, apps.size());
    }


    @Test
    public void testScheduling() throws Exception {
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
        Application a191 = applicationRepo.findByName("191");
        Application lead = applicationRepo.findByName(SystemID.CZGLEADMNG.name());

        ConsumeRelationship firstAgRel = agentInfo.getConsumeRelationship().get(0);
        ConsumeRelationship secondAgRel = agentInfo.getConsumeRelationship().get(1);
        List<ConsumeRelationship> hugoRels = hugo.getConsumeRelationship();

        Assert.assertTrue(agentInfo.getConsumeRelationship().size() > 0);
        Assert.assertEquals(SystemID.CZGAGENTINFO.name(), firstAgRel.getApplication().getName());
        Assert.assertEquals("getLead", firstAgRel.getMethod().getName());
        Assert.assertEquals(new Long(2), firstAgRel.getTotalUsage());

        Assert.assertEquals(SystemID.CZGAGENTINFO.name(), secondAgRel.getApplication().getName());
        Assert.assertEquals("getPropertySomething", secondAgRel.getMethod().getName());
        Assert.assertEquals(new Long(1), secondAgRel.getTotalUsage());

        Assert.assertEquals(1, hugoRels.size());

        ConsumeRelationship hugoRel = hugoRels.get(0);

        Assert.assertEquals(SystemID.NHUGO.name(), hugoRel.getApplication().getName());
        Assert.assertEquals("getClientValue", hugoRel.getMethod().getName());
        Assert.assertEquals(new Long(2), hugoRel.getTotalUsage());

        Assert.assertEquals(2, a191.getConsumeRelationship().size());

        ConsumeRelationship first191Rel = a191.getConsumeRelationship().get(0);
        Assert.assertEquals("191", first191Rel.getApplication().getName());
        Assert.assertEquals("?", first191Rel.getMethod().getName());
        Assert.assertEquals(new Long(2), first191Rel.getTotalUsage());


        ConsumeRelationship second191Rel = a191.getConsumeRelationship().get(1);
        Assert.assertEquals("191", second191Rel.getApplication().getName());
        Assert.assertEquals("nothing", second191Rel.getMethod().getName());
        Assert.assertEquals(new Long(1), second191Rel.getTotalUsage());

        Assert.assertEquals("getLead", lead.getProvidedMethods().get(0).getName());

        Assert.assertEquals(6, Lists.newArrayList(applicationRepo.findAll()).size());
        Assert.assertEquals(5, Lists.newArrayList(methodRepo.findAll()).size());
        Assert.assertEquals(5, Lists.newArrayList(relationshipRepo.findAll()).size());
    }

    @Test
    public void testVisualizationQuery() {
        dataConversion.convertData(createMessages());
        List<Map<String, Object>> result = relationshipRepo.getGraph();
        Map<String,Object> graph = visualization.visualizeGraph();
        Assert.assertEquals(graph.size(), 10);

    }

    @After
    public void clear() {
        session.purgeDatabase();
    }

    private List<Message> createMessages() {
        Message m1 = new Message();
        Message m2 = new Message();
        Message m3 = new Message();
        Message m4 = new Message();
        Message m5 = new Message();
        Message m6 = new Message();
        Message m7 = new Message();
        Message m8 = new Message();
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

        m4.setApplication("czgcalcbm");
        m4.setMsg_src_sys(191);
        m4.setMsg_type("?");
        m4.setMsg_version(null);

        m5.setApplication("czgcalcbm");
        m5.setMsg_src_sys(191);
        m5.setMsg_type("?");
        m5.setMsg_version(null);

        m6.setApplication(SystemID.CZGEARNIX.name());
        m6.setMsg_src_sys(191);
        m6.setMsg_type("nothing");
        m6.setMsg_version(null);

        m7.setApplication("czgleadmng");
        m7.setMsg_src_sys(SystemID.CZGAGENTINFO.getID());
        m7.setMsg_type("getLead");
        m7.setMsg_version(110);

        m8.setApplication(SystemID.CZGLEADMNG.name());
        m8.setMsg_src_sys(SystemID.CZGAGENTINFO.getID());
        m8.setMsg_type("getLead");
        m8.setMsg_version(110);
        return Lists.newArrayList(m1, m2, m3, m4, m5, m6, m7, m8);
    }
}
