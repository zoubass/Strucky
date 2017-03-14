package cz.zoubelu.controller;

import cz.zoubelu.codelist.SystemApp;
import cz.zoubelu.codelist.SystemsList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by Admin on 3.11.2016.
 */
@Controller
public class WebController {
    @Autowired
    private SystemsList systemsList;

    @RequestMapping(value = {"/"})
    public String index() {
        return "visualisation";
    }

    @RequestMapping(value = {"/list"})
    public String showSystemList(Model model) {
        model.addAttribute("systems", systemsList.values());
        //TODO: vracet, nebo zvyraznovat newly created
        return "systemsList";
    }

    @RequestMapping(value = {"/edit"})
    public String edit(@PathVariable SystemApp systemApp, Model model) {
        systemsList.edit(systemApp);
        systemsList.saveList();
        model.addAttribute("message","Edit successful.");
        return "systemsList";
    }

    @RequestMapping(value = {"/delete/{id}"})
    public String delete(@PathVariable Integer id, Model model) {
        systemsList.remove(systemsList.getSystemByID(id));
        systemsList.saveList();
        model.addAttribute("message","Delete successful.");
        return "redirect:/list.html";
    }

    @RequestMapping(value = {"/add"})
    public String add(@PathVariable SystemApp systemApp, Model model) {
        systemsList.add(systemApp);
        systemsList.saveList();
        model.addAttribute("message","Added successfully.");
        return "systemsList";
    }

}
