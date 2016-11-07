package cz.zoubelu.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by Admin on 3.11.2016.
 */
@Controller
public class WebController {

    @RequestMapping(value = {"/","/index"})
    public String index() {
        return "visualisation";
    }
}
