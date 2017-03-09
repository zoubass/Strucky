package cz.zoubelu.task;

import cz.zoubelu.service.DataConversion;
import cz.zoubelu.utils.ConversionError;
import cz.zoubelu.utils.CsvFileUtils;
import cz.zoubelu.utils.DateUtils;
import cz.zoubelu.utils.TimeRange;
import it.sauronsoftware.cron4j.SchedulingPattern;
import it.sauronsoftware.cron4j.Task;
import it.sauronsoftware.cron4j.TaskExecutionContext;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static cz.zoubelu.utils.DateUtils.getTimeRange;

/**
 * Created by zoubas on 14.7.2016.
 */
public class ConversionTask extends Task {

    @Autowired
    private DataConversion dataConversion;

    private final Logger log = Logger.getLogger(getClass());

    private String tablePrefix;

    private String scheduledTaskId;

    private SchedulingPattern pattern;

    private static final String TABLE_NAME = "MESSAGE";

    @Override
    public void execute(TaskExecutionContext taskExecutionContext) throws RuntimeException {
        log.info("Starting conversion.");
        long startTime = System.currentTimeMillis();

        String tableName = getTableName();
        log.info("Querying table " + tableName);

        TimeRange timeRange = DateUtils.getTimeRangeByFrequency(pattern.toString());


        List<ConversionError> errors = dataConversion.convertData(tableName, timeRange);

        long stopTime = System.currentTimeMillis();
        log.info(String.format("Conversion took %s ms, -> %s minutes.", stopTime - startTime,
                ((stopTime - startTime) / 60000)));
        log.info("Conversion ended with " + errors.size() + " errors.");
    }

    private String getTableName() {
        return TABLE_NAME;
    }

    public void setTablePrefix(String tablePrefix) {
        this.tablePrefix = tablePrefix;
    }

    public String getScheduledTaskId() {
        return scheduledTaskId;
    }

    public void setScheduledTaskId(String scheduledTaskId) {
        this.scheduledTaskId = scheduledTaskId;
    }

    public SchedulingPattern getPattern() {
        return pattern;
    }

    public void setPattern(SchedulingPattern pattern) {
        this.pattern = pattern;
    }
}
