package cz.zoubelu.service;

import cz.zoubelu.domain.Application;
import cz.zoubelu.domain.ConsumeRelationship;
import cz.zoubelu.domain.Method;

import java.util.List;

/**
 * Created by zoubas on 9.7.16.
 */
public interface GraphService {

    void saveApp(Application app);

    void saveApps(List<Application> apps);

    void saveRel(ConsumeRelationship rel);

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
    Method findProvidedMethodOfApplication(Application providingApp, String methodName, Integer methodVersion);

    /**
     * @param consumingApp
     * @param consumedMethod
     * @return ConsumeRelationship
     */
    ConsumeRelationship findRelationship(Application consumingApp, Method consumedMethod);



}
