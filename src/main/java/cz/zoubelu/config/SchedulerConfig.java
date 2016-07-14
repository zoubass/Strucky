package cz.zoubelu.config;

import cz.zoubelu.runnable.impl.ConversionTaskImpl;
import it.sauronsoftware.cron4j.Scheduler;
import it.sauronsoftware.cron4j.SchedulingPattern;
import it.sauronsoftware.cron4j.Task;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

/**
 * Created by t922274 on 14.7.2016.
 */
@Configuration
@Profile("test")
public class SchedulerConfig {

	@Bean
	public Scheduler scheduler(){
		//TODO: rozhodnout kde bude scheduler nastavovan, jaky bude scenar jeho pouziti, aby se nemenil pattern za runtime
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
