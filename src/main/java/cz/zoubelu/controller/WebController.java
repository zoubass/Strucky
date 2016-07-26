package cz.zoubelu.controller;

import cz.zoubelu.config.SchedulerConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class WebController {
    @Autowired
    private SchedulerConfig scheduler;

    @RequestMapping(value = ("/index"), method = RequestMethod.GET)
    public String helloWorld() {

        return "index";
    }


    @RequestMapping(value = ("/scheduler"), method = RequestMethod.GET)
    public String configScheduler() {
        //TODO: konfigurace scheduleru pres UI?
        return "";
    }
}
