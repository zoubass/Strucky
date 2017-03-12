package cz.zoubelu.controller;

import cz.zoubelu.codelist.SystemsList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by Admin on 3.11.2016.
 */
@Controller
public class WebController {
    @Autowired
    private SystemsList systemsList;

    @RequestMapping(value = {"/", "/index"})
    public String index() {
        return "visualisation";
    }

    @RequestMapping(value = {"/systemList"})
    public String showSystemList(Model model) {
        model.addAttribute("systems", systemsList.values());
        //vracet, nebo zvyraznovat newly created
        return "systemsList";
    }
}
