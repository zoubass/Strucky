package cz.zoubelu.controller;

import it.sauronsoftware.cron4j.Scheduler;
import it.sauronsoftware.cron4j.SchedulingPattern;
import it.sauronsoftware.cron4j.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class WebController {
    @Autowired
    private Scheduler scheduler;

    @Autowired
    private Task conversionTask;


    @RequestMapping(value = ("/index"), method = RequestMethod.GET)
    public String helloWorld(Model model) {
        model.addAttribute("pattern", "* * * * *");
        return "visualisation";
    }


    @RequestMapping(value = ("/pattern"), method = RequestMethod.GET)
    public String configScheduler(@ModelAttribute String pattern,Model model) {
        try {
            SchedulingPattern schedulingPattern = new SchedulingPattern("* * * * *");
            scheduler.schedule(schedulingPattern, conversionTask);
            model.addAttribute("message","Pattern úspěšně nastaven.");
        }catch(Exception e){
            model.addAttribute("message","Nepodařilo se nastavit pattern, důvod: "+e.getMessage());
        }
        return "visualisation";
    }

    @RequestMapping(value = ("/start"), method = RequestMethod.GET)
    public String startScheduler(Model model,@ModelAttribute boolean isToStart) {
        try {
            if(isToStart) {
                scheduler.start();
            }else{
                scheduler.stop();
            }
            model.addAttribute("message","Start/stop scheduleru byl úspěšný.");
        }catch(Exception e){
            model.addAttribute("message","Nepodařilo se spustit/zastavit scheduler, důvod: "+e.getMessage());
        }
        return "visualisation";
    }
}
