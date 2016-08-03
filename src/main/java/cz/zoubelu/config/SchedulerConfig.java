package cz.zoubelu.config;

import cz.zoubelu.task.ConversionTaskImpl;
import it.sauronsoftware.cron4j.Scheduler;
import it.sauronsoftware.cron4j.SchedulingPattern;
import it.sauronsoftware.cron4j.Task;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by zoubas on 14.7.2016.
 */
@Configuration
public class SchedulerConfig {

	@Bean
	public Scheduler scheduler(){
		Scheduler s = new Scheduler();
		//		"59 11 * * Sun" - PRODUCTION PATTERN
		SchedulingPattern pattern = new SchedulingPattern("* * * * *");
		s.schedule(pattern,conversionTaskImpl());
		return s;
	}

	@Bean
	public Task conversionTaskImpl(){
		return new ConversionTaskImpl();
	}
}
