package cz.zoubelu.task;

import cz.zoubelu.service.DataConversion;
import cz.zoubelu.utils.ConversionError;
import cz.zoubelu.utils.DateUtils;
import it.sauronsoftware.cron4j.Task;
import it.sauronsoftware.cron4j.TaskExecutionContext;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.util.List;

/**
 * Created by zoubas on 14.7.2016.
 */
public class ConversionTaskImpl extends Task implements ConversionTask {

	@Autowired
	private DataConversion dataConversion;

	private final Logger log = Logger.getLogger(getClass());

	@Value("${table.prefix}")
	private String tablePrefix;

	@Override
	public void execute(TaskExecutionContext taskExecutionContext) throws RuntimeException {
		log.info("Starting conversion.");
		long startTime = System.currentTimeMillis();

		String tableName = tablePrefix + DateUtils.getYearMonthSuffix();
		List<ConversionError> errors = dataConversion.convertData(tableName);

		long stopTime = System.currentTimeMillis();
		log.info(String.format("Conversion took %s ms.", stopTime - startTime));
		log.info("Conversion ended with " + errors.size() + " errors.");
	}
}
