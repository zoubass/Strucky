package cz.zoubelu.controller;

import cz.zoubelu.domain.Application;
import cz.zoubelu.domain.ConsumeRelationship;
import cz.zoubelu.domain.Method;
import cz.zoubelu.repository.ApplicationRepository;
import cz.zoubelu.repository.InformaRepository;
import cz.zoubelu.repository.MethodRepository;
import cz.zoubelu.repository.RelationshipRepository;
import cz.zoubelu.service.DataConversion;
import cz.zoubelu.service.Visualization;
import cz.zoubelu.utils.NullUtils;
import cz.zoubelu.utils.TimeRange;
import org.neo4j.ogm.session.Neo4jSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

/**
 * Created by zoubas on 16.7.16.
 */

@RestController
@RequestMapping(value = "/application")
public class RestGraphController {
    @Autowired
    private ApplicationRepository applicationRepo;

    @Autowired
    private MethodRepository methodRepo;

    @Autowired
    private Neo4jSession session;

    @Autowired
    private RelationshipRepository relationRepo;

    @Autowired
    private InformaRepository informaRepository;

    @Autowired
    private Visualization visualization;

    @Autowired
    private DataConversion dataConversion;

    /**
     * @param systemId
     * @return Application with specified system ID
     */
    @RequestMapping(value = "/{systemId}", method = RequestMethod.GET)
    @ResponseBody
    public Application getApplication(@PathVariable("systemId") Integer systemId) {
        NullUtils.nullCheck(systemId);
        return applicationRepo.findBySystemId(systemId);
    }

    /**
     * @param systemId
     * @return Methods that are being consumed by application with specified system ID.
     */
    @RequestMapping(value = "/{systemId}/consumes", method = RequestMethod.GET)
    @ResponseBody
    public List<ConsumeRelationship> getConsumedMethods(@PathVariable("systemId") Integer systemId) {
        NullUtils.nullCheck(systemId);
        Application app = applicationRepo.findBySystemId(systemId);
        return app.getConsumeRelationship();
    }


    /**
     * @param systemId
     * @return Method being provided by the application with specified system ID.
     */
    @RequestMapping(value = "/{systemId}/provides", method = RequestMethod.GET)
    @ResponseBody
    public List<Method> getProvidedMethods(@PathVariable("systemId") Integer systemId) {
        NullUtils.nullCheck(systemId);
        Application app = applicationRepo.findBySystemId(systemId);
        NullUtils.nullCheck(app);
        return app.getProvidedMethods();
    }

    /**
     * Creating JSON which is source for graph view (visualisation.html)
     *
     * @return Map<String,Object> of all the nodes and relations in the graph as JSON, one map row is one relation ('Node - Relation - Node') record.
     */
    @RequestMapping("/graph")
    @ResponseBody
    public Map<String, Object> visualiseWholeGraph() {
        return visualization.visualizeGraph();
    }


/*
    /**
     * Creating JSON which is source for graph view of a single application and it's relations
     *
     * @return Map<String,Object>
     *
    @RequestMapping("/appRelations")
    @ResponseBody
    public Map<String, Object> visualizeAppRelations(@PathVariable("systemId") String appName) {
        return visualization.visualizeApplicationRelations(applicationRepo.findByName(appName));
    }
*/
	/**
     * METODY POD TIMTO KOMENTAREM JSOU NA ODSTRANENI, SLOUZI POUZE PRO TEST
     *
     */

    @RequestMapping(value = ("/insert"), method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> saveApp() {
        Timestamp start = Timestamp.valueOf("2016-06-01 11:00:00.0");
        Timestamp end = Timestamp.valueOf("2016-06-05 11:00:00.0");
        dataConversion.convertData(new TimeRange(start, end));
        return visualization.visualizeGraph();
    }

//    @RequestMapping(value = ("/insertTest"), method = RequestMethod.GET)
//    @ResponseBody
//    public Map<String, Object> control() {
//
//        for (SystemID sysId : SystemID.values()) {
//            applicationRepo.save(new Application(sysId.name(), sysId.getID(), new ArrayList<Method>()));
//        }
//
//        dataConversion.convertData(createMessages());
//        return visualization.visualizeGraph();
//    }

    @RequestMapping(value = ("/purge"), method = RequestMethod.GET)
    @ResponseBody
    public boolean purge() {
        session.purgeDatabase();
        return true;
    }
/*
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
*/
}
