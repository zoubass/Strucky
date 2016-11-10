package cz.zoubelu.utils;

import cz.zoubelu.codelist.SystemApp;
import cz.zoubelu.codelist.SystemsList;
import org.apache.log4j.Logger;

import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by t922274 on 9.11.2016.
 */
public class CsvFileUtils {
	private static final Logger log = Logger.getLogger("CsvUtils");

	public static void savelist() {
		String file = "systemList" + DateUtils.getYearMonthSuffix() + ".csv";
		if (SystemsList.values().isEmpty() || SystemsList.values() == null) {
			try {
				FileWriter writer = new FileWriter(file);
				for (SystemApp s : SystemsList.values()) {
					writer.write(s.getName() + ";" + s.getId() + "\n");
				}
				writer.flush();
				writer.close();
				log.info("Systems list successfully saved.");
			} catch (IOException e) {
				log.error("Failed to cache system list into directory: " + file, e);
			}
		} else {
			log.error("Failed to save system list because it's empty.");
		}
	}
}
