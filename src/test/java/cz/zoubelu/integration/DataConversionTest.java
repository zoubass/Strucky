package cz.zoubelu.integration;

import com.google.common.collect.Lists;
import cz.zoubelu.codelist.SystemApp;
import cz.zoubelu.codelist.SystemsList;
import cz.zoubelu.domain.Application;
import cz.zoubelu.domain.ConsumeRelationship;
import cz.zoubelu.domain.Message;
import cz.zoubelu.domain.Method;
import cz.zoubelu.repository.mapper.MessageMapper;
import cz.zoubelu.service.DataConversion;
import cz.zoubelu.utils.ConversionError;
import cz.zoubelu.utils.TimeRange;
import it.sauronsoftware.cron4j.Scheduler;
import it.sauronsoftware.cron4j.SchedulingPattern;
import it.sauronsoftware.cron4j.Task;
import it.sauronsoftware.cron4j.TaskExecutionContext;
import org.junit.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.Timestamp;
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
    private JdbcTemplate template;
    @Autowired
    private Scheduler scheduler;

    private List<Message> messages;

    @Before
    public void startUp() {
        messages = createMessages();
        for (SystemApp system : new SystemsList().values()) {
            applicationRepo.save(new Application(system.getName(), system.getId(), new ArrayList<Method>()));
        }
    }

    @Test
    public void shouldNotCreateNewEntitiesIfTheyExist() {
        Timestamp start = Timestamp.valueOf("2016-06-01 00:00:00.0");
        Timestamp end = Timestamp.valueOf("2016-06-01 02:00:00.0");
        List<ConversionError> errors = dataConversion.convertData("MESSAGE", new TimeRange(start, end));

        List<Application> apps = Lists.newArrayList(applicationRepo.findAll());
        Assert.assertEquals("The number of applications in integration is not as expected.There were or were not created applications.", 75, apps.size());
        Assert.assertEquals("The newly created application should be only one and that's 29.", "29", SystemsList.getSystemByID(29).getName());
    }


    @Test
    @Ignore("This test is ignored because it tests scheduler and it takes too long")
    public void testScheduling() throws Exception {
        Long init = System.currentTimeMillis();
        //override method using mock, because normal task runs conversion
        Task task = mock(Task.class);
        scheduler.schedule(new SchedulingPattern("* * * * *"), task);
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
        dataConversion.convertData(messages);

        Application agentInfo = applicationRepo.findByName("CZGAGENTINFO");
        Application hugo = applicationRepo.findByName("NHUGO");
        Application a191 = applicationRepo.findByName("191");
        Application lead = applicationRepo.findByName("CZGLEADMNG");

        ConsumeRelationship firstAgRel = agentInfo.getConsumeRelationship().get(0);
        ConsumeRelationship secondAgRel = agentInfo.getConsumeRelationship().get(1);
        List<ConsumeRelationship> hugoRels = hugo.getConsumeRelationship();

        Assert.assertTrue(agentInfo.getConsumeRelationship().size() > 0);
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

        Assert.assertEquals("The size of all apps there are in SystemIDs + 1 (the one should be created). ", 75,
                Lists.newArrayList(applicationRepo.findAll()).size());
        Assert.assertEquals("Method size is not correct.", 5, Lists.newArrayList(methodRepo.findAll()).size());
        Assert.assertEquals("The number of relations in graph.", 5, Lists.newArrayList(relationshipRepo.findAll()).size());
    }

    @Test
    public void testVisualizationQuery() {
        dataConversion.convertData(messages);
        List<Map<String, Object>> result = relationshipRepo.getGraph();
        Assert.assertEquals(result.size(), 10);
    }

    @Test
    public void testSingleAppVisualisation() {
        dataConversion.convertData(createMessages());
        List<Map<String, Object>> result = relationshipRepo.getApplicationRelationships(applicationRepo.findByName("CZGAGENTINFO"));
        Assert.assertTrue(result.size() == 2);
        Assert.assertEquals("getLead", ((Method) result.get(1).get("method")).getName());
        Assert.assertEquals("getPropertySomething", ((Method) result.get(0).get("method")).getName());
    }

    @After
    public void clear() {
        session.purgeDatabase();
        messages = null;
        SystemsList.values().clear();
    }

    @Test
    @Ignore("This test is only for performance comparison")
    public void testRowCallBack() {
        Timestamp start = Timestamp.valueOf("2016-06-01 00:00:00.0");
        Timestamp end = Timestamp.valueOf("2016-06-03 00:00:00.0");
        long first = System.currentTimeMillis();
        dataConversion.convertData("MESSAGE",new TimeRange(start,end));
        long second = System.currentTimeMillis();
        long third = System.currentTimeMillis();
        List<Message> messages = template.query("select * from MESSAGE where request_time between ? and ?", new MessageMapper(),start,end);
        dataConversion.convertData(messages);
        long fourth = System.currentTimeMillis();
        System.out.println(second-first);
        System.out.println(fourth-third);
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
