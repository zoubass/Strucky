package cz.zoubelu.service;

import cz.zoubelu.domain.Application;

import java.util.List;

/**
 * Created by zoubas on 9.7.16.
 */
public interface ApplicationService {

    void save(Application app);

    List<Application> findAll();
}
