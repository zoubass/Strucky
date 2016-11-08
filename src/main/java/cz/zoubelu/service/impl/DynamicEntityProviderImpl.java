package cz.zoubelu.service.impl;

import cz.zoubelu.codelist.SystemApp;
import cz.zoubelu.domain.Application;
import cz.zoubelu.domain.Method;
import cz.zoubelu.repository.ApplicationRepository;
import cz.zoubelu.service.DynamicEntityProvider;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

/**
 * Created by t922274 on 8.11.2016.
 */
@Component
public class DynamicEntityProviderImpl implements DynamicEntityProvider {
	private final Logger log = Logger.getLogger(getClass());

	@Autowired
	private ApplicationRepository applicationRepo;

	@Override
	public Application getApplication(SystemApp system) {
		Application appById = applicationRepo.findBySystemId(system.getId());
		Application appByName = applicationRepo.findByName(system.getName());
		if (appById != null) {
			return appById;
		} else if (appByName != null) {
			return appByName;

		} else {
			return createApplication(system);
		}

	}

	private Application createApplication(SystemApp system) {
		log.info(String.format("Creating application: %s with systemID: %s",system.getName(),system.getId()));
		Application app = new Application(system.getName(), system.getId(), new ArrayList<Method>());
		return applicationRepo.save(app);
	}
}
