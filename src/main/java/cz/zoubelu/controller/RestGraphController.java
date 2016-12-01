package cz.zoubelu.controller;

import cz.zoubelu.codelist.SystemApp;
import cz.zoubelu.codelist.SystemsList;
import cz.zoubelu.domain.ConsumeRelationship;
import cz.zoubelu.domain.Method;
import cz.zoubelu.repository.ApplicationRepository;
import cz.zoubelu.repository.InformaMessageRepository;
import cz.zoubelu.repository.MethodRepository;
import cz.zoubelu.repository.RelationshipRepository;
import cz.zoubelu.service.DataConversion;
import cz.zoubelu.service.Visualization;
import cz.zoubelu.utils.ConversionError;
import cz.zoubelu.utils.ErrorResponse;
import cz.zoubelu.utils.NullUtils;
import cz.zoubelu.utils.TimeRange;
import org.apache.log4j.Logger;
import org.neo4j.ogm.session.Neo4jSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by zoubas on 16.7.16.
 */

@RestController
@RequestMapping(value = "/application")
public class RestGraphController {
    private final Logger log = Logger.getLogger(getClass());

    @Autowired
    private ApplicationRepository applicationRepo;

    @Autowired
    private MethodRepository methodRepo;

    @Autowired
    private Neo4jSession session;

    @Autowired
    private RelationshipRepository relationRepo;

    @Autowired
    private InformaMessageRepository informaRepository;

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
    public cz.zoubelu.domain.Application getApplication(@PathVariable("systemId") Integer systemId) {
        return applicationRepo.findBySystemId(systemId);
    }

    /**
     * @param systemId
     * @return Methods that are being consumed by application with specified system ID.
     */
    @RequestMapping(value = "/{systemId}/consumes", method = RequestMethod.GET)
    @ResponseBody
    public List<ConsumeRelationship> getConsumedMethods(@PathVariable("systemId") Integer systemId) {
        NullUtils.nullCheck(systemId, "Application ID cannot be null.");
        cz.zoubelu.domain.Application app = applicationRepo.findBySystemId(systemId);
        NullUtils.nullCheck(app, "Cannot find application with system id: " + systemId);
        return app.getConsumeRelationship();
    }

    @RequestMapping(value = "/{appName}/info", method = RequestMethod.GET)
    @ResponseBody
    public cz.zoubelu.domain.Application getApplicationInfo(@PathVariable("appName") String appName) {
        return applicationRepo.findByName(appName);
    }


    /**
     * @param systemId
     * @return Method being provided by the application with specified system ID.
     */
    @RequestMapping(value = "/{systemId}/provides", method = RequestMethod.GET)
    @ResponseBody
    public List<Method> getProvidedMethods(@PathVariable("systemId") Integer systemId) {
        NullUtils.nullCheck(systemId, "Application ID cannot be null.");
        cz.zoubelu.domain.Application app = applicationRepo.findBySystemId(systemId);
        NullUtils.nullCheck(app, "Cannot find application with system id: " + systemId);
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


    /**
     * Creating JSON which is source for graph view of a single application and it's relations
     *
     * @return Map<String,Object>
     */
    @RequestMapping("/appGraph/{systemId}")
    @ResponseBody
    public Map<String, Object> visualizeAppRelations(@PathVariable("systemId") String appName) {
        return visualization.visualizeApplicationRelations(applicationRepo.findByName(appName));
    }

    @RequestMapping("/error")
    @ResponseBody
    public ErrorResponse returnError(Exception e) {
        return new ErrorResponse(e.getMessage());
    }



    @RequestMapping(value = ("/insert"), method = RequestMethod.GET)
    @ResponseBody
    public List<ConversionError> insertData(@RequestParam String year,@RequestParam String month,@RequestParam String day, @RequestParam String hour) {
        Timestamp start = Timestamp.valueOf(year+"-"+month+"-01 00:00:00.0");
        Timestamp end = Timestamp.valueOf(year+"-"+month+"-" +day +" " + hour + ":00:00.0");
        long f = System.currentTimeMillis();
        List<ConversionError> errors = dataConversion.convertData("Informa_log.A_MESSAGE_" + year + month, new TimeRange(start, end));
        long l = System.currentTimeMillis();
        log.info("--------------------------------------------------------------------------------");
        log.info("Conversion took: " + (l-f));
        log.info("--------------------------------------------------------------------------------");
        return errors;
    }

    @RequestMapping(value = ("/createGraphSchema"), method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> createSchema() {
        for (SystemApp system : new SystemsList().values()) {
            applicationRepo.save(new cz.zoubelu.domain.Application(system.getName(), system.getId(), new ArrayList<Method>()));
        }
        for (int i = 0; i < 1200; i++) {
            applicationRepo.findBySystemId(i);
        }
        return visualization.visualizeGraph();
    }
}
