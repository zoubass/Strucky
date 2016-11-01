package cz.zoubelu.task;

import cz.zoubelu.service.DataConversion;
import cz.zoubelu.utils.DateUtils;
import it.sauronsoftware.cron4j.Task;
import it.sauronsoftware.cron4j.TaskExecutionContext;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by zoubas on 14.7.2016.
 */
public class ConversionTaskImpl extends Task implements ConversionTask {

	@Autowired
	private DataConversion conversionService;

	private String tablePrefix;

	public ConversionTaskImpl(String tablePrefix){
		this.tablePrefix=tablePrefix;
	}

	@Override
	public void execute(TaskExecutionContext taskExecutionContext) throws RuntimeException {
		String tableName = "A_MESSAGE_" + DateUtils.getTableSuffix();
		conversionService.convertData(tableName);
	}
}
