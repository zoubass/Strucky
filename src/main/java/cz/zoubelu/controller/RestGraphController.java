package cz.zoubelu.controller;


import com.google.common.collect.Lists;
import cz.zoubelu.domain.Application;
import cz.zoubelu.domain.Message;
import cz.zoubelu.domain.SystemID;
import cz.zoubelu.repository.ApplicationRepository;
import cz.zoubelu.repository.InformaDao;
import cz.zoubelu.service.DataConversion;
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
    private ApplicationRepository applicationRepo;

    @Autowired
    private InformaDao informaDao;

    @Autowired
    private Visualization visualization;

    @Autowired
    private DataConversion dataConversion;


    @RequestMapping("/graph")
    public @ResponseBody Map<String, Object> test() {

        return visualization.visualizeGraph();
    }


    @RequestMapping(value = ("/insert"), method = RequestMethod.GET)
    public @ResponseBody Application saveApp() {
        dataConversion.convertData(createMessages());
        return applicationRepo.findByName("NHUGO");
    }


    private List<Message> createMessages() {
        Message m1 = new Message();
        Message m2 = new Message();
        m1.setApplication(SystemID.CZGCALCBM.name());
        m1.setMsg_src_sys(SystemID.NHUGO.getID());
        m1.setMsg_type("getClientValue");
        m1.setMsg_version(100);

        m2.setApplication(SystemID.CZGCALCBM.name());
        m2.setMsg_src_sys(SystemID.NHUGO.getID());
        m2.setMsg_type("getClientValue");
        m2.setMsg_version(100);

        return Lists.newArrayList(m1, m2);
    }

/*
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
