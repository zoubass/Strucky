package cz.zoubelu.config;

import cz.zoubelu.task.ConversionTask;
import it.sauronsoftware.cron4j.Scheduler;
import it.sauronsoftware.cron4j.SchedulingPattern;
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

    @Value("${table.prefix}")
    private String tablePrefix;

    @Bean
    public Scheduler scheduler() {
        Scheduler s = new Scheduler();
        SchedulingPattern pattern = new SchedulingPattern(schedulingPattern);
        ConversionTask task = conversionTaskImpl();
        String scheduleTaskId = s.schedule(pattern, task);
        task.setScheduledTaskId(scheduleTaskId);
        task.setPattern(pattern);
        return s;
    }

    @Bean
    public ConversionTask conversionTaskImpl() {
        ConversionTask conversionTask = new ConversionTask();
        conversionTask.setTablePrefix(tablePrefix);
        return conversionTask;

    }
}
