package cz.zoubelu.controller;

import cz.zoubelu.task.ConversionTask;
import cz.zoubelu.utils.DateUtils;
import it.sauronsoftware.cron4j.Scheduler;
import it.sauronsoftware.cron4j.SchedulingPattern;
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
    private ConversionTask conversionTask;

    @RequestMapping(method = RequestMethod.POST)
    public String configScheduler(@RequestParam(name = "pattern") String pattern, Model model) {
        try {
            scheduler.reschedule(conversionTask.getScheduledTaskId(), pattern);
            conversionTask.setPattern(new SchedulingPattern(pattern));
            model.addAttribute("message", "Pattern " + pattern + " úspěšně nastaven.");
        } catch (Exception e) {
            model.addAttribute("message", "Nepodařilo se nastavit pattern, důvod: " + e.getMessage());
        }
        model.addAttribute("pattern", pattern);
        model.addAttribute("frequency", DateUtils.recogniseSchedulerFrequency(pattern).name());
        return "admin";
    }

    @RequestMapping(method = RequestMethod.GET)
    public String showAdmin(Model model) {
        model.addAttribute("pattern", conversionTask.getPattern());
        return "admin";
    }
}
