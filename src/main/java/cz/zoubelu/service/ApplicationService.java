package cz.zoubelu.service;

import cz.zoubelu.domain.Application;
import cz.zoubelu.domain.ConsumeRelationship;
import cz.zoubelu.domain.Method;

import java.util.List;

/**
 * Created by zoubas on 9.7.16.
 */
public interface ApplicationService {

    void save(Application app);

    void save(List<Application> apps);

    List<Application> findAll();

    Application findByName(String name);

    /**
     * Finds method that is provided by the specified application
     *
     * @param providingApp
     * @param methodName
     * @param methodVersion
     * @return Method
     */
    Method findMethodOfApplication(Application providingApp, String methodName, Integer methodVersion);

    /**
     * @param consumingApp
     * @param consumedMethod
     * @return ConsumeRelationship
     */
    ConsumeRelationship findRelationship(Application consumingApp, Method consumedMethod);

}
