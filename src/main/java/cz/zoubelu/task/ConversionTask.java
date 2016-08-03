package cz.zoubelu.task;

import it.sauronsoftware.cron4j.TaskExecutionContext;

/**
 * Created by zoubas on 14.7.2016.
 */
public interface ConversionTask {

	/**
	 * Executes the task which starts the conversion from RelationalDatabase to GraphDatabase
	 * @param taskExecutionContext
	 * @throws RuntimeException
     */
	void execute(TaskExecutionContext taskExecutionContext) throws RuntimeException;
}
