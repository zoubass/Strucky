package cz.zoubelu.task;

import cz.zoubelu.service.DataConversionService;
import cz.zoubelu.utils.DateUtils;
import it.sauronsoftware.cron4j.Task;
import it.sauronsoftware.cron4j.TaskExecutionContext;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by t922274 on 14.7.2016.
 */
public class ConversionTaskImpl extends Task implements ConversionTask {

	@Autowired
	private DataConversionService conversionService;

	@Override
	public void execute(TaskExecutionContext taskExecutionContext) throws RuntimeException {
		conversionService.convertData(DateUtils.getTimeRange());
	}
}
