package cz.zoubelu.controller;

import it.sauronsoftware.cron4j.Scheduler;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by t922274 on 9.11.2016.
 */
@RestController
@RequestMapping("/scheduler")
public class RestSchedulerController{
//	private final Logger log = Logger.getLogger(getClass());

	@Autowired
	private Scheduler scheduler;


	private static final String OK ="Successfully ";
	private static final String FAIL = "Failed to procceed action. Reason: ";

	@RequestMapping("/start")
	public String startScheduler(Model model) {
		try {
			scheduler.start();
			return OK + " started.";
		} catch (Exception e) {
			return FAIL + e.getMessage();
		}
	}

	@RequestMapping("/stop")
	public String stopScheduler(Model model) {
		try {
			scheduler.stop();
			return OK + " stopped.";
		} catch (Exception e) {
			return FAIL + e.getMessage();
		}
	}

	@RequestMapping("status")
	public String getSchedulerStatus(){
		return scheduler.isStarted()? "Running":"Stopped";
	}


}
