package cz.zoubelu.controller;

import cz.zoubelu.service.Visualization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WebController {

//    @Autowired
//    private Visualization visualization;

    @RequestMapping(value = ("/index"), method = RequestMethod.GET)
    public String helloWorld() {

        return "index";
    }

    @RequestMapping(value = ("/graph"), method = RequestMethod.GET)
    public String showGraph() {
//        return visualization.getGraph();
        return "";
    }
}
