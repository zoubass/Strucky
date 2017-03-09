package cz.zoubelu.integration;

import com.google.common.collect.Lists;
import cz.zoubelu.cache.Cache;
import cz.zoubelu.codelist.SystemApp;
import cz.zoubelu.codelist.SystemsList;
import cz.zoubelu.domain.ConsumeRelationship;
import cz.zoubelu.domain.Message;
import cz.zoubelu.domain.Method;
import cz.zoubelu.service.DataConversion;
import cz.zoubelu.utils.ConversionError;
import it.sauronsoftware.cron4j.Scheduler;
import it.sauronsoftware.cron4j.SchedulingPattern;
import it.sauronsoftware.cron4j.Task;
import it.sauronsoftware.cron4j.TaskExecutionContext;
import org.junit.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

/**
 * Created by zoubas on 10.7.16.
 */
public class DataConversionTest extends AbstractTest {

    @Autowired
    private DataConversion dataConversion;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private Cache cache;

    @Autowired
    private Scheduler scheduler;

    private List<Message> messages;

    private SystemsList systemsList;

    @Before
    public void startUp() {
  /*
        jdbcTemplate.update("Delete from message");
        final String sql = "INSERT INTO MESSAGE(id,application,msg_src_sys,msg_version,msg_type) VALUES (?,?,?,?,?)";
        int id = 1;
        for (Message m: createMessages()){
            jdbcTemplate.update(sql, id++, m.getApplication(), m.getMsg_src_sys(), m.getMsg_version(), m.getMsg_type());
        }
    */
        messages = createMessages();
        systemsList = new SystemsList();
        for (SystemApp system : systemsList.values()) {
            applicationRepo.save(new cz.zoubelu.domain.Application(system.getName(), system.getId(), new ArrayList<Method>()));
        }
    }

    @Test
    @Ignore("This test is ignored because it tests scheduler and it takes too long")
    public void testScheduling() throws Exception {
        Long init = System.currentTimeMillis();
        //override method using mock, because normal task runs conversion
        Task task = mock(Task.class);
        scheduler.schedule(new SchedulingPattern("/1 * * * *"), task);
        scheduler.start();
        while ((init + 2000 * 60) > System.currentTimeMillis()) {
            /** chill time **/
            /*          *\
            \          /
             \        /
              \ (o.o)/
                 //
                 \\
                 / \
                /**/
        }
        scheduler.stop();
        verify(task, times(2)).execute(any(TaskExecutionContext.class));
    }

    @Test
    public void shouldConvertTheGivenMessagesToGraphData() {
        List<ConversionError> errors = dataConversion.convertData(messages);

        cz.zoubelu.domain.Application agentInfo = applicationRepo.findByName("CZGAGENTINFO");
        cz.zoubelu.domain.Application hugo = applicationRepo.findByName("NHUGO");
        cz.zoubelu.domain.Application a191 = applicationRepo.findByName("191");
        cz.zoubelu.domain.Application lead = applicationRepo.findByName("CZGLEADMNG");

        Assert.assertTrue("Shouldn't contain errors.", errors.size() == 0);

        Assert.assertTrue(agentInfo.getConsumeRelationship().size() > 0);
        ConsumeRelationship firstAgRel = agentInfo.getConsumeRelationship().get(1);
        ConsumeRelationship secondAgRel = agentInfo.getConsumeRelationship().get(0);
        List<ConsumeRelationship> hugoRels = hugo.getConsumeRelationship();

        Assert.assertEquals("CZGAGENTINFO", firstAgRel.getApplication().getName());
        Assert.assertEquals("getLead", firstAgRel.getMethod().getName());
        Assert.assertEquals(new Long(2), firstAgRel.getTotalUsage());

        Assert.assertEquals("CZGAGENTINFO", secondAgRel.getApplication().getName());
        Assert.assertEquals("getPropertySomething", secondAgRel.getMethod().getName());
        Assert.assertEquals(new Long(1), secondAgRel.getTotalUsage());

        Assert.assertEquals(1, hugoRels.size());

        ConsumeRelationship hugoRel = hugoRels.get(0);

        Assert.assertEquals("NHUGO", hugoRel.getApplication().getName());
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

        Assert.assertEquals("The size of all apps there are in SystemIDs + 1 (the one should be created). ", 74,
                Lists.newArrayList(applicationRepo.findAll()).size());
        Assert.assertEquals("Method size is not correct.", 5, Lists.newArrayList(methodRepo.findAll()).size());
        Assert.assertEquals("The number of relations in graph.", 5, Lists.newArrayList(relationshipRepo.findAll()).size());
    }

    @Test
    public void testVisualizationQuery() {
        List<ConversionError> errors = dataConversion.convertData(messages);
        List<Map<String, Object>> result = relationshipRepo.getGraph();

        Assert.assertTrue("Shouldn't contain errors.", errors.size() == 0);
        Assert.assertEquals("The number of relations.", result.size(), 10);
    }

    @Test
    public void testSingleAppVisualisation() {
        List<ConversionError> errors = dataConversion.convertData(messages);
        List<Map<String, Object>> result = relationshipRepo.getApplicationRelationships(applicationRepo.findByName("CZGAGENTINFO"));

        Assert.assertTrue("Shouldn't contain errors.", errors.size() == 0);
        Assert.assertTrue("The number of relations.", result.size() == 2);
        boolean hasMethod = ((Method) result.get(0).get("method")).getName().equals("getPropertySomething");

        if (hasMethod) {
            Assert.assertTrue(((Method) result.get(1).get("method")).getName().equals("getLead"));
        } else {
            Assert.assertTrue(((Method) result.get(0).get("method")).getName().equals("getLead"));
            Assert.assertTrue(((Method) result.get(1).get("method")).getName().equals("getPropertySomething"));
        }
    }

    @Test
    @Ignore("This test is only for performance comparison.")
    public void testRowCallBack() {
        long first = System.currentTimeMillis();
        List<ConversionError> errors = dataConversion.convertData("MESSAGE");
        long second = System.currentTimeMillis();

        System.out.println("---------------------------------------------------------");
        System.out.println(second - first);
        System.out.println("---------------------------------------------------------");
        Assert.assertTrue("Shouldn't contain errors.", errors.size() == 0);
        Assert.assertTrue(applicationRepo.findBySystemId(0).getProvidedMethods().size() == 0);
        Assert.assertTrue(applicationRepo.findByName("CZGCONTRACTIMPORT").getSystemId() > 3000);
    }

    @After
    public void clear() {
        session.purgeDatabase();
        messages = null;
        systemsList.values().clear();
        cache.clearCache();
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
        m1.setApplication("CZGCALCBM");
        m1.setMsg_src_sys(1001);
        m1.setMsg_type("getClientValue");
        m1.setMsg_version(100);

        m2.setApplication("CZGCALCBM");
        m2.setMsg_src_sys(1001);
        m2.setMsg_type("getClientValue");
        m2.setMsg_version(100);

        m3.setApplication("CZGEARNIX");
        m3.setMsg_src_sys(16);
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

        m6.setApplication("CZGEARNIX");
        m6.setMsg_src_sys(191);
        m6.setMsg_type("nothing");
        m6.setMsg_version(null);

        m7.setApplication("czgleadmng");
        m7.setMsg_src_sys(16);
        m7.setMsg_type("getLead");
        m7.setMsg_version(110);

        m8.setApplication("CZGLEADMNG");
        m8.setMsg_src_sys(16);
        m8.setMsg_type("getLead");
        m8.setMsg_version(110);
        return Lists.newArrayList(m1, m2, m3, m4, m5, m6, m7, m8);
    }

}
