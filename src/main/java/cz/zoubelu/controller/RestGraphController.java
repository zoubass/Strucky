package cz.zoubelu.controller;


import com.google.common.collect.Lists;
import cz.zoubelu.domain.Application;
import cz.zoubelu.domain.ConsumeRelationship;
import cz.zoubelu.domain.Method;
import cz.zoubelu.repository.ApplicationRepository;
import cz.zoubelu.repository.InformaDao;
import cz.zoubelu.service.DataConversionService;
import cz.zoubelu.service.GraphService;
import cz.zoubelu.service.Visualization;
import cz.zoubelu.utils.TimeRange;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Timestamp;
import java.util.*;

/**
 * Created by zoubas on 16.7.16.
 */

@RestController
public class RestGraphController {
    @Autowired
    private GraphService graphService;

    @Autowired
    private ApplicationRepository appRepo;

    @Autowired
    private InformaDao informaDao;

    @Autowired
    private Visualization visualization;

    @Autowired
    private DataConversionService dataConversionService;


    @RequestMapping("/graph")
    public @ResponseBody Map<String, Object> test() {

        return visualization.visualizeGraph();
    }


    @RequestMapping(value = ("/insert"), method = RequestMethod.GET)
    public @ResponseBody Application saveApp() {
        Timestamp start = Timestamp.valueOf("2016-06-01 00:00:00.0");
        Timestamp end = Timestamp.valueOf("2016-06-01 23:59:00.0");
        dataConversionService.convertData(new TimeRange(start, end));
        return graphService.findByName("czgearnix");
    }

/*
    private List<Application> createSmallExample() {
        Set<Method> providedMethods = new HashSet<Method>();
        providedMethods.add(new Method("getClientValue", 100));
        Application providingApp = new Application("providingApp", providedMethods);
        Application consumingApp = new Application("consumingApp", null);
        ConsumeRelationship consumeRelationship = new ConsumeRelationship();
        consumeRelationship.setApplication(consumingApp);
        consumeRelationship.setMethod(providedMethods.iterator().next());
        consumingApp.setConsumeRelationship(Lists.newArrayList(consumeRelationship));

        return Lists.newArrayList(consumingApp, providingApp);
    }

    private List<Application> createInitSchema() {
        List<String> appsInDb = informaDao.getApplicationsInPlatform();
        List<Application> applications = new ArrayList<Application>();
        for (String appName : appsInDb) {
            List<String> methodsMessages = informaDao.getMethodOfApplication(appName);
            Set<Method> methods = new HashSet<>();
            for (String methodName : methodsMessages) {
                //metody s verzi jinou nez 100 se musi dynamicky vytvorit, coz je soucast testu
                methods.add(new Method(methodName, 100));
            }
            applications.add(new Application(appName, methods));
        }
        return applications;
    }
    */
}
