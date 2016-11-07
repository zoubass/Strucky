package cz.zoubelu.controller;

import it.sauronsoftware.cron4j.Scheduler;
import it.sauronsoftware.cron4j.SchedulingPattern;
import it.sauronsoftware.cron4j.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
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

//    @Value("${scheduler.pattern}")
//    private String pattern;

    @RequestMapping(value = ("/pattern"), method = RequestMethod.GET)
    public String configScheduler(@RequestParam(name = "pattern") String pattern, Model model) {
        try {
            SchedulingPattern schedulingPattern = new SchedulingPattern("* * * * *");
            scheduler.schedule(schedulingPattern, conversionTask);
            model.addAttribute("message", "Pattern úspěšně nastaven. Prosím spusťe scheduler na /admin/start.");
        } catch (Exception e) {
            model.addAttribute("message", "Nepodařilo se nastavit pattern, důvod: " + e.getMessage());
        }
        model.addAttribute("pattern", pattern);
        return "visualisation";
    }

    @RequestMapping(value = ("/start"), method = RequestMethod.GET)
    public String startScheduler(Model model) {
        try {
            scheduler.start();
            model.addAttribute("message", "Start scheduleru byl úspěšný.");
        } catch (Exception e) {
            model.addAttribute("message", "Nepodařilo se spustit scheduler, důvod: " + e.getMessage());
        }
        return "visualisation";
    }

    @RequestMapping(value = ("/stop"), method = RequestMethod.GET)
    public String stopScheduler(Model model) {
        try {
            scheduler.start();
            model.addAttribute("message", "Stop scheduleru byl úspěšný.");
        } catch (Exception e) {
            model.addAttribute("message", "Nepodařilo se zastavit scheduler, důvod: " + e.getMessage());
        }
        return "visualisation";
    }


    @RequestMapping(method = RequestMethod.GET)
    public String showAdmin(Model model) {
        model.addAttribute("pattern", "* * * * *");
        return "admin";
    }
}
