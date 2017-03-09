package cz.zoubelu.utils;

import cz.zoubelu.codelist.SystemApp;
import org.apache.log4j.Logger;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by t922274 on 9.11.2016.
 */
public class CsvFileUtils {
    private static final Logger log = Logger.getLogger("CsvUtils");
    private static final String DELIMITER = ";";

    private String fileLocation;


    public static void save(List<SystemApp> systems, String fileLocation) {

        log.info("Saving system list: " + fileLocation);

        try {
            FileWriter writer = new FileWriter(fileLocation);
            for (SystemApp s : systems) {
                writer.write(s.getName() + DELIMITER + s.getId() + "\n");
            }
            writer.flush();
            writer.close();
            log.info("Systems list successfully saved.");
        } catch (IOException e) {
            log.error("Failed to cache system list into directory: " + fileLocation, e);
        }
    }

    public static List<SystemApp> load(String fileLocation) {
        List<SystemApp> systems = new ArrayList<SystemApp>();

        try {

            BufferedReader br = new BufferedReader(new FileReader(fileLocation));
            String line;

            while ((line = br.readLine()) != null) {
                String[] params = line.split(DELIMITER);
                String name = params[0];
                Integer id = Integer.valueOf(params[1]);
                systems.add(new SystemApp(name, id));
            }

        } catch (IOException e) {
            log.error("Failed to load file: " + fileLocation, e);
        } catch (NumberFormatException e) {
            log.error("Cannot load list, the system ID is not a valid number. Check the csv list!");
        }
        return systems;
    }


}
