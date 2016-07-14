package cz.zoubelu.task;

import it.sauronsoftware.cron4j.TaskExecutionContext;

/**
 * Created by t922274 on 14.7.2016.
 */
public interface ConversionTask {

	void execute(TaskExecutionContext taskExecutionContext) throws RuntimeException;
}
