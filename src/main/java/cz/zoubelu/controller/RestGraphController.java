package cz.zoubelu.controller;


import cz.zoubelu.domain.Application;
import cz.zoubelu.service.GraphService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by zoubas on 16.7.16.
 */

@RestController
public class RestGraphController {
    @Autowired
    private GraphService graphService;

    @RequestMapping("/application")
    public
    @ResponseBody
    Application test() {
        Application app = graphService.findByName("newApp");
        return app;
    }
}
