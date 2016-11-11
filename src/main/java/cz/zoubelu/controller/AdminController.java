package cz.zoubelu.controller;

import it.sauronsoftware.cron4j.Scheduler;
import it.sauronsoftware.cron4j.SchedulingPattern;
import it.sauronsoftware.cron4j.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private Scheduler scheduler;

    @Autowired
    private Task conversionTask;

    @RequestMapping(value = ("/pattern"), method = RequestMethod.POST)
    public String configScheduler(@RequestParam(name = "pattern") String pattern, Model model) {
        try {
            SchedulingPattern schedulingPattern = new SchedulingPattern(pattern);
            scheduler.schedule(schedulingPattern,conversionTask);
            model.addAttribute("message", "Pattern úspěšně nastaven. Prosím spusťe scheduler na /admin/start.");
        } catch (Exception e) {
            model.addAttribute("message", "Nepodařilo se nastavit pattern, důvod: " + e.getMessage());
        }
        model.addAttribute("pattern", pattern);
        return "admin";
    }

    @RequestMapping(method = RequestMethod.GET)
    public String showAdmin(Model model) {
        return "admin";
    }
}
