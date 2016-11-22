package cz.zoubelu.config;

import cz.zoubelu.task.ConversionTaskImpl;
import it.sauronsoftware.cron4j.Scheduler;
import it.sauronsoftware.cron4j.SchedulingPattern;
import it.sauronsoftware.cron4j.Task;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by zoubas on 14.7.2016.
 */
@Configuration
public class SchedulerConfig {

	@Value("${scheduler.pattern}")
	private String schedulingPattern;

	@Bean
	public Scheduler scheduler(){
		Scheduler s = new Scheduler();
		SchedulingPattern pattern = new SchedulingPattern("59 11 * * Sun");
		s.schedule(pattern,conversionTaskImpl());
		return s;
	}

	@Bean
	public Task conversionTaskImpl(){
		ConversionTaskImpl conversionTask = new ConversionTaskImpl();
		conversionTask.setTablePrefix("Informa_log.A_MESSAGE_");
		return conversionTask;

	}
}
