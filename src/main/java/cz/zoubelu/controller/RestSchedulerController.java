package cz.zoubelu.controller;

import it.sauronsoftware.cron4j.Scheduler;
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

	@Autowired
	private Scheduler scheduler;

	private static final String OK ="Success.";
	private static final String FAIL = "Nepodařilo se provést akci, důvod: ";

	@RequestMapping(value = ("/start"), method = RequestMethod.GET)
	public String startScheduler(Model model) {
		try {
			scheduler.start();
			return OK;
		} catch (Exception e) {
			return FAIL + e.getMessage();
		}
	}

	@RequestMapping(value = ("/stop"), method = RequestMethod.GET)
	public String stopScheduler(Model model) {
		try {
			scheduler.start();
			return OK;
		} catch (Exception e) {
			return FAIL + e.getMessage();
		}
	}


}
