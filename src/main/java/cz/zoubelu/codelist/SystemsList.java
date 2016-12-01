package cz.zoubelu.codelist;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zoubas on 10.7.16.
 * This enum represents the application schema of graph integration
 */
public class SystemsList {
	private final Logger log = Logger.getLogger(getClass());

	private static List<SystemApp> systemsList;

	public SystemsList() {
		this.systemsList = new ArrayList<SystemApp>() {{
			add(new SystemApp("PEGAS-KUKÁTKO", 100));
			add(new SystemApp("SCA", 101));
			add(new SystemApp("PORADCE", 102));
			add(new SystemApp("HUGO", 103));
			add(new SystemApp("FAC-APP", 104));
			add(new SystemApp("PŠP KLIENT DESKTOPOVÁ APLIKACE", 107));
			add(new SystemApp("BERTA", 108));
			add(new SystemApp("EREGRESY", 109));
			add(new SystemApp("EAGENTI", 110));
			add(new SystemApp("FIP-PARTNERS", 111));
			add(new SystemApp("SFTPBRIDGE", 112));
			add(new SystemApp("WEB GENERALI", 113));
			add(new SystemApp("NEDORUČENÁ POŠTA LN", 114));
			add(new SystemApp("MESSAGE_HOUSE", 115));
			add(new SystemApp("EPOHLEDAVKY", 116));
			add(new SystemApp("LISA", 141));
			add(new SystemApp("JOK/CLK", 142));
			add(new SystemApp("MICROSITEPOV", 201));
			add(new SystemApp("AWD", 1000));
			add(new SystemApp("NHUGO", 1001));
			add(new SystemApp("UNICREDIT LEASING", 1003));
			add(new SystemApp("INSIA", 1004));
			add(new SystemApp("MODUL SERVIS", 1005));
			add(new SystemApp("TOTAL BROKERS", 1006));
			add(new SystemApp("PFP", 1007));
			add(new SystemApp("FIXUM", 1008));
			add(new SystemApp("DATALIFE", 1009));
			add(new SystemApp("OK_GROUP", 1010));
			add(new SystemApp("SALVE FINANCE", 1011));
			add(new SystemApp("RENOMIA", 1012));
			add(new SystemApp("INWEB", 1013));
			add(new SystemApp("SMS FINANČNÍ PORADENSTVÍ", 1014));
			add(new SystemApp("FSS + PARTNERS_", 1015));
			add(new SystemApp("VOLKSWAGEN_FINANCIAL_SERVICES_", 1016));
			add(new SystemApp("AXIMA_SPOL.S.R.O", 1017));
			add(new SystemApp("ČESKÝ_OBCHODNÍ_SERVIS", 1018));
			add(new SystemApp("FEDS", 1019));
			add(new SystemApp("OVB", 1020));
			add(new SystemApp("FINCENTRUM", 1021));
			add(new SystemApp("ALLRISK", 1022));
			add(new SystemApp("EFINANCE", 1023));
			add(new SystemApp("INPOL", 1024));
			add(new SystemApp("SROVNÁVAČ", 1025));
			add(new SystemApp("IBM_MB", 0));
			add(new SystemApp("CZGUSRMNG", 1));
			add(new SystemApp("CZGUCINFO", 2));
			add(new SystemApp("CZGPOLINFO", 3));
			add(new SystemApp("CZGPOLICING", 4));
			add(new SystemApp("CZGMESSAGING", 5));
			add(new SystemApp("CZGCALCBM", 6));
			add(new SystemApp("CZGDELIVERY", 7));
			add(new SystemApp("CZGPRINTS", 8));
			add(new SystemApp("CZGDA", 9));
			add(new SystemApp("CZGCLAIMS", 12));
			add(new SystemApp("CZGCODELIST", 13));
			add(new SystemApp("CZGCARPRICING", 14));
			add(new SystemApp("CZGPRODCATALOG", 15));
			add(new SystemApp("CZGAGENTINFO", 16));
			add(new SystemApp("CZGEARNIX", 17));
			add(new SystemApp("CZGSEARCH", 18));
			add(new SystemApp("CZGCOMM", 19));
			add(new SystemApp("CZGLEADMNG", 20));
			add(new SystemApp("CZGBLACKLISTING", 21));
			add(new SystemApp("CZGCONTRACTIMPORTPMV", 22));
			add(new SystemApp("CZGPROCESSSUPPORT", 23));
			add(new SystemApp("CZCTIA", 24));
			add(new SystemApp("MONITORING", 99));
			//newly added
			add(new SystemApp("Message House", 134));
			add(new SystemApp("SVCOVB", 10));
			//vymyslene ID
			add(new SystemApp("CZGCONTRACTUPDATE", 555));
			add(new SystemApp("SVCPMS", 1026));
			add(new SystemApp("CPOJPWB", 1055));
			add(new SystemApp("TPUCCINI", 1056));
		}};
	}

	public SystemApp getSystemByID(Integer id) {
		for (SystemApp system : systemsList) {
			if ((id).equals(system.getId())) {
				return system;
			}
		}
		log.info("Unable to find system with id: " + id + ".");
		return addSystem(id.toString(), id);
	}

	public SystemApp getIdByName(String nameLCase) {
		String name = StringUtils.upperCase(nameLCase);
		for (SystemApp system : systemsList) {
			if (name.equals(system.getName())) {
				return system;
			}
		}
		log.info("Unable to find system with name: " + name + ".");
		return addSystem(name, -1);
	}

	private SystemApp addSystem(String name, Integer id) {
		log.info(String.format("Adding new system to the system list. Name: %s id: %s.", name, id));
		SystemApp s = new SystemApp(name, id);
		systemsList.add(s);
		return s;
	}

	public List<SystemApp> values(){
		return systemsList;
	}
}
